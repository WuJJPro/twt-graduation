package com.graduation.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.graduation.dao.UserDao;
import com.graduation.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    public User getUser(String id){
        User user = userDao.getUser(id);
        if(user!=null){
        user.setlike();}
        return user;
    }
    public void createUser(String id){
        userDao.createUser(id);
    }
    public void addTapNumber(String userid,int number){
        userDao.addTapNumber(userid,number);
    }
    public void updateLike(String content,int flag,int comment,String userid){
        List<Integer> likes = JSONArray.parseArray(content, Integer.class);
        if(flag==1)likes.add(comment);
        else {
            for (int i = 0; i < likes.size(); i++) {
                if (likes.get(i).equals(comment)) {
                    likes.remove(i);
                }

            }
        }
        String arrayJson = JSON.toJSONString(likes);
        userDao.updateLike(arrayJson,userid);
    }
    public void updatedisLike(String content,int flag,int comment,String userid){
        List<Integer> likes = JSONArray.parseArray(content, Integer.class);
        if(flag==1)likes.add(comment);
        else {
            for (int i = 0; i < likes.size(); i++) {
                if (likes.get(i).equals(comment)) {
                    likes.remove(i);
                }

            }
        }
        String arrayJson = JSON.toJSONString(likes);
        userDao.updatedisLike(arrayJson,userid);
    }
}
