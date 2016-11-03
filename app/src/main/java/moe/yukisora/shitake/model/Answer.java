package moe.yukisora.shitake.model;

/**
 * Created by Delacrix on 03/11/2016.
 */

public class Answer {
    private String mUsername;
    private String mContent;

    public Answer(String mUsername, String mContent) {
        this.mUsername = mUsername;
        this.mContent = mContent;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }
}
