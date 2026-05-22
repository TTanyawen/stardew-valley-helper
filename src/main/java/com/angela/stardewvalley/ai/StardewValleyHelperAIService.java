package com.angela.stardewvalley.ai;


import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

public interface StardewValleyHelperAIService {
    @SystemMessage(fromResource = "system-prompt.txt")
    String chat(String userMessage);
    //RAG,返回封装后的结果
    @SystemMessage(fromResource = "system-prompt.txt")
    Result<String> chatWithRag(String userMessage);
    // 流式对话
    Flux<String> chatStream(@MemoryId int memoryGroupId, @UserMessage String messageContext);

}
