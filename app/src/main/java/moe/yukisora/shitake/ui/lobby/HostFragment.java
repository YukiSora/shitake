package moe.yukisora.shitake.ui.lobby;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.adapter.HostRecyclerAdapter;
import moe.yukisora.shitake.model.Deck;

/**
 * Created by Delacrix on 10/10/2016.
 */

public class HostFragment extends Fragment implements DeckViewHolder.OnDeckSelectedListener{

    private RecyclerView mRecyclerView;
    private Button mJoinButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_host, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.deck_recycler_view);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Layout Manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        // Host Recycler Adapter
        HostRecyclerAdapter mHostRecyclerAdapter = new HostRecyclerAdapter();
        mHostRecyclerAdapter.setDeckList(setDeckList());
        mHostRecyclerAdapter.setDeckListener(this);

        mRecyclerView.setAdapter(mHostRecyclerAdapter);
    }

    @Override
    public void onDeckSelected(Deck deck) {
        Snackbar.make(getView(), "Good Mythical Morning", Snackbar.LENGTH_SHORT).show();
    }

    private ArrayList<String> setDeckList() {
        ArrayList<String> mDeckList = new ArrayList<>();

        mDeckList.add("Is That A Fact?");
        mDeckList.add("Word Up!");
        mDeckList.add("Movie Bluff!");
        mDeckList.add("It's the Law");
        mDeckList.add("The Plot Thickens");
        mDeckList.add("Name that Show!");
        mDeckList.add("Poetry");
        mDeckList.add("Say My Name");
        mDeckList.add("Proverbs");
        mDeckList.add("Adults Only");
        mDeckList.add("Animals");

        return mDeckList;
    }

}
