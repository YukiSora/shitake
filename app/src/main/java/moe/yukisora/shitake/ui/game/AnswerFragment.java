package moe.yukisora.shitake.ui.game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import moe.yukisora.shitake.R;

/**
 * Created by Delacrix on 22/09/2016.
 */

public class AnswerFragment extends Fragment {

    private LinearLayout mAnswerLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_answer, container, false);

        mAnswerLayout = (LinearLayout) rootView.findViewById(R.id.fragment_answer_vg);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Animation mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.interpolator_accelerate_decelerate);
        mAnswerLayout.startAnimation(mAnimation);
    }
}
