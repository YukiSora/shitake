package moe.yukisora.shitake.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import moe.yukisora.shitake.model.Deck;
import moe.yukisora.shitake.ui.lobby.DeckViewHolder;
import moe.yukisora.shitake.R;
import moe.yukisora.shitake.ui.lobby.JoinViewHolder;

/**
 * Created by Delacrix on 20/10/2016.
 */

public class HostRecyclerAdapter extends RecyclerView.Adapter {

    private DeckViewHolder.OnDeckSelectedListener mDeckListener;
    private JoinViewHolder.OnJoinSelectedListener mJoinListener;

    private ArrayList<String> mDeckList = new ArrayList<>();

    // Override Methods

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case 0:
                return new JoinViewHolder(inflater.inflate(R.layout.view_join, parent, false), mJoinListener);
            default:
                return new DeckViewHolder(inflater.inflate(R.layout.view_deck, parent, false), mDeckListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DeckViewHolder) {
            ((DeckViewHolder) holder).setDeck(mDeckList.get(--position));
        }
    }

    @Override
    public int getItemCount() {
        return 1 + mDeckList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // Methods

    public DeckViewHolder.OnDeckSelectedListener getDeckListener() {
        return mDeckListener;
    }

    public void setDeckListener(DeckViewHolder.OnDeckSelectedListener deckListener) {
        this.mDeckListener = deckListener;
    }

    public JoinViewHolder.OnJoinSelectedListener getJoinListener() {
        return mJoinListener;
    }

    public void setJoinListener(JoinViewHolder.OnJoinSelectedListener mJoinListener) {
        this.mJoinListener = mJoinListener;
    }

    public void setDeckList(ArrayList<String> mDeckList){
        this.mDeckList = mDeckList;
    }



}
