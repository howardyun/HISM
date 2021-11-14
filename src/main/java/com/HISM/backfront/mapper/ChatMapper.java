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
    List<Chat> queryChatRecording(String sendUserId, String receiveUserId);
    // 通过发送方id、收方的id和时间查询聊天记录
    List<Chat> queryChatRecordingt(String sendUserId, String receiveUserId, Date ChatTime);
    // 插入聊天记录
    void insertChat(Chat chat);
    // 删除聊天记录

}
