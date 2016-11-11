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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.api.AnswerAPIClient;

/**
 * Created by Delacrix on 22/09/2016.
 */

public class AnswerFragment extends Fragment {

    private LinearLayout mAnswerLayout;
    private TextView mQuestion;

    private String question;

    public static AnswerFragment newInstance(String question) {
        Bundle args = new Bundle();
        AnswerFragment fragment = new AnswerFragment();
        args.putString("question", question);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_answer, container, false);

        mQuestion = (TextView)rootView.findViewById(R.id.fragment_answer_text_question);
        mAnswerLayout = (LinearLayout)rootView.findViewById(R.id.fragment_answer_vg_list);

        question = getArguments().getString("question");

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        populateAnswers();

        mQuestion.setText(question);
        mQuestion.setAlpha(0);

        mAnswerLayout.startAnimation(getAnimation());
    }

    public Animation getAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.interpolator_accelerate_decelerate);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mQuestion.setAlpha(1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        return animation;
    }

    public void populateAnswers() {
        // Shuffle Answers
        ArrayList<String> addresses = new ArrayList<>(AnswerAPIClient.getInstance().getAnswers().keySet());
        Collections.shuffle(addresses, new Random(System.nanoTime()));

        // Populate Answers
        for (String address : addresses) {
            addPendingViewFromLayoutResource(mAnswerLayout, address, AnswerAPIClient.getInstance().getAnswers().get(address));
        }
    }

    public View addPendingViewFromLayoutResource(LinearLayout linearLayout, final String address, String answer) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.view_answer, linearLayout, false);
        Button mAnswer = (Button)rootView.findViewById(R.id.view_btn_answer);

        mAnswer.setAllCaps(true);
        mAnswer.setText(answer);
        mAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResultFragment(address);
            }
        });
        linearLayout.addView(rootView);

        return rootView;
    }

    public void showResultFragment(String address) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, ResultFragment.newInstance(address))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
