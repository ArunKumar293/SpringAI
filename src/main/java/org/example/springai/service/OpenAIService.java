package org.example.springai.service;

import org.example.springai.model.GetCapitalRequest;
import org.example.springai.model.GetCapitalResponse;
import org.example.springai.model.Query;
import org.example.springai.model.Response;

public interface OpenAIService {

    String getAnswer(String question);

    Response getAnswer(Query query);

    Response getCapital(GetCapitalRequest getCapitalRequest);

    Response getCapitalWithInfo(GetCapitalRequest getCapitalRequest);

    Response getCapitalJSON(GetCapitalRequest getCapitalRequest);

    GetCapitalResponse getCapitalInGivenFormat(GetCapitalRequest getCapitalRequest);
}
