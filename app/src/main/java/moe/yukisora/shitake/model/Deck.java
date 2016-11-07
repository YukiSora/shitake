package moe.yukisora.shitake.model;

/**
 * Created by Delacrix on 20/10/2016.
 */

public class Deck {
    private String question;
    private String answer;

    public Deck(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
