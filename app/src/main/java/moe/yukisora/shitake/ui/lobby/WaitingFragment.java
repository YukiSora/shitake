package moe.yukisora.shitake.ui.lobby;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import moe.yukisora.shitake.GameActivity;
import moe.yukisora.shitake.R;
import moe.yukisora.shitake.api.Bluetooth;
import moe.yukisora.shitake.api.PlayerAPIClient;

/**
 * Created by Delacrix on 10/10/2016.
 */

public class WaitingFragment extends Fragment {
    private Activity activity;
    private static FragmentHandler fragmentHandler;
    private LinearLayout waitingLinearLayout;
    private boolean isHost;
    private boolean isStartGame;

    public static FragmentHandler getFragmentHandler() {
        return fragmentHandler;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting, container, false);
        isHost = getArguments().getBoolean("isHost");

        activity = getActivity();
        fragmentHandler = new FragmentHandler(this);
        waitingLinearLayout = (LinearLayout)view.findViewById(R.id.waitingLinearLayout);

        //init list with default player
        for (PlayerAPIClient.Player player : PlayerAPIClient.getInstance().getPlayers().values())
            addPlayerView(player);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isHost) {
            view.findViewById(R.id.startGame).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isStartGame = true;

                    Intent intent =new Intent(WaitingFragment.this.getActivity(), GameActivity.class);
                    startActivity(intent);
                }
            });
        }
        else {
            view.findViewById(R.id.startGame).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!isStartGame) {
            Bluetooth.getInstance().closeClient();
            Bluetooth.getInstance().closeServer();
        }
    }

    public void addPlayerView(PlayerAPIClient.Player player) {
        View view = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_player, waitingLinearLayout, false);

        view.setTag(player.address);

        ((ImageView)view.findViewById(R.id.playerPicture)).setImageBitmap(player.picture);
        ((TextView)view.findViewById(R.id.playerName)).setText(player.name);

        waitingLinearLayout.addView(view);
    }

    public void removePlayerView(String address) {
        for (int i = 0; i < waitingLinearLayout.getChildCount(); i++) {
            LinearLayout view = (LinearLayout)waitingLinearLayout.getChildAt(i);
            if (view.getTag().equals(address)) {
                waitingLinearLayout.removeView(view);
                break;
            }
        }
    }

    public static class FragmentHandler extends Handler {
        private WaitingFragment fragment;

        FragmentHandler(WaitingFragment fragment) {
            super();

            this.fragment = fragment;
        }

        public void addToList(final PlayerAPIClient.Player player) {
            post(new Runnable() {
                @Override
                public void run() {
                    fragment.addPlayerView(player);
                }
            });
        }

        public void removeFromList(final String address) {
            post(new Runnable() {
                @Override
                public void run() {
                    fragment.removePlayerView(address);
                }
            });
        }

        public void back() {
            post(new Runnable() {
                @Override
                public void run() {
                    fragment.activity.onBackPressed();
                }
            });
        }
    }
}
