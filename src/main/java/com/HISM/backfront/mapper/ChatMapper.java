package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.Chat;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface ChatMapper {
    // 通过发送方和接收方的id查询聊天记录
    /**
     * 通过Id查询管理员账户
     * @param sendUserId
     * @param receiveUserId
     * @return List<com.HISM.backfront.domain.Administrator></>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18
     */
    List<Chat> queryChatRecording(String sendUserId, String receiveUserId);


    /**
     * 通过发送方id、收方的id和时间查询聊天记录
     * @param sendUserId
	 * @param receiveUserId
	 * @param ChatTime
     * @return java.util.List<com.HISM.backfront.domain.Chat>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:03 下午
     */
    List<Chat> queryChatRecordingt(String sendUserId, String receiveUserId, Date ChatTime);


    /**
     *
     * 插入聊天记录
     * @param chat
     * @return void
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:04 下午
     */
    void insertChat(Chat chat);


}
