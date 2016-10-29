package moe.yukisora.shitake.model;

/**
 * Created by Delacrix on 20/10/2016.
 */

public class Deck {
    private String mName;
    private String mQuestion;
    private String mAnswer;

    public Deck(String mName, String mQuestion, String mAnswer) {
        this.mName = mName;
        this.mQuestion = mQuestion;
        this.mAnswer = mAnswer;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
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
