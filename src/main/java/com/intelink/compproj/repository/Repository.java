package com.intelink.compproj.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Repository<T> {

    private Set<T> entities;

    public Repository() {
        entities = new HashSet<>();
    }

    public List<T> getAll() {
        List<T> result = new ArrayList<>(entities);
        return result;
    }

    public void add(T entity) {
        entities.add(entity);
    }

    public void remove(T entity) {
        entities.remove(entity);
    }
}
