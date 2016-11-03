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
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.api.GameAPIClient;
import moe.yukisora.shitake.model.User;


/**
 * Created by Delacrix on 22/09/2016.
 */

public class PendingFragment extends Fragment {
    private TextView mTextDone;
    private TextView mTextWaiting;

    private Button nextButton;
    private Button cancelButton;

    private LinearLayout mPlayersDone;
    private LinearLayout mPlayersWait;

    private HashMap<User, View> mArrayPlayers = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pending, container, false);

        mTextDone = (TextView) rootView.findViewById(R.id.fragment_pending_text_done);
        mTextWaiting = (TextView) rootView.findViewById(R.id.fragment_pending_text_waiting);

        nextButton = (Button) rootView.findViewById(R.id.next_button);
        cancelButton = (Button) rootView.findViewById(R.id.cancel_button);

        mPlayersDone = (LinearLayout) rootView.findViewById(R.id.fragment_pending_vg_players_done);
        mPlayersWait = (LinearLayout) rootView.findViewById(R.id.fragment_pending_vg_waiting_players);

        mPlayersWait.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();

        animate(mPlayersWait);

        return rootView;
    }

    public void animate(View view) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.layout_expanding);

        view.setVisibility(LinearLayout.VISIBLE);
        view.setAnimation(animation);
        view.animate();

        animation.setDuration(500);
        animation.start();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        populatePendingPlayers();

        mTextDone.setAlpha(0);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetValues();
                PendingFragment.this.showVoteFragment();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkWaitingPlayers();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    public void showVoteFragment() {
        PendingFragment.this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new VoteFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getClass().getSimpleName())
                .commit();
    }

    public void populatePendingPlayers() {
        ArrayList<User> users = GameAPIClient.getInstance().getmUser();

        for (User user : users) {
            View view = addPendingViewFromLayoutResource(mPlayersWait, user);
            mArrayPlayers.put(user, view);
        }
    }

    public void resetValues() {
        for (User user : mArrayPlayers.keySet()) {
            user.setmDone(false);
        }
    }

    public void checkWaitingPlayers() {
        Boolean allDone = true;

        for (User user : mArrayPlayers.keySet()) {
            if (!user.getmDone()) {
                user.setmDone(true);

                mTextDone.setAlpha(1);
                mPlayersWait.removeView(mArrayPlayers.get(user));
                addPendingViewFromLayoutResource(mPlayersDone, user);

                break;
            }
        }

        for (User user : mArrayPlayers.keySet()) {
            if (!user.getmDone()) {
                allDone = false;
            }
        }

        if (allDone) {
            mTextWaiting.setAlpha(0);
        }

    }

    public View addPendingViewFromLayoutResource(LinearLayout linearLayout, User user) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View pendingView = inflater.inflate(R.layout.view_pending_player, linearLayout, false);

        TextView mTextViewName = (TextView) pendingView.findViewById(R.id.view_name);
        TextView mTextViewAmount = (TextView) pendingView.findViewById(R.id.view_amount);

        mTextViewName.setText(user.getmName());
        mTextViewAmount.setText("");

        linearLayout.addView(pendingView);

        return pendingView;
    }

}
