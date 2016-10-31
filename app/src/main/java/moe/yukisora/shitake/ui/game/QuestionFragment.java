package moe.yukisora.shitake.ui.game;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.api.DeckAPIClient;


/**
 * Created by Delacrix on 22/09/2016.
 */

public class QuestionFragment extends Fragment {

    private TextView mQuestionTitle;
    private Button mSubmitButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question, container, false);

        mQuestionTitle = (TextView) rootView.findViewById(R.id.question_title);
        mSubmitButton = (Button) rootView.findViewById(R.id.submit_button);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mQuestionTitle.setText(DeckAPIClient.getInstance().getDeck().getmQuestion());

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionFragment.this.showPendingFragment();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    public void showPendingFragment(){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new PendingFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getClass().getSimpleName())
                .commit();
    }
}
