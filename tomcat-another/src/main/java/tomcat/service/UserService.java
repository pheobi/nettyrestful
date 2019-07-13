package tomcat.service;

import com.alibaba.fastjson.JSONObject;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.util.CollectionUtils;
import tomcat.dao.UserDao;
import tomcat.domain.UserDo;
import tomcat.utils.HibernateUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


/**
 * @author liyahui
 * @create 2019-07-10
 */
public class UserService {

    public UserService() {
    }

    UserDao userDao = new UserDao();
    public JSONObject regist(String userName ,String pwd) {
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();
        JSONObject resultJson = new JSONObject();
        try {
            List<UserDo> user = userDao.findUser(userName);
            if(CollectionUtils.isEmpty(user)){
                UserDo userDo = new UserDo();
                userDo.setPwd(pwd);
                userDo.setName(userName);
                Serializable save = session.save(userDo);
                System.out.println(save);
                resultJson.put("code", 200);
                resultJson.put("msg", "注册成功");
            }else{
                resultJson.put("code", 203);
                resultJson.put("msg", "用户已存在");
            }
            tr.commit();
            //session.close();
        }catch (Exception e){
            resultJson.put("code", 202);
            resultJson.put("msg", "注册失败");
            e.printStackTrace();
        }finally {
            return resultJson;
        }
    }

    public boolean login(String userName ,String pwd){
        Session session = HibernateUtils.getCurrentSession();
        Transaction tr = session.beginTransaction();
        boolean b =false;
        try{
            List<UserDo> user = userDao.findUser(userName);
            if(!CollectionUtils.isEmpty(user)&&Objects.nonNull(user.get(0))){
                b=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        tr.commit();
        return b;
    }
}
