package moe.yukisora.shitake.model;

/**
 * Created by Delacrix on 28/10/2016.
 */

public class User {
    private String mName;
    private String mProfilePicture;
    private int mGameScore;
    private int mVoteScore;

    public User(String mName, String mProfilePicture) {
        this.mName = mName;
        this.mProfilePicture = mProfilePicture;

        mGameScore = 0;
        mVoteScore = 0;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmProfilePicture() {
        return mProfilePicture;
    }

    public void setmProfilePicture(String mProfilePicture) {
        this.mProfilePicture = mProfilePicture;
    }

    public int getmGameScore() {
        return mGameScore;
    }

    public void setmGameScore(int mGameScore) {
        this.mGameScore = mGameScore;
    }

    public int getmVoteScore() {
        return mVoteScore;
    }

    public void setmVoteScore(int mVoteScore) {
        this.mVoteScore = mVoteScore;
    }
}
