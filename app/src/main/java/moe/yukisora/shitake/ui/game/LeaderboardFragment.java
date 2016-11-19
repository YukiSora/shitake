package moe.yukisora.shitake.ui.game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.adapter.LeaderboardPlayerRecyclerViewAdapter;
import moe.yukisora.shitake.api.Bluetooth;
import moe.yukisora.shitake.api.PlayerAPIClient;

/**
 * Created by Delacrix on 10/10/2016.
 */

public class LeaderboardFragment extends Fragment {
    private static LeaderboardPlayerRecyclerViewAdapter adapter;
    private boolean isHost;

    public static LeaderboardPlayerRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public static LeaderboardFragment newInstance() {
        Bundle args = new Bundle();
        LeaderboardFragment fragment = new LeaderboardFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        isHost = Bluetooth.getInstance().getServer() != null;

        PlayerAPIClient.getInstance().sort();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Recycler View
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.leaderboardRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);

        //Layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Adapter
        adapter = new LeaderboardPlayerRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        //Divider
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }
}
