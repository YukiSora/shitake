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

public class UserAPIClient {
    private static UserAPIClient sSharedInstance;
    private static String sUserSample;

    private ArrayList<User> mUser = new ArrayList<>();
    private SharedPreferences mSharedPreferences;

    // Singleton New Instance
    public static synchronized UserAPIClient newInstance(@NonNull Context context) {
        if (sSharedInstance == null) {
            sSharedInstance = new UserAPIClient(context);
        }

        return sSharedInstance;
    }

    // Singleton Get Instance
    public static synchronized UserAPIClient getInstance() {
        return sSharedInstance;
    }

    // Constructor
    private UserAPIClient(@NonNull Context context) {
        sUserSample = "sample_user";

        mSharedPreferences = context.getSharedPreferences("preference-key", Context.MODE_PRIVATE);

        populateUsers(context);
    }

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
}
