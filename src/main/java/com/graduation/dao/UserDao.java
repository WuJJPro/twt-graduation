package com.graduation.dao;

import com.graduation.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    User getUser(String id);
    int[] getLike(String userid);
    int[] getDislike(String userid);
    void createUser(String id);
    void addTapNumber(String userid,int number);
    void addLike(String userid,int id);
    void addDislike(String userid,int id);
    void deleteLike(String userid,int id);
    void deleteDislike(String userid,int id);
}
