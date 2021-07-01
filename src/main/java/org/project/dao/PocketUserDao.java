package org.project.dao;

import org.project.entity.PocketUser;

import javax.persistence.EntityManager;

public class PocketUserDao implements Dao<PocketUser, Long> {
    private EntityManager manager;

    public PocketUserDao(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(PocketUser entity) {
        manager.getTransaction().begin();
        manager.persist(entity);
        manager.getTransaction().commit();
    }

    @Override
    public PocketUser getByKey(Long key) {
        return manager.find(PocketUser.class, key);
    }

    @Override
    public void update(PocketUser entity) {
        manager.getTransaction().begin();
        manager.merge(entity);
        manager.getTransaction().commit();
    }

    @Override
    public void deleteByKey(Long key) {
        manager.getTransaction().begin();
        manager.remove(getByKey(key));
        manager.getTransaction().commit();
    }
}
