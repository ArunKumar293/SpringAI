package org.example.springai.service;


import org.example.springai.model.GetCapitalRequest;
import org.example.springai.model.Query;
import org.example.springai.model.Response;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatModel chatModel;

    public OpenAIServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Override
    public String getAnswer(String question) {

        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt  = promptTemplate.create();

        ChatResponse response = chatModel.call(prompt);

        return response.getResult().getOutput().getText();
    }

    @Override
    public Response getAnswer(Query query){
        PromptTemplate promptTemplate = new PromptTemplate(query.query());
        Prompt prompt  = promptTemplate.create();

        ChatResponse response = chatModel.call(prompt);

        return new Response(response.getResult().getOutput().getText());
    }

    @Override
    public Response getCapital(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt  = promptTemplate.create(Map.of("stateOrCountry",getCapitalRequest.stateOrCountry()));

        ChatResponse response = chatModel.call(prompt);

        return new Response(response.getResult().getOutput().getText());
    }
}
