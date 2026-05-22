package com.angela.stardewvalley.user.service;

import com.angela.stardewvalley.user.entity.MessageDetail;
import com.angela.stardewvalley.user.entity.MessageGroup;
import com.angela.stardewvalley.user.repo.MessageDetailRepo;
import com.angela.stardewvalley.user.repo.MessageGroupRepo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/*
    消息相关
 */
@Service
public class MessgaeService {
    @Resource
    private MessageGroupRepo messageGroupRepo;
    @Resource
    private MessageDetailRepo messageDetailRepo;

    //按userid查找所有消息组并按id排序
    public List<MessageGroup> findAllMessageGroupByUserId(Long userId){
        return messageGroupRepo.findAllMessageGroupByUserId(userId);
    }

    //按所有的消息组id获取所有消息以及详情
    public List<MessageDetail> findAllMessgaeDetailByMessageId(Long messageGroupId){
        return messageDetailRepo.findAllMessgaeDetailByMessageId(messageGroupId);
    }

    //创建新组
    public void createNewMessageGroup(Long userId,String title){
        MessageGroup messageGroup = new MessageGroup();
        messageGroup.setUserId(userId);
        messageGroup.setTitle(title);
        messageGroupRepo.save(messageGroup);
    }

    //存消息
    public void saveMessage(Long messageGroupId,String role,String content){
        MessageDetail messageDetail = new MessageDetail();
        messageDetail.setMessageGroupId(messageGroupId);
        messageDetail.setRole(role);
        messageDetail.setContent(content);
        messageDetailRepo.save(messageDetail);
    }


}
