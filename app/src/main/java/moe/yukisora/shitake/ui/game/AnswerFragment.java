package moe.yukisora.shitake.ui.game;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.api.DeckAPIClient;
import moe.yukisora.shitake.api.GameAPIClient;
import moe.yukisora.shitake.model.Answer;
import moe.yukisora.shitake.model.User;

/**
 * Created by Delacrix on 22/09/2016.
 */

public class AnswerFragment extends Fragment {

    private LinearLayout mAnswerLayout;
    private TextView mQuestion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_answer, container, false);

        mQuestion = (TextView) rootView.findViewById(R.id.fragment_answer_text_question);
        mAnswerLayout = (LinearLayout) rootView.findViewById(R.id.fragment_answer_vg_list);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        populateAnswers();

        mQuestion.setText(DeckAPIClient.getInstance().getDeck().getmQuestion());
        mQuestion.setAlpha(0);

        mAnswerLayout.startAnimation(getAnimation());
    }

    public Animation getAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.interpolator_accelerate_decelerate);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                mQuestion.setAlpha(1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        return animation;
    }

    public void showVoteFragment() {
        AnswerFragment.this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new VoteFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getClass().getSimpleName())
                .commit();
    }

    public void populateAnswers() {
        // Shuffle Answers
        GameAPIClient.getInstance().shuffleAnswer();

        // Populate Answers
        for (Answer answer : GameAPIClient.getInstance().getmAnswer()) {
            addPendingViewFromLayoutResource(mAnswerLayout, answer);
        }
    }

    public View addPendingViewFromLayoutResource(LinearLayout linearLayout, Answer answer) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.view_answer, linearLayout, false);
        Button mAnswer = (Button) rootView.findViewById(R.id.view_btn_answer);

        mAnswer.setAllCaps(true);
        mAnswer.setText(answer.getmContent());
        mAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnswerFragment.this.showVoteFragment();
            }
        });
        linearLayout.addView(rootView);

        return rootView;
    }
}
