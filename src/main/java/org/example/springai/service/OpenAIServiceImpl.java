package org.example.springai.service;


import org.example.springai.model.GetCapitalRequest;
import org.example.springai.model.GetCapitalResponse;
import org.example.springai.model.Query;
import org.example.springai.model.Response;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Objects;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatModel chatModel;

    public OpenAIServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get_capital_with_info.st")
    private Resource getCapitalWithInfoPrompt;

    @Value("classpath:templates/get_capital_JSON.st")
    private Resource getCapitalJSONPrompt;

    @Value("classpath:templates/get_capital_format.st")
    private Resource getCapitalFormat;

    @Autowired
    ObjectMapper objectMapper;

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

    @Override
    public Response getCapitalWithInfo(GetCapitalRequest getCapitalRequest){
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalWithInfoPrompt);
        Prompt prompt  = promptTemplate.create(Map.of("stateOrCountry",getCapitalRequest.stateOrCountry()));

        ChatResponse response = chatModel.call(prompt);

        return new Response(response.getResult().getOutput().getText());

    }

    @Override
    public Response getCapitalJSON(GetCapitalRequest getCapitalRequest) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalJSONPrompt);
        Prompt prompt  = promptTemplate.create(Map.of("stateOrCountry",getCapitalRequest.stateOrCountry()));

        ChatResponse response = chatModel.call(prompt);

        String responseString;

        try{
            JsonNode jsonNode = objectMapper.readTree(response.getResult().getOutput().getText());
            responseString = jsonNode.get("answer").asText();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        return new Response(responseString);
    }

    @Override
    public GetCapitalResponse getCapitalInGivenFormat(GetCapitalRequest getCapitalRequest) {

        BeanOutputConverter<GetCapitalResponse> converter = new BeanOutputConverter<>(GetCapitalResponse.class);
        String format = converter.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalFormat);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry",getCapitalRequest.stateOrCountry(),"format",format));

        ChatResponse response = chatModel.call(prompt);

        return converter.convert(Objects.requireNonNull(response.getResult().getOutput().getText()));
    }
}
