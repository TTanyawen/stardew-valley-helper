package com.angela.stardewvalley.ai;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StardewValleyHelperAITest {
    @Autowired
    private StardewValleyHelperAI stardewValleyHelperAI;

    @Test
    void chat(){
        stardewValleyHelperAI.chat("你好，我Angela");
    }
}