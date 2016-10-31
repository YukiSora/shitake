package moe.yukisora.shitake.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import moe.yukisora.shitake.model.User;

/**
 * Created by Delacrix on 28/10/2016.
 */

public class GameAPIClient {
    private static GameAPIClient sSharedInstance;
    private static String sUserSample;

    private ArrayList<User> mUser = new ArrayList<>();
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
        sUserSample = "sample_user";

        mSharedPreferences = context.getSharedPreferences("preference-key", Context.MODE_PRIVATE);

        populateUsers(context);
    }

    // Method - Load Users

    private void populateUsers(Context context) {
        try {
            JSONObject jsonRootObject = new JSONObject(String.valueOf(loadJSONFromAsset(context)));
            JSONArray jsonArray = jsonRootObject.optJSONArray(sUserSample);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                mUser.add(new User(jsonObject.optString("Username"), jsonObject.optString("Profile Picture")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private StringBuilder loadJSONFromAsset(Context context) {
        StringBuilder buf = new StringBuilder();

        try {
            InputStream json = context.getAssets().open(sUserSample + ".json");
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

    public int getmRoundNumber() {
        return mRoundNumber;
    }

    public void setmRoundNumber(int mRoundNumber) {
        this.mRoundNumber = mRoundNumber;
    }
}
