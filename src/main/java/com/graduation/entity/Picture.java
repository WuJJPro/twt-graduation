package com.graduation.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Picture {
    private int id;
    @JSONField(serialize = false)
    private String array;
    private int state;
    private int[][] arrayList;

    public void setArrayList() {
        arrayList = JSON.parseObject(array, int[][].class);
    }
}
