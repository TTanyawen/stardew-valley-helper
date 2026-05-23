package com.angela.stardewvalley.user.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MessageServiceTest {
    @Resource
    private MessageService messageService;

    @Test
    void findAllMessageGroupByUserId() {
        //打印所有消息组的每个字段
        messageService.findAllMessageGroupByUserId(1L).forEach(messageGroup -> {
            System.out.println(messageGroup.getId());
            System.out.println(messageGroup.getUserId());
            System.out.println(messageGroup.getTitle());
        });
    }

    @Test
    void findAllMessgaeDetailByMessageId() {
        //打印所有消息详情
        messageService.findAllMessgaeDetailByMessageId(1L).forEach(messageDetail -> {
            System.out.println(messageDetail.getId());
            System.out.println(messageDetail.getMessageGroupId());
            System.out.println(messageDetail.getRole());
            System.out.println(messageDetail.getContent());
        });
    }

    @Test
    void saveMessage() {
//        messgaeService.saveMessage(1L, "user", "谢谢你的回答");
//        messgaeService.saveMessage(1L, "model", "不用谢，更多星露谷知识敬请提问！");
        messageService.saveMessage(2L, "user", "请给我一个星露谷的npc");
    }

    @Test
    void createNewMessageGroup() {
        messageService.createNewMessageGroup(1L, "星露谷npc提问",null);
    }

    @Test
    void setMessageLevelSystemPrompt() {
        messageService.setMessageLevelSystemPrompt(1L, "你是一个星露谷的npc，请回答我的问题");
    }
}