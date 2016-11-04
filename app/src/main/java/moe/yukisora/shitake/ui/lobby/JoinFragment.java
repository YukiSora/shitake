package moe.yukisora.shitake.ui.lobby;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import moe.yukisora.shitake.MainActivity;
import moe.yukisora.shitake.R;
import moe.yukisora.shitake.api.Bluetooth;
import moe.yukisora.shitake.api.PlayerAPIClient;

public class JoinFragment extends Fragment {
    private BroadcastReceiver receiver;
    private ArrayList<HashMap<String, String>> bluetooths;
    private SimpleAdapter adapter;

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
                    HashMap<String, String> map = new HashMap<>();
                    map.put("name", device.getName());
                    map.put("address", device.getAddress());
                    bluetooths.add(map);
                    adapter.notifyDataSetChanged();
                }
            }
        };
        getActivity().registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ListView
        ListView listView = (ListView)view.findViewById(R.id.hostListView);
        adapter = new SimpleAdapter(getActivity(), bluetooths, R.layout.view_host, new String[]{"name"}, new int[]{R.id.bluetoothTextView});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //try to connect to server
                Bluetooth.getInstance().newBluetoothClient(bluetooths.get(i).get("address"));

                PlayerAPIClient.getInstance().getPlayers().put(MainActivity.getBluetoothAddress(), PlayerAPIClient.getInstance().new Player(MainActivity.getBluetoothAddress(), "Poi", BitmapFactory.decodeResource(getResources(), R.drawable.picture)));

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_main_vg_fragment, new WaitingFragment())
                        .addToBackStack("join")
                        .commit();
            }
        });

        //calculate list view height inside scroll view
        MainActivity.setListViewHeightBasedOnChildren(listView);

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
}
