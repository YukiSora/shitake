package moe.yukisora.shitake.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import moe.yukisora.shitake.model.Answer;
import moe.yukisora.shitake.model.User;

import static android.content.ContentValues.TAG;

/**
 * Created by Delacrix on 28/10/2016.
 */

public class GameAPIClient {
    private static GameAPIClient sSharedInstance;

    private ArrayList<User> mUser = new ArrayList<>();
    private ArrayList<Answer> mAnswer = new ArrayList<>();

    private int mRoundNumber;

    private SharedPreferences mSharedPreferences;

    // Singleton New Instance
    public static synchronized GameAPIClient newInstance(@NonNull Context context) {
        if (sSharedInstance == null) {
            sSharedInstance = new GameAPIClient(context);
        }

        return sSharedInstance;
    }

    // Singleton Get Instance
    public static synchronized GameAPIClient getInstance() {
        return sSharedInstance;
    }

    // Constructor
    private GameAPIClient(@NonNull Context context) {
        mSharedPreferences = context.getSharedPreferences("preference-key", Context.MODE_PRIVATE);

        populateUsers(context, "sample_user");
        populateAnswers(context, "sample_answer");
    }

    // Method - Load Users

    private void populateUsers(Context context, String userJSON) {
        try {
            JSONObject jsonRootObject = new JSONObject(String.valueOf(loadJSONFromAsset(context, userJSON)));
            JSONArray jsonArray = jsonRootObject.optJSONArray(userJSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                mUser.add(new User(jsonObject.optString("Username"), jsonObject.optString("Profile Picture")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateAnswers(Context context, String userJSON) {
        try {
            JSONObject jsonRootObject = new JSONObject(String.valueOf(loadJSONFromAsset(context, userJSON)));
            JSONArray jsonArray = jsonRootObject.optJSONArray(userJSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                mAnswer.add(new Answer(jsonObject.optString("Username"), jsonObject.optString("Answer")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private StringBuilder loadJSONFromAsset(Context context, String userJSON) {
        StringBuilder buf = new StringBuilder();

        try {
            InputStream json = context.getAssets().open(userJSON + ".json");
            BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }

            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return buf;
    }

    // Method - Start Game
    public void startGame(){
        setmRoundNumber(1);
    }

    // Getters and Setters

    public ArrayList<User> getmUser() {
        return mUser;
    }

    public void setmUser(ArrayList<User> mUser) {
        this.mUser = mUser;
    }

    public ArrayList<Answer> getmAnswer() {
        return mAnswer;
    }

    public void setmAnswer(ArrayList<Answer> mAnswer) {
        this.mAnswer = mAnswer;
    }

    public int getmRoundNumber() {
        return mRoundNumber;
    }

    public void setmRoundNumber(int mRoundNumber) {
        this.mRoundNumber = mRoundNumber;
    }
}
