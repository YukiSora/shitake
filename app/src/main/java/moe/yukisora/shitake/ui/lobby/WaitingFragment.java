package moe.yukisora.shitake.ui.lobby;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moe.yukisora.shitake.GameActivity;
import moe.yukisora.shitake.R;
import moe.yukisora.shitake.adapter.PlayerRecyclerViewAdapter;
import moe.yukisora.shitake.api.Bluetooth;
import moe.yukisora.shitake.api.PlayerAPIClient;

/**
 * Created by Delacrix on 10/10/2016.
 */

public class WaitingFragment extends Fragment {
    private static PlayerRecyclerViewAdapter adapter;
    private boolean isHost;
    private boolean isStartGame;

    public static PlayerRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting, container, false);
        isHost = getArguments().getBoolean("isHost");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Recycler View
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.playerRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);

        //Layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Adapter
        adapter = new PlayerRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        //Divider
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

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
        PlayerAPIClient.getInstance().clearPlayer();
    }
}
