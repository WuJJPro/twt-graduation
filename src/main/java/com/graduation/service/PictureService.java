package com.graduation.service;

import com.alibaba.fastjson.JSON;
import com.graduation.dao.PictureDao;
import com.graduation.entity.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class PictureService {
    @Autowired
    PictureDao pictureDao;

    public int updatePicture(int picture,int[][] array){
        int count = 0;
        int oriCount = 0;
        int id = picture;
        Picture oriPicture = getPicture(id);
        int[][] oriArray = oriPicture.getArrayList();
        for(int i=0;i<oriArray.length;i++){
            for(int j=0;j<oriArray[i].length;j++){
                if(oriArray[i][j]==0){
                    oriCount++;
                }
            }
        }

        for(int j=0;j<array.length;j++){
            oriArray[array[j][0]][array[j][1]] = 1;
        }
        for(int i=0;i<oriArray.length;i++){
            for(int j=0;j<oriArray[i].length;j++){
                if(oriArray[i][j]==0){
                    count++;
                }
            }
        }
        String arrayJson = JSON.toJSONString(oriArray);
        pictureDao.updatePicture(picture,arrayJson,count);
        return oriCount - count;
    }
    public Picture getPicture(int id){
        Picture picture = pictureDao.getPicture(id);
        picture.setArrayList();
        return picture;
    }
}
