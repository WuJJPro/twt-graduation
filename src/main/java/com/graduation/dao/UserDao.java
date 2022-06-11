package com.graduation.dao;

import com.graduation.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    User getUser(String id);
    void createUser(String id);
    void addTapNumber(String userid,int number);
    void updateLike(String content,String userid);
    void updatedisLike(String content,String userid);
}
