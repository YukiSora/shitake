package moe.yukisora.shitake.ui.game;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.api.AnswerAPIClient;
import moe.yukisora.shitake.api.Bluetooth;
import moe.yukisora.shitake.api.PlayerAPIClient;


/**
 * Created by Delacrix on 22/09/2016.
 */

public class PendingFragment extends Fragment {
    private Button nextButton;

    private LinearLayout mPlayersDone;
    private LinearLayout mPlayersWait;

    private HashMap<PlayerAPIClient.Player, View> mArrayPlayers = new HashMap<>();

    private static FragmentTask fragmentTask;
    private Handler handler;
    private boolean isHost;

    public static FragmentTask getFragmentTask() {
        return fragmentTask;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pending, container, false);

        nextButton = (Button)rootView.findViewById(R.id.next_button);

        mPlayersDone = (LinearLayout)rootView.findViewById(R.id.fragment_pending_vg_players_done);
        mPlayersWait = (LinearLayout)rootView.findViewById(R.id.fragment_pending_vg_waiting_players);

        mPlayersWait.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();

        animate(mPlayersWait);

        fragmentTask = new FragmentTask(this);
        handler = new Handler();
        isHost = Bluetooth.getInstance().getServer() != null;

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set button is only visible to host
        nextButton.setAlpha(0);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PendingFragment.this.showAnswerFragment();
            }
        });

        //generate waiting list
        populatePendingPlayers();

        //generate answer list
        populateAnswers();
    }

    public void animate(View view) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.layout_expanding);

        view.setVisibility(LinearLayout.VISIBLE);
        view.setAnimation(animation);
        view.animate();

        animation.setDuration(500);
        animation.start();
    }

    public void showAnswerFragment() {
        PendingFragment.this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new AnswerFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getClass().getSimpleName())
                .commit();
    }

    public void populatePendingPlayers() {
        for (PlayerAPIClient.Player player : PlayerAPIClient.getInstance().getPlayers().values()) {
            View view = addPendingViewFromLayoutResource(mPlayersWait, player);
            mArrayPlayers.put(player, view);
        }
    }

    public void populateAnswers() {
        for (String address : AnswerAPIClient.getInstance().getAnswers().keySet())
            if (!address.equals("correct"))
                addAnswerView(address);

        showNextButton();
    }

    public void addAnswer(final String address) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                addAnswerView(address);

                showNextButton();
            }
        });
    }

    public void addAnswerView(String address) {
        PlayerAPIClient.Player player = PlayerAPIClient.getInstance().get(address);
        mPlayersWait.removeView(mArrayPlayers.get(player));
        addPendingViewFromLayoutResource(mPlayersDone, player);
    }

    private void showNextButton() {
        if (isHost && mArrayPlayers.size() == AnswerAPIClient.getInstance().getAnswers().size())
            nextButton.setAlpha(1);
    }

    public View addPendingViewFromLayoutResource(LinearLayout linearLayout, PlayerAPIClient.Player player) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.view_player, linearLayout, false);

        ((TextView)rootView.findViewById(R.id.playerName)).setText(player.name);
        ((ImageView)rootView.findViewById(R.id.playerPicture)).setImageBitmap(player.picture);

        linearLayout.addView(rootView);

        return rootView;
    }

    public static class FragmentTask {
        private PendingFragment fragment;

        FragmentTask(Fragment fragment) {
            this.fragment = (PendingFragment)fragment;
        }

        public void addAnswer(String address) {
            fragment.addAnswer(address);
        }
    }
}
