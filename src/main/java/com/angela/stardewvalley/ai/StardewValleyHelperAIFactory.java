package com.angela.stardewvalley.ai;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StardewValleyHelperAIFactory {
    @Resource
    private ChatModel qwenChatModel;

    @Bean
    public StardewValleyHelperAIService createStardewValleyHelperAI(){
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        StardewValleyHelperAIService stardewValleyHelperAIService = AiServices.builder(StardewValleyHelperAIService.class)
                .chatModel(qwenChatModel)
                .chatMemory(chatMemory)
                .build();
        return stardewValleyHelperAIService;
    }
}
