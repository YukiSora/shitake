package moe.yukisora.shitake.adapter;

import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import moe.yukisora.shitake.MainActivity;
import moe.yukisora.shitake.R;
import moe.yukisora.shitake.api.Bluetooth;
import moe.yukisora.shitake.api.PlayerAPIClient;
import moe.yukisora.shitake.ui.lobby.HostFragment;
import moe.yukisora.shitake.ui.lobby.WaitingFragment;

public class DeckRecyclerViewAdapter extends RecyclerView.Adapter<DeckRecyclerViewAdapter.ViewHolder> {
    private HostFragment fragment;

    public DeckRecyclerViewAdapter(Fragment fragment) {
        this.fragment = (HostFragment)fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_deck, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ViewData bluetooth = fragment.getDecks().get(position);
        holder.deckTitleTextView.setText(bluetooth.title);
        holder.deckCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bluetooth.getInstance().newBluetoothServer();

                PlayerAPIClient.getInstance().getPlayers().put(MainActivity.getBluetoothAddress(), PlayerAPIClient.getInstance().new Player(MainActivity.getBluetoothAddress(), "Yuki Sora", BitmapFactory.decodeResource(fragment.getResources(), R.drawable.picture)));

                fragment.getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_main_vg_fragment, new WaitingFragment())
                        .addToBackStack("host")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return fragment.getDecks().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView deckCardView;
        public TextView deckTitleTextView;

        public ViewHolder(View view) {
            super(view);

            deckCardView = (CardView)view.findViewById(R.id.deckCardView);
            deckTitleTextView = (TextView)view.findViewById(R.id.deckTitleTextView);
        }
    }

    public class ViewData {
        public String title;

        public ViewData(String title) {
            this.title = title;
        }
    }
}
