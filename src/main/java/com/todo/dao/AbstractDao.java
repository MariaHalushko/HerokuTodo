package com.todo.dao;

import com.todo.dao.interfaces.GenericDao;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

abstract class AbstractDao<PK extends Serializable, T> implements GenericDao<PK, T> {

    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    AbstractDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Autowired
    private SessionFactory sessionFactory;


    Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public T findById(PK key) {
        return (T) getSession().get(persistentClass, key);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<T> listAll() {
        Criteria criteria = createEntityCriteria();
        return (List<T>) criteria.list();
    }

    @Override
    @Transactional
    public void save(T entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        getSession().delete(entity);
    }

    Criteria createEntityCriteria() {
        return getSession().createCriteria(persistentClass, persistentClass.getName());
    }

    @Override
    public void update(T entity) {
        getSession().persist(entity);
    }
}
