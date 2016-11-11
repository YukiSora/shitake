package moe.yukisora.shitake.ui.game;

import android.os.Bundle;
import android.os.Handler;
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

/**
 * Created by Delacrix on 10/10/2016.
 */

public class LeaderboardFragment extends Fragment {
    private static FragmentTask fragmentTask;
    private static LeaderboardPlayerRecyclerViewAdapter adapter;
    private Handler handler;
    private RecyclerView recyclerView;
    private boolean isHost;

    public static LeaderboardPlayerRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public static FragmentTask getFragmentTask() {
        return fragmentTask;
    }

    public static LeaderboardFragment newInstance() {
        Bundle args = new Bundle();
        LeaderboardFragment fragment = new LeaderboardFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public LeaderboardFragment() {
        handler = new Handler();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        fragmentTask = new FragmentTask(this);
        isHost = Bluetooth.getInstance().getServer() != null;

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Recycler View
        recyclerView = (RecyclerView)view.findViewById(R.id.leaderboardRecyclerView);
        recyclerView.setAlpha(0);
        recyclerView.setNestedScrollingEnabled(false);

        //Layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Adapter
        adapter = new LeaderboardPlayerRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        //Divider
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }

    public void showRecyclerView() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAlpha(1);
            }
        });
    }

    public static class FragmentTask {
        private LeaderboardFragment fragment;

        FragmentTask(Fragment fragment) {
            this.fragment = (LeaderboardFragment)fragment;
        }

        public void showRecyclerView() {
            fragment.showRecyclerView();
        }
    }
}
