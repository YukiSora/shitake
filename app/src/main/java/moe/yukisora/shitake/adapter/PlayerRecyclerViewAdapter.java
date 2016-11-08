package moe.yukisora.shitake.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.api.PlayerAPIClient;

public class PlayerRecyclerViewAdapter extends RecyclerView.Adapter<PlayerRecyclerViewAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_player, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PlayerAPIClient.Player player = PlayerAPIClient.getInstance().get(position);
        holder.playerName.setText(player.name);
        holder.playerPicture.setImageBitmap(player.picture);
    }

    @Override
    public int getItemCount() {
        return PlayerAPIClient.getInstance().getPlayers().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView playerPicture;
        public TextView playerName;

        public ViewHolder(View view) {
            super(view);

            playerPicture = (ImageView)view.findViewById(R.id.playerPicture);
            playerName = (TextView)view.findViewById(R.id.playerName);
        }
    }
}
