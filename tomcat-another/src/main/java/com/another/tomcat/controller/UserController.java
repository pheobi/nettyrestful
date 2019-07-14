package com.another.tomcat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.another.tomcat.domain.UserDo;
import com.another.tomcat.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liyahui
 * @create 2019-07-06
 */
@Controller
@RequestMapping(value="/user",produces = "text/json;charset=utf-8")
public class UserController {

    UserService uservice = new UserService();

    @RequestMapping("/login")
    @ResponseBody
    public String login(@RequestParam String username, @RequestParam String pwd){
        JSONObject resultJson = new JSONObject();
        Map<String, String> loginResult = new HashMap<String, String>();
        boolean login = uservice.login(username, pwd);
        if(login){
            resultJson.put("code", 200);
            resultJson.put("msg", "登录成功");
        }else{
            resultJson.put("code", 201);
            resultJson.put("msg", "用户不存在");
        }

        //resultJson.put("result", loginResult);

        return JSONObject.toJSONString(resultJson);
    }

    @RequestMapping("/regist")
    @ResponseBody
    public String regist(String username,String pwd){
        JSONObject resultJson = uservice.regist(username, pwd);
        return JSONObject.toJSONString(resultJson);
    }

    @RequestMapping("/list")
    @ResponseBody
    public String listUser(){
        List<UserDo> userDoList = uservice.listUser();
        return JSON.toJSONString(userDoList);
    }
}
