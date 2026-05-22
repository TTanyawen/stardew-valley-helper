package com.angela.stardewvalley.controller;

import com.angela.stardewvalley.ai.StardewValleyHelperAIService;
import jakarta.annotation.Resource;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class MessageController {
    @Resource
    StardewValleyHelperAIService stardewValleyHelperAIService;

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
}
