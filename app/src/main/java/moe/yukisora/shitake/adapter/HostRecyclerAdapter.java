package moe.yukisora.shitake.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moe.yukisora.shitake.ui.lobby.DeckViewHolder;
import moe.yukisora.shitake.R;
import moe.yukisora.shitake.ui.lobby.JoinViewHolder;

/**
 * Created by Delacrix on 20/10/2016.
 */

public class HostRecyclerAdapter extends RecyclerView.Adapter {

    private DeckViewHolder.OnDeckSelectedListener mDeckListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case 0:
                return new JoinViewHolder(inflater.inflate(R.layout.view_join, parent, false), mDeckListener);
            default:
                return new DeckViewHolder(inflater.inflate(R.layout.view_deck, parent, false), mDeckListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    public DeckViewHolder.OnDeckSelectedListener getDeckListener() {
        return mDeckListener;
    }

    public void setDeckListener(DeckViewHolder.OnDeckSelectedListener deckListener) {
        this.mDeckListener = deckListener;
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
