package moe.yukisora.shitake.ui.lobby;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

public class HostFragment extends Fragment {
    private ArrayList<HashMap<String, String>> decks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host, container, false);

        initDecks();

        //set bluetooth discoverable
        Bluetooth.getInstance().setDiscoverable(getActivity());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = (ListView)view.findViewById(R.id.deckListView);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), decks, R.layout.view_deck, new String[]{"title"}, new int[]{R.id.deckTitle});
        listView.setAdapter(adapter);
        listView.setDivider(null);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("poi", decks.get(i).get("title"));

                Bluetooth.getInstance().newBluetoothServer();

                PlayerAPIClient.getInstance().getPlayers().put(MainActivity.getBluetoothAddress(), PlayerAPIClient.getInstance().new Player(MainActivity.getBluetoothAddress(), "Yuki Sora", null));

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_main_vg_fragment, new WaitingFragment())
                        .addToBackStack("host")
                        .commit();
            }
        });

        //calculate list view height inside scroll view
        MainActivity.setListViewHeightBasedOnChildren(listView);
    }

    private void initDecks() {
        decks = new ArrayList<>();
        HashMap<String, String> map;

        map = new HashMap<>();
        map.put("title", "Is That A Fact?");
        decks.add(map);

        map = new HashMap<>();
        map.put("title", "Word Up!");
        decks.add(map);

        map = new HashMap<>();
        map.put("title", "Movie Bluff!");
        decks.add(map);

        map = new HashMap<>();
        map.put("title", "It's the Law");
        decks.add(map);

        map = new HashMap<>();
        map.put("title", "The Plot Thickens");
        decks.add(map);

        map = new HashMap<>();
        map.put("title", "Name that Show!");
        decks.add(map);

        map = new HashMap<>();
        map.put("title", "Poetry");
        decks.add(map);

        map = new HashMap<>();
        map.put("title", "Say My Name");
        decks.add(map);

        map = new HashMap<>();
        map.put("title", "Proverbs");
        decks.add(map);

        map = new HashMap<>();
        map.put("title", "Adults Only");
        decks.add(map);

        map = new HashMap<>();
        map.put("title", "Animals");
        decks.add(map);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        PlayerAPIClient.getInstance().clearPlayer();
    }
}
