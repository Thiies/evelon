package dev.httpmarco.evelon.common.model.subs;

import dev.httpmarco.evelon.common.builder.Builder;
import dev.httpmarco.evelon.common.model.Model;
import dev.httpmarco.evelon.common.model.Stage;
import dev.httpmarco.evelon.common.model.SubStage;
import dev.httpmarco.evelon.common.repository.RepositoryField;
import dev.httpmarco.evelon.common.repository.clazz.RepositoryClass;
import dev.httpmarco.evelon.common.repository.clazz.RepositoryClassImpl;
import dev.httpmarco.evelon.common.repository.clazz.RepositoryObjectClass;
import dev.httpmarco.osgan.reflections.Reflections;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract class AbstractMapSubStage<B extends Builder<B, ?>> implements SubStage<Map<?, ?>, B> {

    @Override
    public void initialize(String stageId, Model<B> model, @NotNull RepositoryField<?> ownField, RepositoryObjectClass<?> clazz, B queries) {
        var mapTypes = Reflections.of(ownField.field()).generics();

        if (mapTypes.length != 2) {
            throw new IllegalStateException("Map stage type has not exactly two elements. That is not a requirement of maps.");
        }

        // add foreign key linking
        queries.linkPrimaries(ownField.parentClass().asObjectClass().primaryFields());

        var keyType = new RepositoryClassImpl<>(mapTypes[0]);
        var valueType = new RepositoryClassImpl<>(mapTypes[1]);

        this.initializeMapElement(true, queries, keyType.stageOf(model), ownField, keyType, clazz);
        this.initializeMapElement(false, queries, valueType.stageOf(model), ownField, valueType, clazz);
    }

    @Override
    public void create(Map<?, ?> value, String stageId, Model<B> model, RepositoryField<Map<?, ?>> ownField, RepositoryObjectClass<?> clazz, B queries) {
        // todo
    }

    public abstract void initializeMapElement(boolean key, B builder, Stage<?, B> stage, RepositoryField<?> parentField, RepositoryClass<?> type, RepositoryObjectClass<?> clazz);

    @Override
    public boolean isElement(Class<?> type) {
        return Map.class.isAssignableFrom(type);
    }
}
