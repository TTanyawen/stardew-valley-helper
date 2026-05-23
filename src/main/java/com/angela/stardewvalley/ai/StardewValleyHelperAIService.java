package com.angela.stardewvalley.ai;


import dev.langchain4j.service.*;
import reactor.core.publisher.Flux;

public interface StardewValleyHelperAIService {
    @SystemMessage(fromResource = "system-prompt.txt")
    String chat(String userMessage);
    //RAG,返回封装后的结果
    @SystemMessage(fromResource = "system-prompt.txt")
    Result<String> chatWithRag(String userMessage);
    // 流式对话
    Flux<String> chatStream(@MemoryId int memoryGroupId, @UserMessage String messageContext);

    //带系统提示词做参数的chatStream，这样可以动态构造提示词
    @SystemMessage("{{systemPrompt}}")
    Flux<String> chatStream(
            @MemoryId Long memoryGroupId,
            @V("systemPrompt") String systemPrompt,
            @UserMessage String messageContext
    );

}
