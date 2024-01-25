package dev.httpmarco.evelon.common.model;

import dev.httpmarco.evelon.common.repository.clazz.RepositoryObjectClass;

/**
 * @param <R> response of initialize phase (String for query languages)
 */
public interface SubStage<R> extends Stage {

    void initialize(String stageId, RepositoryObjectClass<?> clazz, R queries);

    default void initializeWithMapping(String stageId, RepositoryObjectClass<?> clazz, Object queries) {
        this.initialize(stageId, clazz, (R) queries);
    }

}