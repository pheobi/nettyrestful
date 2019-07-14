package com.another.tomcat.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


/**
 * @author liyahui
 * @create 2019-07-11
 */
public class HibernateUtils {
    private static final Configuration CONFIG;
    private static final SessionFactory FACTORY;

    //编写静态代码块
    static {
        //加载xml的配置文件
        CONFIG=new Configuration().configure();
        FACTORY = CONFIG.buildSessionFactory();
    }
    /**从工厂中获取session对象*/
    public static Session getSession(){
        return FACTORY.openSession();
    }
    /**从threadlocal中获取session对象
     **/
    public static Session getCurrentSession(){
        return FACTORY.getCurrentSession();
    }


}
