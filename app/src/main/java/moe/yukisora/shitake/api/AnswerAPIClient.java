package moe.yukisora.shitake.api;

import java.util.HashMap;

import moe.yukisora.shitake.ui.game.PendingFragment;

public class AnswerAPIClient {
    private static AnswerAPIClient answerAPIClient;
    private HashMap<String, String> answers;
    private String question;

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

    public void addAnswer(String address, String answer) {
        answers.put(address, answer);
        if (PendingFragment.getFragmentTask() != null)
            PendingFragment.getFragmentTask().done(address);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
