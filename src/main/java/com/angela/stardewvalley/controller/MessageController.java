package com.angela.stardewvalley.controller;

import com.angela.stardewvalley.user.entity.MessageDetail;
import com.angela.stardewvalley.user.entity.MessageGroup;
import com.angela.stardewvalley.user.service.MessageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/msg")
@Slf4j
public class MessageController {
    @Resource
    MessageService messageService;

    @RequestMapping("/createNewMessageGroup")
    public void createNewMessageGroup(Long userId,String title,String messageLevelSystemPrompt) {
        messageService.createNewMessageGroup(userId,title,messageLevelSystemPrompt);
    }

    @RequestMapping("/getAllMessageGroupByUserId")
    public List<MessageGroup> getAllMessageGroupByUserId(Long userId) {
        return messageService.findAllMessageGroupByUserId(userId);
    }
    @RequestMapping("/getAllMessgaeDetailByMessageId")
    public List<MessageDetail> getAllMessgaeDetailByMessageId(Long messageGroupId) {
        return messageService.findAllMessgaeDetailByMessageId(messageGroupId);
    }
    /*
    为消息组设置系统提示词
     */
    @RequestMapping("/setMessageLevelSystemPrompt")
    public void setMessageLevelSystemPrompt(Long messageGroupId,String messageLevelSystemPrompt) {
        messageService.setMessageLevelSystemPrompt(messageGroupId,messageLevelSystemPrompt);
    }
}
