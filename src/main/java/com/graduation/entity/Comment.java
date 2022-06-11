package com.graduation.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class Comment {

  private int id;
  private String comment;
  private int timestamp;
  private int question;
  private int likeNumber;
  private int dislikeNumber;
  @JSONField(serialize = false)
  private String userid;
  @JSONField(serialize = false)
  private String picture;
  private List<String> pictures;
  public void setPictures(){
    pictures = JSONArray.parseArray(picture, String.class);
  }
  private int isLike;
  private int isDislike;
  @JSONField(serialize = false)
  private int type;
  private List<Comment> contain;
  private String nickname;
  private String avatar;
  private int isShow;
  @JSONField(serialize = false)
  private Integer rootQuestion;




}
