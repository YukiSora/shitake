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
import java.util.Collections;
import java.util.Random;

import moe.yukisora.shitake.model.Deck;

/**
 * Created by Delacrix on 27/10/2016.
 */

public class DeckAPIClient {

    private static DeckAPIClient sSharedInstance;
    private static String sCurrentDeck;

    private int mCounter;
    private ArrayList<Deck> mDeck = new ArrayList<>();
    private SharedPreferences mSharedPreferences;

    // Singleton Instances
    public static synchronized DeckAPIClient newInstance(@NonNull Context context, String filename) {
        if (sSharedInstance == null && sCurrentDeck != filename) {
            sSharedInstance = new DeckAPIClient(context, filename);
        }

        return sSharedInstance;
    }

    // Constructor
    private DeckAPIClient(@NonNull Context context, String filename) {
        mCounter = 0;
        sCurrentDeck = filename;
        mSharedPreferences = context.getSharedPreferences("preference-key", Context.MODE_PRIVATE);

        populateQuestions(context);

        Collections.shuffle(mDeck, new Random(System.nanoTime()));

    }

    // Singleton Instances
    public static synchronized DeckAPIClient getInstance() {
        return sSharedInstance;
    }


    private void populateQuestions(Context context) {
        try {
            JSONObject jsonRootObject = new JSONObject(String.valueOf(loadJSONFromAsset(context)));
            JSONArray jsonArray = jsonRootObject.optJSONArray(sCurrentDeck);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                mDeck.add(new Deck(jsonObject.optString("Question"), jsonObject.optString("Answer")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private StringBuilder loadJSONFromAsset(Context context) {
        StringBuilder buf = new StringBuilder();

        try {
            InputStream json = context.getAssets().open(sCurrentDeck+".json");
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

    public Deck getDeck(){
        return mDeck.get(mCounter++);
    }
}
