package dev.httpmarco.evelon;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class RepositoryConstantPool {

    private final Map<RepositoryConstant<?>, Object> constants = new ConcurrentHashMap<>();

    public void add(RepositoryConstant<Void> constant) {
        constants.put(constant, true);
    }

    public <T> T put(RepositoryConstant<T> constant, T value) {
        constants.put(constant, value);
        return value;
    }

    public boolean has(RepositoryConstant<?> constant) {
        return constants.containsKey(constant);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(RepositoryConstant<T> constant) {
        return (T) constants.get(constant);
    }
}