package com.angela.stardewvalley.ai;


import dev.langchain4j.service.SystemMessage;

public interface StardewValleyHelperAIService {
    @SystemMessage(fromResource = "system-prompt.txt")
    String chat(String userMessage);
}
