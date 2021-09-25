package com.HISM.backfront.Service;

import com.HISM.backfront.domain.Comment;
import com.HISM.backfront.mapper.CommentMapper;
import org.apache.ibatis.jdbc.Null;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentSerive {
    @Resource
    CommentMapper commentMapper;

    // 添加评论
    public boolean insertComment(Comment comment){
        try{
            commentMapper.insertComment(comment);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 删除评论
    public boolean deleteComment(int commentId){
        try{
            commentMapper.deleteComment(commentId);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 查看某一动态评论
    public List<Comment> selectCommentByD(int dynamicId){
        List<Comment> commentList = commentMapper.selectCommentByD(dynamicId);
        if(commentList.isEmpty()){
            System.out.println("error, 无法查到此动态的评论");
        }
        return commentList;
    }
}
