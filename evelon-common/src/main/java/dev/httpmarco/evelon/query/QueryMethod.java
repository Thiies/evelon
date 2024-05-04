package dev.httpmarco.evelon.query;

import org.jetbrains.annotations.Nullable;

public interface QueryMethod<T> extends QueryMethodInfo {

    void create(T value);

    void update(T value);

    void delete();

    boolean exists();

    @Nullable
    T findFirst();

}