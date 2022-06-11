package com.graduation.dao;

import com.graduation.entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PictureDao {
    void updatePicture(int picture,String array,int state);
    int getState();
    Picture getPicture(int id);
}
