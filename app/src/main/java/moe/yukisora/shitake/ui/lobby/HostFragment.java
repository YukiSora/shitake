package moe.yukisora.shitake.ui.lobby;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.adapter.DeckRecyclerViewAdapter;
import moe.yukisora.shitake.api.Bluetooth;
import moe.yukisora.shitake.api.PlayerAPIClient;

public class HostFragment extends Fragment {
    private ArrayList<DeckRecyclerViewAdapter.ViewData> decks;
    private DeckRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host, container, false);

        decks = new ArrayList<>();

        //set bluetooth discoverable
        Bluetooth.getInstance().setDiscoverable(getActivity());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Recycler View
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.deckRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);

        //Layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Adapter
        adapter = new DeckRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        initDecks();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        PlayerAPIClient.getInstance().clearPlayer();
    }

    private void initDecks() {
        decks.add(adapter.new ViewData("Is That A Fact?"));
        decks.add(adapter.new ViewData("Movie Bluff!"));
        decks.add(adapter.new ViewData("It's the Law"));
        decks.add(adapter.new ViewData("The Plot Thickens"));
        decks.add(adapter.new ViewData("Name that Show!"));
        decks.add(adapter.new ViewData("Poetry"));
        decks.add(adapter.new ViewData("Say My Name"));
        decks.add(adapter.new ViewData("Proverbs"));
        decks.add(adapter.new ViewData("Adults Only"));
        decks.add(adapter.new ViewData("Animals"));
    }

    public ArrayList<DeckRecyclerViewAdapter.ViewData> getDecks() {
        return decks;
    }
}
