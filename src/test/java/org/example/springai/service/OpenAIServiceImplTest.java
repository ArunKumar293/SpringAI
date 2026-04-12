package org.example.springai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OpenAIServiceImplTest {

    @Autowired
    private OpenAIServiceImpl openAIService;

    @Test
    void getAnswer() {

        String answer = openAIService.getAnswer("what is life");

        System.out.println(answer);
    }
}