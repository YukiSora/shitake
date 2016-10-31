package moe.yukisora.shitake.model;

import java.util.ArrayList;

/**
 * Created by Delacrix on 31/10/2016.
 */

public class Game {
    private ArrayList<User> mUser = new ArrayList<>();
    private String mDeckName;
    private int mRoundNumber;

    public Game(ArrayList<User> mUser, String mDeckName, int mRoundNumber) {
        this.mUser = mUser;
        this.mDeckName = mDeckName;
        this.mRoundNumber = mRoundNumber;
    }

    public ArrayList<User> getmUser() {
        return mUser;
    }

    public void setmUser(ArrayList<User> mUser) {
        this.mUser = mUser;
    }

    public String getmDeckName() {
        return mDeckName;
    }

    public void setmDeckName(String mDeckName) {
        this.mDeckName = mDeckName;
    }

    public int getmRoundNumber() {
        return mRoundNumber;
    }

    public void setmRoundNumber(int mRoundNumber) {
        this.mRoundNumber = mRoundNumber;
    }
}
