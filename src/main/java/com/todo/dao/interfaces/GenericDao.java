package com.todo.dao.interfaces;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<PK extends Serializable, T> {

    T findById(PK id);

    void save(T entity);

    void delete(T entity);

    void update(T entity);

    List<T> listAll();

}
