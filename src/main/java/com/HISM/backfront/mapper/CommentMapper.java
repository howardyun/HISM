package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.Comment;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    // 添加评论 主要添加一条评论后,还有修改该动态数据.
    public void insertComment(Comment comment);

    // 删除评论
    public void deleteComment(int commentId);

    // 查看某一动态评论
    public List<Comment> selectCommentById(int dynamicId);

    // 判断某一用户是否对某一动态进行评论
    public int isComment(String userId, int dynamicId);

    ///////////////////////////////////////////////////////////////
    //通过评论id获取动态id
    public int getDynamicId(int commentId);

    //通过评论id获取用户id
    public String getUserId(int commentId);
}
