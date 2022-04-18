package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.Comment;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    /**
     * 添加评论 主要添加一条评论后,还有修改该动态数据.
     * @param comment
     * @return void
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:05 下午
     */
    public void insertComment(Comment comment);

    /**
     * 删除评论
     * @param commentId
     * @return void
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:05 下午
     */
    public void deleteComment(int commentId);

    /**
     * 查看某一动态评论
     * @param dynamicId
     * @return java.util.List<com.HISM.backfront.domain.Comment>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:05 下午
     */
    public List<Comment> selectCommentById(int dynamicId);

    /**
     * 判断某一用户是否对某一动态进行评论
     * @param userId
     * @param dynamicId
     * @return int
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:06 下午
     */
    public int isComment(String userId, int dynamicId);


    /**
     * 通过评论id获取动态id
     * @param commentId
     * @return int
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:07 下午
     */
    public int getDynamicId(int commentId);


    /**
     * 通过评论id获取用户id
     * @param commentId
     * @return String
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:07 下午
     */
    public String getUserId(int commentId);
}
