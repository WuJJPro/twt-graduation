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
        user.setLike(userDao.getLike(id));
        user.setDislike(userDao.getDislike(id));
        return user;
    }
    public void createUser(String id){
        userDao.createUser(id);
    }
    public void addTapNumber(String userid,int number){
        userDao.addTapNumber(userid,number);
    }
    public void updateLike(int flag,String userid,int id){
        if(flag==1){
            userDao.addLike(userid,id);
        }
        else {
            userDao.deleteLike(userid,id);
        }
    }
    public void updatedisLike(int flag,String userid,int id){

        if(flag==1){
            userDao.addDislike(userid,id);
        }
        else {
            userDao.deleteDislike(userid,id);
        }
    }
}
