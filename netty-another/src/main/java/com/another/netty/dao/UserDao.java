package com.another.netty.dao;

import com.another.netty.domain.UserDo;
import com.another.netty.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;


/**
 * @author liyahui
 * @create 2019-07-10
 */
public class UserDao {
    public UserDao() {
    }

    public void regist(String name,String pwd){

    }
    public List<UserDo> findUser(String name){
        Session session = HibernateUtils.getCurrentSession();
        String sql = "from UserDo where name = :name";
        Query query = session.createQuery(sql);
        query.setParameter("name",name);
        return query.list();
    }



}
