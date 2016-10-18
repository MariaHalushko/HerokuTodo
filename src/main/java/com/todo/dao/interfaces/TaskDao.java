package com.todo.dao.interfaces;

import com.todo.model.Task;

/**
 * Created by RAYANT on 26.01.2016.
 */
public interface TaskDao extends GenericDao<Long,Task> {

    void delete(Long id);
}
