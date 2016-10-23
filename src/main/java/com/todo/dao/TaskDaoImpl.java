package com.todo.dao;

import com.todo.dao.interfaces.TaskDao;
import com.todo.model.Task;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository()
public class TaskDaoImpl extends AbstractDao<Long, Task> implements TaskDao {

    @Override
    public void delete(Long id) {
        Session session = getSession();
        session.getTransaction().begin();
        SQLQuery query = session.createSQLQuery("DELETE FROM tasks WHERE id=:id");
        query.setLong("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
    }
}
