package com.HISM.backfront.Service;

import com.HISM.backfront.domain.Chat;
import com.HISM.backfront.domain.User;
import com.HISM.backfront.mapper.ChatMapper;
import com.HISM.backfront.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ChatSerive {

    @Resource
    private ChatMapper chatMapper;




    /**
     * 通过发送方和接收方的id查询聊天记录
     *
     * @param sendUserId
     * @param receiveUserId
     * @return java.util.List<com.HISM.backfront.domain.Chat>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2021/12/3 10:44 下午
     */
    public List<Chat> queryChatRecording(String sendUserId, String receiveUserId) {
        return chatMapper.queryChatRecording(sendUserId, receiveUserId);
    }


    /**
     * 通过发送方id、收方的id和时间查询聊天记录
     * @param sendUserId
	 * @param receiveUserId
	 * @param chatTime
     * @return java.util.List<com.HISM.backfront.domain.Chat>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2021/12/3 10:44 下午
     */
    public List<Chat> queryChatRecordingt(String sendUserId, String receiveUserId, Date chatTime) {
        return chatMapper.queryChatRecordingt(sendUserId, receiveUserId, chatTime);
    }

    /**
     * 插入聊天记录
     * @param chat
     * @return boolean
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2021/12/3 10:44 下午
     */
    public boolean insertChat(Chat chat) {
        try {
            chatMapper.insertChat(chat);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("插入失败");
            return false;
        }
        return true;
    }
}
