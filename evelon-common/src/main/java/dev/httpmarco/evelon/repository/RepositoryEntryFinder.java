package dev.httpmarco.evelon.repository;

import dev.httpmarco.evelon.repository.exception.UnsupportedEntryTypeException;
import dev.httpmarco.evelon.repository.external.RepositoryCollectionEntry;
import dev.httpmarco.evelon.repository.external.RepositoryMapEntry;
import dev.httpmarco.evelon.repository.external.RepositoryObjectEntry;
import dev.httpmarco.osgan.utils.Utils;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public final class RepositoryEntryFinder {

    @Contract("_, _, _, _ -> new")
    public static @NotNull RepositoryEntry find(Class<?> clazz, Field field, String id, RepositoryExternalEntry parent) {
        var externalId = parent != null ? parent.id() + "_" + id : id;

        if (Utils.JAVA_ELEMENTS.contains(clazz) || clazz.isEnum() || clazz.isPrimitive() || clazz.equals(UUID.class)) {
            return new RepositoryEntry(id, clazz, parent);
        }

        if (Map.class.isAssignableFrom(clazz)) {
            return new RepositoryMapEntry(externalId, field, parent);
        }

        if (Collection.class.isAssignableFrom(clazz)) {
            return new RepositoryCollectionEntry(externalId, field, parent);
        }

        if (!clazz.isSynthetic()) {
            return new RepositoryObjectEntry(externalId, clazz, parent);
        }

        throw new UnsupportedEntryTypeException(clazz);
    }

    @Contract("_, _, _ -> new")
    public static @NotNull RepositoryEntry find(Field field, String id, RepositoryExternalEntry parent) {
        return find(field.getType(), field, id, parent);
    }
}