package moe.yukisora.shitake.ui.lobby;

import android.content.Context;
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

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.api.Bluetooth;
import moe.yukisora.shitake.api.PlayerAPIClient;

/**
 * Created by Delacrix on 10/10/2016.
 */

public class WaitingFragment extends Fragment {
    private static UpdateListHandler updateListHandler;
    private LinearLayout waitingLinearLayout;

    public static UpdateListHandler getUpdateListHandler() {
        return updateListHandler;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting, container, false);

        updateListHandler = new UpdateListHandler(this);

        waitingLinearLayout = (LinearLayout)view.findViewById(R.id.waitingLinearLayout);

        for (PlayerAPIClient.Player player : PlayerAPIClient.getInstance().getPlayers().values()) {
            addPlayerView(player);
        }

        return view;
    }

    public void addPlayerView(PlayerAPIClient.Player player) {
        View view = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_player, waitingLinearLayout, false);

        view.setTag(player.address);

        ((ImageView)view.findViewById(R.id.playerPicture)).setImageResource(R.mipmap.ic_launcher);
        ((TextView)view.findViewById(R.id.playerName)).setText(player.name);

        waitingLinearLayout.addView(view);
    }

    public void removePlayerView(PlayerAPIClient.Player player) {
        for (int i = 0; i < waitingLinearLayout.getChildCount(); i++) {
            LinearLayout view = (LinearLayout)waitingLinearLayout.getChildAt(i);
            if (view.getTag().equals(player.address)) {
                waitingLinearLayout.removeView(view);
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.waitingStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bluetooth.BluetoothClient client = Bluetooth.getInstance().getClient();
                if (client != null) {
                    client.write("poiiiii");
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Bluetooth.getInstance().closeClient();
    }

    public static class UpdateListHandler extends Handler {
        private WaitingFragment fragment;

        UpdateListHandler(WaitingFragment fragment) {
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

        public void removeFromList(final PlayerAPIClient.Player player) {
            post(new Runnable() {
                @Override
                public void run() {
                    fragment.removePlayerView(player);
                }
            });
        }
    }
}
