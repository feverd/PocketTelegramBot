package org.project.dao;

import java.util.List;

public interface Dao<T, PK> {

    void add(T entity);

    T getByKey(PK pk);

    void update(T entity);

    void deleteByKey(PK pk);
}

