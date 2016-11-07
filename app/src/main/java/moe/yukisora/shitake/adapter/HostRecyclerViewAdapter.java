package moe.yukisora.shitake.adapter;

import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import moe.yukisora.shitake.MainActivity;
import moe.yukisora.shitake.R;
import moe.yukisora.shitake.api.Bluetooth;
import moe.yukisora.shitake.api.PlayerAPIClient;
import moe.yukisora.shitake.ui.lobby.JoinFragment;
import moe.yukisora.shitake.ui.lobby.WaitingFragment;

public class HostRecyclerViewAdapter extends RecyclerView.Adapter<HostRecyclerViewAdapter.ViewHolder> {
    private JoinFragment fragment;

    public HostRecyclerViewAdapter(Fragment fragment) {
        this.fragment = (JoinFragment)fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_host, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ViewData bluetooth = fragment.getBluetooths().get(position);

        holder.hostNameTextView.setText(bluetooth.name);
        holder.hostNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //try to connect to server
                Bluetooth.getInstance().newBluetoothClient(bluetooth.address);

                PlayerAPIClient.getInstance().getPlayers().put(MainActivity.getBluetoothAddress(), PlayerAPIClient.getInstance().new Player(MainActivity.getBluetoothAddress(), "Poi", BitmapFactory.decodeResource(fragment.getResources(), R.mipmap.ic_launcher)));

                fragment.getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_main_vg_fragment, new WaitingFragment())
                        .addToBackStack("join")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return fragment.getBluetooths().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView hostNameTextView;

        public ViewHolder(View view) {
            super(view);

            hostNameTextView = (TextView)view.findViewById(R.id.hostNameTextView);
        }
    }

    public class ViewData {
        public String name;
        public String address;

        public ViewData(String name, String address) {
            this.name = name;
            this.address = address;
        }
    }
}
