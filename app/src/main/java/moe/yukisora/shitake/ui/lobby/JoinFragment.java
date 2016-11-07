package moe.yukisora.shitake.ui.lobby;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.adapter.HostRecyclerViewAdapter;
import moe.yukisora.shitake.api.Bluetooth;
import moe.yukisora.shitake.api.PlayerAPIClient;

public class JoinFragment extends Fragment {
    private BroadcastReceiver receiver;
    private ArrayList<HostRecyclerViewAdapter.ViewData> bluetooths;
    private HostRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join, container, false);

        bluetooths = new ArrayList<>();

        //Bluetooth discovery receiver
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    String name = device.getName();
                    if (name == null)
                        name = "Unknown";
                    String address = device.getAddress();

                    bluetooths.add(adapter.new ViewData(name, address));
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyItemInserted(bluetooths.size() - 1);
                        }
                    });
                }
            }
        };
        getActivity().registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Recycler View
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.hostRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);

        //Layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Adapter
        adapter = new HostRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        //Divider
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        //Bluetooth discovery
        Bluetooth.getInstance().startDiscovery();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Bluetooth.getInstance().stopDiscovery();
        getActivity().unregisterReceiver(receiver);
        PlayerAPIClient.getInstance().clearPlayer();
    }

    public ArrayList<HostRecyclerViewAdapter.ViewData> getBluetooths() {
        return bluetooths;
    }
}
