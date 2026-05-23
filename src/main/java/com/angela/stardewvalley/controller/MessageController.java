package com.angela.stardewvalley.controller;

import com.angela.stardewvalley.ai.StardewValleyHelperAIService;
import com.angela.stardewvalley.user.service.MessageService;
import jakarta.annotation.Resource;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ai")
public class MessageController {
    @Resource
    StardewValleyHelperAIService stardewValleyHelperAIService;

    @Resource
    MessageService messageService;

    /*
        仅用于测试流式输出
     */
    @GetMapping("/testchat")
    public Flux<ServerSentEvent<String>> chat(int memoryId, String message) {
        return stardewValleyHelperAIService.chatStream(memoryId, message)
                .map(chunk -> ServerSentEvent.<String>builder()
                        .data(chunk)
                        .build());
    }
    /*
        chat接口全流程
        todo：系统提示词（系统级+会话级）拼接
     */
    @GetMapping("/chat2")
    public Flux<ServerSentEvent<String>> chat2(int memoryId, String message) {
        //存用户消息
        messageService.saveUserMessage(memoryId, message);

        Mono<String> fullAnswerMono =
                stardewValleyHelperAIService.chatStream(memoryId, message)
                        .reduce(new StringBuilder(), StringBuilder::append)
                        .map(StringBuilder::toString)
                        .doOnNext(full -> {
                            messageService.saveAssistantMessage(memoryId, full);//存模型消息
                        });

        fullAnswerMono.subscribe(); // 触发执行

        return stardewValleyHelperAIService.chatStream(memoryId, message)
                .map(chunk -> ServerSentEvent.<String>builder()
                        .data(chunk)
                        .build());
    }
}
