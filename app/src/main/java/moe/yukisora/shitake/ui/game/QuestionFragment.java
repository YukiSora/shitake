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

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.api.DeckAPIClient;
import moe.yukisora.shitake.api.GameAPIClient;
import moe.yukisora.shitake.model.Answer;

/**
 * Created by Delacrix on 22/09/2016.
 */

public class QuestionFragment extends Fragment {

    private TextView mQuestionTitle;
    private TextView mUserAnswer;

    private Button mSubmitButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question, container, false);

        mQuestionTitle = (TextView) rootView.findViewById(R.id.fragment_question_text_title);
        mUserAnswer = (TextView) rootView.findViewById(R.id.fragment_question_text_answer);

        mSubmitButton = (Button) rootView.findViewById(R.id.submit_button);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mQuestionTitle.setText(DeckAPIClient.getInstance().getDeck().getQuestion());
        mUserAnswer.setText("Mother");

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameAPIClient.getInstance().addAnswer(new Answer("Real Answer", DeckAPIClient.getInstance().getDeck().getAnswer()));
                GameAPIClient.getInstance().addAnswer(new Answer("Username", mUserAnswer.getText().toString()));
                QuestionFragment.this.showPendingFragment();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    public void showPendingFragment() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new PendingFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getClass().getSimpleName())
                .commit();
    }
}
