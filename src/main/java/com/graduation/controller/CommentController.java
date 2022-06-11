package com.graduation.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.graduation.component.JsonResult;
import com.graduation.dao.CommentDao;
import com.graduation.entity.Comment;
import com.graduation.entity.User;
import com.graduation.service.CommentService;
import com.graduation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    CommentDao commentDao;
    @PostMapping("api/comment")
    public JsonResult<String> comment(@RequestBody() JSONObject json,
                                      @RequestHeader("token") String userid){
        int question = (int) json.get("question");
        String comment = (String) json.get("comment");
        int type = (int)json.get("type");
        String nickName = (String) json.get("nickname") ;
        String avatar = (String) json.get("avatar");
        List<String> pictures = JSON.parseArray(json.getString("picture"), String.class);
        commentService.comment(comment,question,userid,pictures,type,nickName,avatar);
        return new JsonResult<>("ok");
    }
    @GetMapping("api/comment/like")
    public JsonResult<String> commentLike(int flag,int id,@RequestHeader("token") String userid){
        commentService.commentLike(flag,id);
        userService.updateLike(flag,userid,id);
        return new JsonResult<>("ok");
    }
    @GetMapping("api/comment/dislike")
    public JsonResult<String> commentDislike(int flag,int id,@RequestHeader("token") String userid){
        commentService.commentDislike(flag,id);
        userService.updatedisLike(flag,userid,id);
        return new JsonResult<>("ok");
    }
    @GetMapping("api/comment/get")
    public JsonResult<JSONObject> getAllCommnet(@RequestHeader("token") String userid,int id,int curpage,int type){

        return new JsonResult<>(commentService.getComment2(userid,id,curpage,type));
    }
    @GetMapping("api/comment/son/get")
    public JsonResult<JSONObject> getSonCommnet(@RequestHeader("token") String userid,int id,int curpage,int type){

        return new JsonResult<>(commentService.getSonComments(userid,id,curpage,type));
    }
//    @GetMapping("api/comment/count")
//    public JsonResult<Integer> getCommnetCount(int id){
//        return new JsonResult<>(commentService.getCommentCount(id));
//    }
//    @GetMapping("api/comment/son/count")
//    public JsonResult<Integer> getSonCommnetCount(int id){
//        List<Comment> comments = new ArrayList<>();
//        return new JsonResult<>(commentService.getSonCommentCount(id));
//    }

    @GetMapping("api/comment/getrandom")
    public JsonResult<List> getCommentPage(int pagesize){
        List<JSONObject> jsonObjects = commentService.getRandomComment(pagesize);
        return new JsonResult<>(jsonObjects);
    }
    @GetMapping("api/comment/show/add")
    public JsonResult<String> commentShowAdd(int id){
        commentDao.commentShowAdd(id);
        return new JsonResult<>("ok");
    }
    @GetMapping("api/comment/show/delete")
    public JsonResult<String> commentShowDelete(int id){
        commentDao.commentShowDelete(id);
        return new JsonResult<>("ok");
    }
    @GetMapping("api/comment/getall")
    public JsonResult<List> getAllComment(@RequestHeader("token") String userid){

        return new JsonResult<>(commentService.getAllComments(userid));
    }

}
