package org.example.springai.controller;

import org.example.springai.model.GetCapitalRequest;
import org.example.springai.model.Query;
import org.example.springai.model.Response;
import org.example.springai.service.OpenAIService;
import org.springframework.ai.model.openai.autoconfigure.OpenAiChatProperties;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private  final OpenAIService openAIService;
    private final OpenAiChatProperties openAiChatProperties;

    public ChatController(OpenAIService openAIService, OpenAiChatProperties openAiChatProperties) {
        this.openAIService = openAIService;
        this.openAiChatProperties = openAiChatProperties;
    }

    @PostMapping("/chat")
    public Response askQuery(@RequestBody Query query){
        return openAIService.getAnswer(query);
    }

    @PostMapping("/capital")
    public Response captialQuery(@RequestBody GetCapitalRequest getCapitalRequest){
        return openAIService.getCapital(getCapitalRequest);
    }
}
