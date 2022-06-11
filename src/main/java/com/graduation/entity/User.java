package com.graduation.entity;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class User {
  private String id;
  @JSONField(serialize = false)
  private String likeList;
  @JSONField(serialize = false)
  private String dislikeList;
  private int tapNumber;
  private int[] like;
  private int[] dislike;

  public void setlike() {
    like = JSON.parseObject(likeList, int[].class);
    dislike = JSON.parseObject(dislikeList, int[].class);
  }
}
