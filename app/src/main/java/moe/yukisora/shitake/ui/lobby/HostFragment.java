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

import moe.yukisora.shitake.DeckViewHolder;
import moe.yukisora.shitake.R;
import moe.yukisora.shitake.adapter.DeckListAdapter;
import moe.yukisora.shitake.model.Deck;

/**
 * Created by Delacrix on 10/10/2016.
 */

public class HostFragment extends Fragment implements DeckViewHolder.OnDeckSelectedListener{

    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_host, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.deck_category);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Layout Manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        // History List Adapter
        DeckListAdapter mDeckListAdapter = new DeckListAdapter();
        mDeckListAdapter.setDeckListener(this);

        mRecyclerView.setAdapter(mDeckListAdapter);
    }

    @Override
    public void onDeckSelected(Deck deck) {
        Snackbar.make(getView(), "Good Mythical Morning", Snackbar.LENGTH_SHORT).show();
    }
}
