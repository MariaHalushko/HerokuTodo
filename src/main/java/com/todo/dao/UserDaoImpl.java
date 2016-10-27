package com.todo.dao;

import com.todo.dao.interfaces.UserDao;
import com.todo.model.Task;
import com.todo.model.User;
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
public class UserDaoImpl extends AbstractDao<Long, User> implements UserDao {

    @Override
    @Transactional
    public List<Task> getUserTasks(Long userId) {
        Session session = getSession();
        SQLQuery query = session.createSQLQuery("Select * FROM tasks WHERE user_id=:userId").addEntity(Task.class);
        query.setLong("userId", userId);
        return query.list();
    }

    @Override
    @Transactional
    public List<User> getWithFilter(String filter, int page, int perPage) {
        Criteria criteria = createEntityCriteria();
        if(filter!=null && !filter.isEmpty()){
            criteria.add(Restrictions.disjunction()
                    .add(Restrictions.ilike("firstname",filter, MatchMode.ANYWHERE))
                    .add(Restrictions.ilike("lastname",filter, MatchMode.ANYWHERE)));
        }
        criteria.setMaxResults(perPage);
        criteria.addOrder(Order.desc("id"));
        criteria.setFirstResult(perPage*(page-1));
        return (List<User>) criteria.list();
    }
}
