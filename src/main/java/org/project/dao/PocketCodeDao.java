package org.project.dao;

import org.project.entity.PocketCode;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Objects;

public class PocketCodeDao implements Dao<PocketCode, String> {
    private EntityManager manager;

    public PocketCodeDao(EntityManager manager) {
        this.manager = Objects.requireNonNull(manager);
    }


    public PocketCode getByCode(String code) {
        Query query = manager.createQuery(
                "SELECT p FROM PocketCode p WHERE p.code = :c");
        query.setParameter("c", code);

        return (PocketCode) query.getSingleResult();
    }

    @Override
    public void add(PocketCode entity) {
        manager.getTransaction().begin();
        manager.persist(entity);
        manager.getTransaction().commit();
    }

    @Override
    public PocketCode getByKey(String s) {
        return manager.find(PocketCode.class, s);
    }

    @Override
    public void update(PocketCode entity) {
        manager.merge(entity);
    }

    @Override
    public void deleteByKey(String s) {
        manager.remove(getByKey(s));
    }
}
