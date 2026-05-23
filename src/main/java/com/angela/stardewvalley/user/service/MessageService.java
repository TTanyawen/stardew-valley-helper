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
public class MessageService {
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
    public void createNewMessageGroup(Long userId,String title,String messageLevelSystemPrompt){
        MessageGroup messageGroup = new MessageGroup();
        messageGroup.setUserId(userId);
        messageGroup.setTitle(title);
        messageGroup.setMessageLevelSystemPrompt(messageLevelSystemPrompt);
        messageGroupRepo.save(messageGroup);
    }

    //存消息
    public void saveMessage(Long messageGroupId,String role,String content){
        MessageDetail messageDetail = new MessageDetail();
        messageDetail.setMessageGroupId(messageGroupId);
        messageDetail.setRole(role);
        messageDetail.setContent(content);
        messageDetailRepo.save(messageDetail);

        //查询该消息组的消息，若只有一条消息且title为空，则将messageLevelSystemPrompt设为第一条消息的内容的前15字符(不足15则取全部)
        List<MessageDetail> messageDetails = messageDetailRepo.findAllMessgaeDetailByMessageId(messageGroupId);
        if(messageDetails.size()==1){
            MessageGroup messageGroup = messageGroupRepo.findById(messageGroupId).get();
            if(messageGroup.getTitle().isEmpty()){
                messageGroup.setTitle(messageDetails.get(0).getContent().substring(0,Math.min(messageDetails.get(0).getContent().length(),15)));
                messageGroupRepo.save(messageGroup);
            }
        }
    }

    //设置系统提示词
    public void setMessageLevelSystemPrompt(Long messageGroupId,String messageLevelSystemPrompt){
        MessageGroup messageGroup = messageGroupRepo.findById(messageGroupId).get();
        messageGroup.setMessageLevelSystemPrompt(messageLevelSystemPrompt);
        messageGroupRepo.save(messageGroup);
    }


    public void saveUserMessage(int memoryId, String message) {
//        转long
        this.saveMessage((long) memoryId, "user", message);
    }

    public void saveAssistantMessage(int memoryId, String full) {
        this.saveMessage((long) memoryId, "model", full);
    }

    //按messageGroupId查找messageGroup
    public MessageGroup getMessageGroupById(int memoryGroupId) {
        return messageGroupRepo.findById((long) memoryGroupId).get();
    }
}
