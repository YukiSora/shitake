package moe.yukisora.shitake.ui.lobby;

import android.content.Context;
import android.os.Bundle;
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
    private LinearLayout waitingLinearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting, container, false);

        waitingLinearLayout = (LinearLayout)view.findViewById(R.id.waitingLinearLayout);
        addPlayerView();
        return view;
    }

    public void addPlayerView() {
        for (PlayerAPIClient.Player player : PlayerAPIClient.getInstance().getPlayers().values()) {
            View view = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_player, waitingLinearLayout, false);

            ((ImageView)view.findViewById(R.id.playerPicture)).setImageResource(R.mipmap.ic_launcher);
            ((TextView)view.findViewById(R.id.playerName)).setText(player.name);

            waitingLinearLayout.addView(view);
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
}
