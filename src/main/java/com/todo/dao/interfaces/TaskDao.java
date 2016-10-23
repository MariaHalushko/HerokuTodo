package com.todo.dao.interfaces;

import com.todo.model.Task;

public interface TaskDao extends GenericDao<Long, Task> {

    void delete(Long id);
}
