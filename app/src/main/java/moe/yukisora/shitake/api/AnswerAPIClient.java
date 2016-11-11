package moe.yukisora.shitake.api;

import java.util.HashMap;

public class AnswerAPIClient {
    private static AnswerAPIClient answerAPIClient;
    private HashMap<String, String> answers;

    private AnswerAPIClient() {
        answers = new HashMap<>();
    }

    public static AnswerAPIClient newInstance() {
        answerAPIClient = new AnswerAPIClient();

        return answerAPIClient;
    }

    public static AnswerAPIClient getInstance() {
        if (answerAPIClient == null)
            answerAPIClient = new AnswerAPIClient();

        return answerAPIClient;
    }

    public HashMap<String, String> getAnswers() {
        return answers;
    }
}
