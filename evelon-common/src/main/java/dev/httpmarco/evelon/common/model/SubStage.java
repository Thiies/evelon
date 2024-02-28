package dev.httpmarco.evelon.common.model;

import dev.httpmarco.evelon.common.builder.Builder;
import dev.httpmarco.evelon.common.repository.RepositoryField;
import dev.httpmarco.evelon.common.repository.clazz.RepositoryObjectClass;

/**
 * @param <R> response of initialize phase (String for query languages)
 */
public interface SubStage<R extends Builder> extends Stage<R> {

    void initialize(String stageId, Model<?> model, RepositoryField ownField, RepositoryObjectClass<?> clazz, R queries);

    @SuppressWarnings("unchecked")
    default void initializeWithMapping(String stageId, Model<?> model, RepositoryField ownField, RepositoryObjectClass<?> clazz, Object queries) {
        this.initialize(stageId, model, ownField, clazz, (R) queries);
    }

}
