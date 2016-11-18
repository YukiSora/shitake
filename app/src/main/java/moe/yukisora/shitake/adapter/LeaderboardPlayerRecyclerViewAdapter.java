package moe.yukisora.shitake.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.api.PlayerAPIClient;
import moe.yukisora.shitake.model.Player;
import moe.yukisora.shitake.ui.game.LeaderboardFragment;

public class LeaderboardPlayerRecyclerViewAdapter extends RecyclerView.Adapter<LeaderboardPlayerRecyclerViewAdapter.ViewHolder> {
    LeaderboardFragment fragment;

    public LeaderboardPlayerRecyclerViewAdapter(Fragment fragment) {
        this.fragment = (LeaderboardFragment) fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LeaderboardPlayerRecyclerViewAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_leaderboard_player, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Player player = PlayerAPIClient.getInstance().get(position);

        holder.playerPicture.setImageBitmap(player.picture);
        holder.playerName.setText(player.name);
        holder.playerScore.setText(player.score + "");
    }

    @Override
    public int getItemCount() {
        return PlayerAPIClient.getInstance().getPlayers().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView playerPicture;
        public TextView playerName;
        public TextView playerScore;
        public TextView playerAddingScore;

        public ViewHolder(View view) {
            super(view);

            playerPicture = (ImageView) view.findViewById(R.id.playerPicture);
            playerName = (TextView) view.findViewById(R.id.playerName);
            playerScore = (TextView) view.findViewById(R.id.playerScore);
            playerAddingScore = (TextView) view.findViewById(R.id.playerAddingScore);
        }
    }
}
