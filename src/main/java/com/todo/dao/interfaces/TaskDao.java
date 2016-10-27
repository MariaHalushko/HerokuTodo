package com.todo.dao.interfaces;

import com.todo.model.Task;
import com.todo.model.enums.Status;

import java.util.List;

public interface TaskDao extends GenericDao<Long, Task> {

    void delete(Long id);

    List<Task> getWithFilter(String filter, int page, int perPage, Status status);
}
