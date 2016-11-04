package moe.yukisora.shitake.ui.lobby;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.model.Deck;

/**
 * Created by Delacrix on 20/10/2016.
 */

public class DeckViewHolder extends RecyclerView.ViewHolder {

    private String deckJSON;
    private TextView mDeckTitle;

    public interface OnDeckSelectedListener{
        void onDeckSelected(String deckJSON);
    }

    public DeckViewHolder(View itemView, final OnDeckSelectedListener handler) {
        super(itemView);

        mDeckTitle = (TextView) itemView.findViewById(R.id.deck_title);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.onDeckSelected(deckJSON);
            }
        });
    }

    public void setDeckTitle(@NonNull String deckDisplayTitle, @NonNull String deckJSON){
        mDeckTitle.setText(deckDisplayTitle);
        this.deckJSON = deckJSON;

    }

}
