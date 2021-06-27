package org.project.dao;

import org.project.entity.PocketAppCode;

import javax.persistence.EntityManager;
import java.util.Objects;

public class PocketCodeDao implements Dao<PocketAppCode, String> {
    private EntityManager manager;

    public PocketCodeDao(EntityManager manager) {
        this.manager = Objects.requireNonNull(manager);
    }

    @Override
    public void add(PocketAppCode entity) {
        manager.getTransaction().begin();
        manager.persist(entity);
        manager.getTransaction().commit();
    }

    @Override
    public PocketAppCode getByKey(String s) {
        return manager.find(PocketAppCode.class, s);
    }

    @Override
    public void update(PocketAppCode entity) {
        manager.merge(entity);
    }

    @Override
    public void deleteByKey(String s) {
        manager.remove(getByKey(s));
    }
}
