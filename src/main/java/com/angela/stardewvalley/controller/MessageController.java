package com.angela.stardewvalley.controller;

import com.angela.stardewvalley.ai.StardewValleyHelperAIService;
import com.angela.stardewvalley.ai.service.SystemPromptService;
import com.angela.stardewvalley.user.entity.MessageGroup;
import com.angela.stardewvalley.user.service.MessageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ai")
@Slf4j
public class MessageController {
    @Resource
    StardewValleyHelperAIService stardewValleyHelperAIService;

    @Resource
    MessageService messageService;
    @Resource
    SystemPromptService systemPromptService;

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
    /*
    chat接口全流程
    优化，仅调用一次模型

 */
    @GetMapping("/chat3")
    public Flux<ServerSentEvent<String>> chat3(int memoryId, String message) {
        // 保存用户消息
        messageService.saveUserMessage(memoryId, message);

        // 只调用一次模型
        Flux<String> stream = stardewValleyHelperAIService.chatStream(memoryId, message).share();

        StringBuilder fullAnswer = new StringBuilder();

        return stream
                .doOnNext(chunk -> {
                    // 收集完整回复
                    fullAnswer.append(chunk);
                })
                .doOnComplete(() -> {
                    // 保存完整模型回复
                    messageService.saveAssistantMessage(
                            memoryId,
                            fullAnswer.toString()
                    );
                })
                .map(chunk ->
                        ServerSentEvent.<String>builder()
                                .data(chunk)
                                .build());
    }

    /*
    chat接口全流程
    加入系统提示词（系统级+会话级）拼接
     */
    @GetMapping(value="/chat4",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> chat4(int memoryGroupId, String message) {

        // 保存用户消息
        messageService.saveUserMessage(memoryGroupId, message);

        // 查询会话
        MessageGroup group = messageService.getMessageGroupById(memoryGroupId);

        // 拼接系统提示词
        String systemPrompt = systemPromptService.build(
                group.getMessageLevelSystemPrompt()
        );
        log.info("systemPrompt: {}", systemPrompt);

        // 调模型（只调用一次）
        Flux<String> stream =
                stardewValleyHelperAIService
                        .chatStream(
                                (long)memoryGroupId,
                                systemPrompt,
                                message
                        )
                        .share();

        StringBuilder fullAnswer = new StringBuilder();

        return stream
                .doOnNext(chunk -> {
                    fullAnswer.append(chunk);
                })
                .doOnComplete(() -> {
                    // 保存模型回复
                    messageService.saveAssistantMessage(memoryGroupId, fullAnswer.toString());
                })
                .map(chunk ->
                        ServerSentEvent
                                .<String>builder()
                                .data(chunk)
                                .build()
                );
    }

}
