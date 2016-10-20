package moe.yukisora.shitake.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moe.yukisora.shitake.DeckViewHolder;
import moe.yukisora.shitake.R;

/**
 * Created by Delacrix on 20/10/2016.
 */

public class DeckListAdapter extends RecyclerView.Adapter {

    private DeckViewHolder.OnDeckSelectedListener mDeckListener;

    @Override
    public int getItemCount() {
        return 100;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_deck, parent, false);

        return new DeckViewHolder(view, mDeckListener);
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
}
