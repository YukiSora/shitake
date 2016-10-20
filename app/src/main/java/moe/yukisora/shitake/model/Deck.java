package moe.yukisora.shitake.model;

/**
 * Created by Delacrix on 20/10/2016.
 */

public class Deck {
    private String mQuestion;
    private String mAnswer;

    public Deck(String mQuestion, String mAnswer) {
        this.mQuestion = mQuestion;
        this.mAnswer = mAnswer;
    }

    public String getmQuestion() {
        return mQuestion;
    }

    public void setmQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    public String getmAnswer() {
        return mAnswer;
    }

    public void setmAnswer(String mAnswer) {
        this.mAnswer = mAnswer;
    }
}
