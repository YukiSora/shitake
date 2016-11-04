package moe.yukisora.shitake.ui.lobby;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import moe.yukisora.shitake.GameActivity;
import moe.yukisora.shitake.R;
import moe.yukisora.shitake.model.Deck;

import static android.content.Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

/**
 * Created by Delacrix on 28/10/2016.
 */

public class JoinViewHolder extends RecyclerView.ViewHolder {

    private Button mJoinButton;

    public interface OnJoinSelectedListener {
        void onJoinSelected();
    }

    public JoinViewHolder(View itemView, final JoinViewHolder.OnJoinSelectedListener handler) {
        super(itemView);

        mJoinButton = (Button) itemView.findViewById(R.id.view_btn_join);

        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.onJoinSelected();
            }
        });
    }

    public void JoinViewHolder(@NonNull Deck deck) {
//        mMeetup = history;
//        userName.setText(history.getmUser().toString());
//        locationName.setText(history.getMyLocation().toString());
    }
}
