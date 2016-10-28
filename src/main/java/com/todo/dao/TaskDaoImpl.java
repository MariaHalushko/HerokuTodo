package com.todo.dao;

import com.todo.dao.interfaces.TaskDao;
import com.todo.model.Task;
import com.todo.model.enums.Status;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TaskDaoImpl extends AbstractDao<Long, Task> implements TaskDao {

    @Override
    @Transactional
    public void delete(Long id) {
        Session session = getSession();
        session.getTransaction().begin();
        SQLQuery query = session.createSQLQuery("DELETE FROM tasks WHERE id=:id");
        query.setLong("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    @Transactional
    public List<Task> getWithFilter(String filter, int page, int perPage, Status status) {
        Criteria criteria = createEntityCriteria();
        if(filter!=null && !filter.isEmpty()){
            criteria.add(Restrictions.disjunction()
                    .add(Restrictions.ilike("name",filter, MatchMode.ANYWHERE)));
        }
        criteria.add(Restrictions.eq("status", status));
        criteria.setMaxResults(perPage);
        criteria.addOrder(Order.desc("id"));
        criteria.setFirstResult(perPage*(page-1));
        return (List<Task>) criteria.list();
    }
}
