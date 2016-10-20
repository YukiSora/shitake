package moe.yukisora.shitake;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import moe.yukisora.shitake.model.Deck;

/**
 * Created by Delacrix on 20/10/2016.
 */

public class DeckViewHolder extends RecyclerView.ViewHolder {
    public interface OnDeckSelectedListener{
        void onDeckSelected(Deck deck);
    }

    public DeckViewHolder(View itemView, final OnDeckSelectedListener handler) {
        super(itemView);

//        userName = (TextView) itemView.findViewById(R.id.history_username);
//        locationName = (TextView) itemView.findViewById(R.id.history_location_name);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // handler.onHistorySelected(mMeetup);
            }
        });
    }

    public void DeckViewHolder(@NonNull Deck deck){
//        mMeetup = history;
//        userName.setText(history.getmUser().toString());
//        locationName.setText(history.getMyLocation().toString());
    }
}