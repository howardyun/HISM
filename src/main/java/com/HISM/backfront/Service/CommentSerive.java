package com.HISM.backfront.Service;

import com.HISM.backfront.domain.Comment;
import com.HISM.backfront.domain.Dynamic;
import com.HISM.backfront.mapper.CommentMapper;
import com.HISM.backfront.mapper.DynamicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentSerive {
    @Resource
    CommentMapper commentMapper;
    @Resource
    DynamicMapper dynamicMapper;

    // 添加评论
    public boolean insertComment(Comment comment){
        try{
            commentMapper.insertComment(comment);
            Dynamic dynamic = dynamicMapper.selectDynamicByDynamicId(comment.getDynamicId());
            dynamic.setCommentNum(dynamic.getCommentNum() + 1);
            dynamicMapper.updateDynamic(dynamic);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 删除评论
    public boolean deleteComment(int commentId){
        try{

            Dynamic dynamic = dynamicMapper.selectDynamicByDynamicId(commentMapper.getDynamicId(commentId));
            commentMapper.deleteComment(commentId);
            dynamic.setCommentNum(dynamic.getCommentNum() - 1);
            dynamicMapper.updateDynamic(dynamic);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 查看某一动态评论
    public List<Comment> selectCommentById(int dynamicId){
        List<Comment> commentList = commentMapper.selectCommentById(dynamicId);
        if(commentList.isEmpty()){
            System.out.println("error, 无法查到此动态的评论");
        }
        return commentList;
    }

    // 判断某一用户是否对某一动态进行评论
    public Boolean isComment(String userId, int dynamicId){
        int commentNum = commentMapper.isComment(userId, dynamicId);
        // 若用户对动态评论数量大于1 则返回true 否则返回false
        return commentNum >= 1;
    }

    //通过评论id获取动态id
    public int getDynamicId(int commentId){
        return commentMapper.getDynamicId(commentId);
    }

    //通过评论id获取用户id
    public String getUserId(int commentId){
        return commentMapper.getUserId(commentId);
    }
}
