package com.graduation.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class Question {

    private int id;
    private String comment;
    private int likeNumber;
    private int isLike;
    private int isDislike;



}