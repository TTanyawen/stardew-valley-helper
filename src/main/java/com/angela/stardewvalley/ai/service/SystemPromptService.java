package com.angela.stardewvalley.ai.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class SystemPromptService {

    private final String globalPrompt;

    public SystemPromptService() {
        this.globalPrompt = loadGlobalPrompt();
    }

    public String build(String conversationPrompt){

        if(conversationPrompt == null ||
                conversationPrompt.isBlank()) {

            return globalPrompt;
        }

        return globalPrompt
                + "\n\n"
                + conversationPrompt;
    }

    private String loadGlobalPrompt(){

        try{
            ClassPathResource resource =
                    new ClassPathResource(
                            "system-prompt.txt"
                    );

            return new String(
                    resource.getInputStream()
                            .readAllBytes(),
                    StandardCharsets.UTF_8
            );

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
