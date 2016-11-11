package moe.yukisora.shitake.ui.game;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import moe.yukisora.shitake.MainActivity;
import moe.yukisora.shitake.R;
import moe.yukisora.shitake.api.AnswerAPIClient;
import moe.yukisora.shitake.api.Bluetooth;
import moe.yukisora.shitake.api.DeckAPIClient;
import moe.yukisora.shitake.model.Deck;

/**
 * Created by Delacrix on 22/09/2016.
 */

public class QuestionFragment extends Fragment {

    private TextView mQuestionTitle;
    private TextView mUserAnswer;

    private Button mSubmitButton;

    private static FragmentTask fragmentTask;
    private Handler handler;
    private boolean isAbleSubmit;
    private boolean isHost;

    public static FragmentTask getFragmentTask() {
        return fragmentTask;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question, container, false);

        mQuestionTitle = (TextView)rootView.findViewById(R.id.fragment_question_text_title);
        mUserAnswer = (TextView)rootView.findViewById(R.id.fragment_question_text_answer);

        mSubmitButton = (Button)rootView.findViewById(R.id.submit_button);

        fragmentTask = new FragmentTask(this);
        handler = new Handler();
        isHost = Bluetooth.getInstance().getServer() != null;

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AnswerAPIClient.newInstance();

        if (isHost) {
            //get question
            Deck deck = DeckAPIClient.getInstance().getDeck();

            //send question
            try {
                JSONObject data = new JSONObject();
                data.put("question", deck.getQuestion());
                data.put("answer", deck.getAnswer());
                Bluetooth.getInstance().getServer().sendExclude(null, Bluetooth.wrapMessage(Bluetooth.DATA_TYPE_QUESTION, data));
            } catch (JSONException ignore) {
            }

            //set question
            mQuestionTitle.setText(deck.getQuestion());
            isAbleSubmit = true;
        }

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAbleSubmit) {
                    String answer = mUserAnswer.getText().toString();

                    //send answer
                    try {
                        JSONObject data = new JSONObject();
                        data.put("address", MainActivity.getBluetoothAddress());
                        data.put("answer", answer);
                        if (isHost)
                            Bluetooth.getInstance().getServer().sendExclude(null, Bluetooth.wrapMessage(Bluetooth.DATA_TYPE_ANSWER, data));
                        else
                            Bluetooth.getInstance().getClient().send(Bluetooth.wrapMessage(Bluetooth.DATA_TYPE_ANSWER, data));
                    } catch (JSONException ignore) {
                    }

                    //set answer
                    AnswerAPIClient.getInstance().getAnswers().put(MainActivity.getBluetoothAddress(), answer);

                    QuestionFragment.this.showPendingFragment();
                }
            }
        });
    }

    public void showPendingFragment() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new PendingFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getClass().getSimpleName())
                .commit();
    }

    private void updateQuestion(final String question) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mQuestionTitle.setText(question);
                isAbleSubmit = true;
            }
        });
    }

    public static class FragmentTask {
        private QuestionFragment fragment;

        FragmentTask(Fragment fragment) {
            this.fragment = (QuestionFragment)fragment;
        }

        public void updateQuestion(String question) {
            fragment.updateQuestion(question);
        }
    }
}
