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

    /**
     * 添加评论
     * @param comment 
     * @return boolean
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2021/12/3 10:31 下午
     */
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
    
    /**
     * 删除评论
     * @param commentId
     * @return boolean
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2021/12/3 10:31 下午
     */
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


    /**
     * 查看某一动态评论
     * @param dynamicId 
     * @return java.util.List<com.HISM.backfront.domain.Comment>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2021/12/3 10:32 下午
     */
    public List<Comment> selectCommentById(int dynamicId){
        List<Comment> commentList = commentMapper.selectCommentById(dynamicId);
        if(commentList.isEmpty()){
            System.out.println("error, 无法查到此动态的评论");
        }
        return commentList;
    }

    /**
     * 判断某一用户是否对某一动态进行评论
     * @param userId
	 * @param dynamicId
     * @return java.lang.Boolean
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2021/12/3 10:33 下午
     */
    public Boolean isComment(String userId, int dynamicId){
        int commentNum = commentMapper.isComment(userId, dynamicId);
        // 若用户对动态评论数量大于1 则返回true 否则返回false
        return commentNum >= 1;
    }

    /**
     * 通过评论id获取动态id
     * @param commentId
     * @return int
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2021/12/3 10:33 下午
     */
    public int getDynamicId(int commentId){
        return commentMapper.getDynamicId(commentId);
    }

    /**
     * 通过评论id获取用户id
     * @param commentId
     * @return java.lang.String
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2021/12/3 10:33 下午
     */
    public String getUserId(int commentId){
        return commentMapper.getUserId(commentId);
    }
}
