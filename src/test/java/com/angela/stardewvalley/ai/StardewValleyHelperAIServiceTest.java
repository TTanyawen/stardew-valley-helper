package com.angela.stardewvalley.ai;

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

}