package com.graduation.dao;

import com.graduation.entity.Comment;
import com.graduation.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;

@Mapper
@Repository

public interface CommentDao {
    void comment (String comment,int question,String userid,int timestamp,String pictures,int type,String nickName,String avatar);
    void commentLike(int id);
    void commentLikeCancel(int id);
    void commentDislikeCancel(int id);
    void commentDislike(int id);
    Comment getComment(int id);
    Question getQuestion(int id);
    List<Comment> getCommentPage(int question,int a,int b);
    List<Comment> getRandomComment(int pagesize);
    List<Comment> getSonComment(int fatherId,int a,int b);
    List<Comment> getCommentCount(int id);
    List<Comment> getSonCommentCount(int id);
    List<Question> getAllComment();
    List<Comment> getCommentPageByLike(int question,int a,int b);
    void commentShowAdd(int id);
    void commentShowDelete(int id);
}
