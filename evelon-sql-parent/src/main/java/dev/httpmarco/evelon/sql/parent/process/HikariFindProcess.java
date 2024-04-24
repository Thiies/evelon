package dev.httpmarco.evelon.sql.parent.process;

import dev.httpmarco.evelon.RepositoryConstant;
import dev.httpmarco.evelon.RepositoryEntry;
import dev.httpmarco.evelon.RepositoryExternalEntry;
import dev.httpmarco.evelon.external.RepositoryCollectionEntry;
import dev.httpmarco.evelon.process.kind.QueryProcess;
import dev.httpmarco.evelon.sql.parent.reference.HikariProcessReference;
import dev.httpmarco.osgan.reflections.Reflections;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;

@AllArgsConstructor
public final class HikariFindProcess extends QueryProcess<HikariProcessReference> {

    private static final String SELECT_QUERY = "SELECT %s FROM %s;";

    //todo
    private final int skip = -1;
    private final int limit = -1;

    @Override
    public @NotNull Object run(@NotNull RepositoryExternalEntry entry, HikariProcessReference reference) {
        var items = new ArrayList<>();
        var searchedItems = new ArrayList<String>();

        for (var child : entry.children()) {
            if (child instanceof RepositoryExternalEntry) {
                continue;
            }
            searchedItems.add(child.id());
        }

        var itemStringList = String.join(", ", searchedItems);
        reference.append(SELECT_QUERY.formatted(itemStringList, entry.id()), new Object[0], resultSet -> {
            try {
                if (entry instanceof RepositoryCollectionEntry collectionEntry && !(collectionEntry.typeEntry() instanceof RepositoryExternalEntry)) {
                    items.add(resultSet.getObject(collectionEntry.typeEntry().id()));
                    return;
                }

                var reflections = Reflections.on(entry.clazz());

                // we must use the value type of collection entry to create the object
                if (entry instanceof RepositoryCollectionEntry collectionEntry) {
                    reflections = Reflections.on(collectionEntry.typeEntry().clazz());
                }

                var object = reflections.allocate();
                for (var child : entry.children()) {
                    // children need a separate statement
                    if (child instanceof RepositoryExternalEntry externalEntry) {
                        Reflections.on(object).modify(child.constant(RepositoryConstant.PARAM_FIELD), new HikariFindProcess().run(externalEntry, reference));
                        continue;
                    }

                    var value = resultSet.getObject(child.id());
                    // jdbc cannot cast to char ... we handle this manuel
                    if (value instanceof String && child.clazz().equals(char.class)) {
                        value = ((String) value).charAt(0);
                    }
                    // modify the original field with a new value
                    Reflections.on(object).modify(child.id(), value);
                }
                items.add(object);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        return items;
    }
}
