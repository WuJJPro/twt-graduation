package com.graduation.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.graduation.component.JsonResult;
import com.graduation.component.QiniuPicture;
import com.graduation.dao.PictureDao;
import com.graduation.entity.Picture;
import com.graduation.service.PictureService;
import com.graduation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class PictureController {
    @Autowired
    PictureService pictureService;
    @Autowired
    UserService userService;
    @Autowired
    QiniuPicture qiniuService;
    @Autowired
    PictureDao pictureDao;
    @PostMapping("api/picture/update")
    public JsonResult<Map<String,Integer>> a(@RequestBody() JSONObject json, @RequestHeader("token") String userid){
        int picture = (int) json.get("picture");
        List<int[]> array_raw = JSON.parseArray(json.getString("array"), int[].class);
        int[][] array = new int[array_raw.size()][2];
        for (int i=0;i<array_raw.size();i++){
            for(int j=0;j< array_raw.get(i).length;j++){
                    array[i][j] = array_raw.get(i)[j];
            }
        }
        int tapNumber = pictureService.updatePicture(picture,array);
        userService.addTapNumber(userid,tapNumber);
        Map<String,Integer> map = new HashMap<>();
        map.put("tapNumber",tapNumber);
        return new JsonResult<>(map);
    }
    @GetMapping("api/picture/get")
    public JsonResult<Picture> getPicture(){
        int id = pictureDao.getState();
        return new JsonResult<>(pictureService.getPicture(id));
    }

    @PostMapping("api/picture/upload")
    public JsonResult<String> upload(@RequestParam("file") MultipartFile uploadFile) {
        if (!uploadFile.isEmpty()) {
            return new JsonResult<>(qiniuService.saveFile(uploadFile));
        }
        return new JsonResult<>("上传失败!");
    }

    @PostMapping("api/picture/uploads")
    public JsonResult<List> upload(@RequestParam("files") MultipartFile[] uploadFiles, HttpServletRequest req) {
        if (!ObjectUtils.isEmpty(uploadFiles) && uploadFiles.length > 0) {
            return new JsonResult<>(qiniuService.saveFile(uploadFiles));
        }
        return new JsonResult<>(Collections.singletonList("上传失败!"));
    }
    @GetMapping("/demo")
    public String demo(){
        return "sda";
    }
}
