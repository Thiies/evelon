package dev.httpmarco.evelon.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class RepositoryConstantPool {

    private final Map<RepositoryConstant<?>, Object> constants = new ConcurrentHashMap<>();

    public <T> void add(RepositoryConstant<Void> constant) {
        constants.put(constant, null);
    }

    public <T> void put(RepositoryConstant<T> constant, T value) {
        constants.put(constant, value);
    }

    public List<RepositoryConstant<?>> list() {
        return List.copyOf(constants.keySet());
    }

    public boolean has(RepositoryConstant<?> constant) {
        return constants.containsKey(constant);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(RepositoryConstant<T> constant) {
        return (T) constants.get(constant);
    }
}
