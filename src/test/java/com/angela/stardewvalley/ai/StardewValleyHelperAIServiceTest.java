package com.angela.stardewvalley.ai;

import dev.langchain4j.service.Result;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StardewValleyHelperAIServiceTest {
    @Resource
    private StardewValleyHelperAIService stardewValleyHelperAIService;
    @Test
    void chat(){
        System.out.println(stardewValleyHelperAIService.chat("你好,我是Angela，我刚刚开始星露谷游戏的游玩。"));
    }


    @Test
    void chatWithMemory() {
        String res = stardewValleyHelperAIService.chat("你好,我是Angela");
        System.out.println(res);
        res = stardewValleyHelperAIService.chat("你好,我是谁来着?");
        System.out.println(res);
    }

    @Test
    void chatWithRag(){
        Result<String> result=stardewValleyHelperAIService.chatWithRag("蛋节怎么过？");
        System.out.println(result.sources());
        System.out.println(result.content());
    }
    @Test
    void chatWithMcp() {
        String result = stardewValleyHelperAIService.chat("什么是懂鸟，懂鸟有什么功能？？");
        System.out.println(result);
    }

}