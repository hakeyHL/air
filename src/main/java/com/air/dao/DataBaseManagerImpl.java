package com.air.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * Created by linux on 2017年03月29日.
 * Time 23:39
 */
@Component
public class DataBaseManagerImpl<T> implements IDataBaseManager<T> {
    @Resource(name = "org.springframework.orm.hibernate4.HibernateTemplate")
    private HibernateTemplate hibernateTemplate;

    public <ID extends Serializable> T get(Class<T> tClass, ID id) {
        return hibernateTemplate.get(tClass, id);
    }

    public <ID extends Serializable> ID create(T t) {
        Serializable id = hibernateTemplate.save(t);
        return (ID) id;
    }

    public int batchSave(final List<T> array) {
        return (Integer) hibernateTemplate.execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session) throws HibernateException {
//                        Transaction tx = session.beginTransaction();
                        for (int i = 0; i < array.size(); i++) {
                            session.save(array.get(i));
                            //强制提交
                            if ((i + 1) % 30 == 0) {
                                session.flush();
                            }
                        }
//                        tx.commit();
                        session.close();
                        return array.size();
                    }
                }
        );
    }

    public int batchDelete(final String jpaSql, final String[] ids) {
        return (Integer) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(jpaSql);
                query.setParameterList("ids", ids);
                int i = query.executeUpdate();
                session.flush();
//                session.clear();
                session.close();
                return i;
            }
        });
    }

    public <ID extends Serializable> void delete(Class<T> tClass, ID id) {
        hibernateTemplate.delete(tClass);
    }

    public void update(Object t) {
        hibernateTemplate.update(t);
    }

    public <T> List<T> query(String jpaSql) {
        return this.query(jpaSql, null, 1, 20);
    }

    public <T1> List<T1> query(String jpaSql, List params) {
        return this.query(jpaSql, params, 1, 20);
    }

    public <T> List<T> query(final String jpaSql, final List params, final int pageNumber, final int pageSize) {
        return hibernateTemplate.execute(new HibernateCallback<List<T>>() {
            public List<T> doInHibernate(org.hibernate.Session session) throws HibernateException {
                Query query = session.createQuery(jpaSql);
                query.setFirstResult(pageNumber * pageSize - pageSize);
                query.setMaxResults(pageSize);
                if (params != null) {
                    int i = 0;
                    for (Object obj : params) {
                        query.setParameter("" + i, obj);
                        i++;
                    }
                }
                return query.list();
            }
        });
    }

}
