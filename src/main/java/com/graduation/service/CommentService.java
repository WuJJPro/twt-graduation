package com.graduation.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.graduation.dao.CommentDao;
import com.graduation.dao.UserDao;
import com.graduation.entity.Comment;
import com.graduation.entity.Question;
import com.graduation.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.swing.text.html.Option;
import java.util.*;

@Service
public class CommentService {
    @Autowired
    CommentDao commentDao;
    @Autowired
    UserService userService;
//    发表评论
    public void comment(String comment, int question, String userid, List<String> pictures, int type, String nickName, String avatar) {
        int timeStamp = (int) (System.currentTimeMillis() / 1000);
//      数组->json字符串
        String strPicture = JSON.toJSONString(pictures);
        commentDao.comment(comment, question, userid, timeStamp, strPicture, type, nickName, avatar);
    }


    public void commentLike(int flag,int id) {
        if(flag==1)
        commentDao.commentLike(id);
        else if(flag==0)
            commentDao.commentLikeCancel(id);
    }

    public void commentDislike(int flag,int id) {
        if(flag==1)
            commentDao.commentDislike(id);
        else if(flag==0)
            commentDao.commentDislikeCancel(id);

    }

    public List<Comment> getSonComment(int fatherId, int curPage, int b) {
        int a = (curPage - 1) * 3;
        List<Comment> comments = commentDao.getSonComment(fatherId, a, b);



        return comments;
    }

    public List<Comment> getSonCommentPage(int fatherId, int curPage, String userid, List<Comment> comments, int b,int type) {
        List<Comment> list = getSonComment(fatherId, 1, 10000);

        for (Comment comment : list) {
            comment.setPictures();
            User user = userService.getUser(userid);
            int[] likeList = user.getLike();
            int[] dislikeList = user.getDislike();
            for (int x : likeList) {
                if (comment.getId() == x) {
                    comment.setIsLike(1);
                    break;
                } else {
                    comment.setIsLike(0);
                }
            }
            for (int x : dislikeList) {
                if (comment.getId() == x) {
                    comment.setIsDislike(1);
                    break;
                } else {
                    comment.setIsDislike(0);
                }
            }

            comments.add(comment);
            getSonCommentPage(comment.getId(), 1, userid, comments, 10000,type);
        }
        return comments;
    }

    public List<Comment> getAllComment(String userid, int id, int curPage,int type) {
        int a = (curPage - 1) * 3;
        int b = 3;
        List<Comment> comments;
        if(type==0){
            comments = commentDao.getCommentPageByLike(id, a, b);}
        else{
            comments = commentDao.getCommentPage(id, a, b);
        }
        User user = userService.getUser(userid);
        int[] likeList = user.getLike();
        int[] dislikeList = user.getDislike();
        for (Comment comment : comments) {
            comment.setPictures();
            for (int x : likeList) {
                if (comment.getId() == x) {
                    comment.setIsLike(1);
                    break;
                } else {
                    comment.setIsLike(0);
                }
            }
            for (int x : dislikeList) {
                if (comment.getId() == x) {
                    comment.setIsDislike(1);
                    break;
                } else {
                    comment.setIsDislike(0);
                }
            }
            List<Comment> c = new ArrayList<>();
            getSonCommentPage(comment.getId(), 1, userid,c,10000,1);
            List<Comment> cc = new ArrayList<>();
            if(c.size()<=4){
                comment.setContain(c);
            }
            else{
            for(int i=0;i<4;i++){
                cc.add(c.get(i));
            }
            comment.setContain(cc);}
        }
        return comments;
    }



    public int getCommentCount(int id) {
        int count = 0;
        List<Comment> comments = commentDao.getCommentCount(id);
        count += comments.size();
        for (Comment comment : comments) {
            count += getCommentCount(comment.getId());
        }
        return count;
    }

    public int getSonCommentCount(int id) {
        int count = 0;
        List<Comment> comments = commentDao.getSonCommentCount(id);
        count += comments.size();
        for (Comment comment : comments) {
            count += getCommentCount(comment.getId());
        }
        return count;
    }

    public Comment getComment(String userid, int id, int curPage,int type) {
        Comment comment = commentDao.getComment(id);
        User user = userService.getUser(userid);
        int[] likeList = user.getLike();
        int[] dislikeList = user.getDislike();
        for (int x : likeList) {
            if (comment.getId() == x) {
                comment.setIsLike(1);
                break;
            } else {
                comment.setIsLike(0);
            }
        }
        for (int x : dislikeList) {
            if (comment.getId() == x) {
                comment.setIsDislike(1);
                break;
            } else {
                comment.setIsDislike(0);
            }
        }
        comment.setContain(getAllComment(userid, id, curPage,type));
        return comment;
    }
//    外层方法
    public JSONObject getComment2(String userid,int id,int curpage,int type){
        Comment comment = getComment(userid,id,curpage,type);
        JSONObject json = (JSONObject) JSON.toJSON(comment);
        json.put("CommentNumber", getCommentCount(id));
        json.remove("question");
        json.remove("nickname");
        json.remove("id");
        json.remove("timestamp");
        json.remove("type");
        json.remove("avatar");
        json.remove("pictures");
        return json;
    }

    public JSONObject getSonComments(String userid, int id, int curPage,int type) {
        List<Comment> comments = new ArrayList<>();
        List<Comment> s = getSonCommentPage(id, curPage, userid, comments, 3,type);
        Comment comment = commentDao.getComment(id);
        User user = userService.getUser(userid);
        int[] likeList = user.getLike();
        int[] dislikeList = user.getDislike();
        for (int x : likeList) {
            if (comment.getId() == x) {
                comment.setIsLike(1);
                break;
            } else {
                comment.setIsLike(0);
            }
        }
        for (int x : dislikeList) {
            if (comment.getId() == x) {
                comment.setIsDislike(1);
                break;
            } else {
                comment.setIsDislike(0);
            }
        }
        if(type==0){
            s.sort(Comparator.comparing(Comment::getLikeNumber).reversed());
        }
        else {
            s.sort(Comparator.comparing(Comment::getId).reversed());
        }
        List<Comment> cc = new ArrayList<>();
        if(s.size()>=3*curPage){
            for(int i=3*(curPage-1);i<3*curPage;i++){
                cc.add(s.get(i));

            }
            comment.setContain(cc);
        }
        else{
            for(int i=3*(curPage-1);i<s.size();i++){
                cc.add(s.get(i));

            }
            comment.setContain(cc);}

        JSONObject json = (JSONObject) JSON.toJSON(comment);
        json.put("CommentNumber", getSonCommentCount(id));
        json.remove("id");
        json.remove("type");
        return json;
    }

    public List<JSONObject> getAllComments(String userid) {
        List<Question> questions = commentDao.getAllComment();
        List<JSONObject> list = new ArrayList<>();
        for (Question comment : questions) {
            User user = userService.getUser(userid);
            int[] likeList = user.getLike();
            int[] dislikeList = user.getDislike();
            for (int x : likeList) {
                if (comment.getId() == x) {
                    comment.setIsLike(1);
                    break;
                } else {
                    comment.setIsLike(0);
                }
            }
            for (int x : dislikeList) {
                if (comment.getId() == x) {
                    comment.setIsDislike(1);
                    break;
                } else {
                    comment.setIsDislike(0);
                }
            }
            JSONObject json = (JSONObject) JSON.toJSON(comment);
            json.put("CommentNumber", getCommentCount(comment.getId()));
            list.add(json);

        }

        return list;
    }
    private Comment matchQuestion(Comment comment){
        if(comment.getType()==-1){
            return comment;
        }
        else{
            Comment com = commentDao.getComment(comment.getQuestion());
            return matchQuestion(com);
        }
    }
    public List<JSONObject> getRandomComment(int pagesize){
        List<Comment> comments = commentDao.getRandomComment(pagesize);
        List<JSONObject> questions = new ArrayList<>();
        for(Comment comment:comments){
            int id = matchQuestion(comment).getId();
            JSONObject commentJson =(JSONObject) JSON.toJSON(comment);
            commentJson.remove("contain");
            commentJson.remove("isLike");
            commentJson.remove("isDislike");
            JSONObject json =(JSONObject) JSON.toJSON(commentDao.getQuestion(id));
            json.remove("isLike");
            json.remove("isDislike");
            json.put("contain",commentJson);
            questions.add(json);
        }
        return questions;

    }
}

