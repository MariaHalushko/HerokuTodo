package com.todo.dao;

import com.todo.dao.interfaces.UserDao;
import com.todo.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by mary on 17.10.16.
 */


@Repository
public class UserDaoImpl extends AbstractDao<Long,User> implements UserDao {
}
