package com.todo.dao.interfaces;

import com.todo.model.Task;
import com.todo.model.User;

import java.util.List;

public interface UserDao extends GenericDao<Long, User> {

    public List<Task> getUserTasks(Long userId);

    public List<User> getWithFilter(String filter, int page, int perPage);
}
