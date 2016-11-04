package moe.yukisora.shitake.ui.lobby;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;

import moe.yukisora.shitake.GameActivity;
import moe.yukisora.shitake.R;
import moe.yukisora.shitake.adapter.HostRecyclerAdapter;
import moe.yukisora.shitake.api.DeckAPIClient;
import moe.yukisora.shitake.model.Deck;
import moe.yukisora.shitake.ui.game.PendingFragment;

import static android.content.Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

/**
 * Created by Delacrix on 10/10/2016.
 */

public class HostFragment extends Fragment implements JoinViewHolder.OnJoinSelectedListener, DeckViewHolder.OnDeckSelectedListener {

    private RecyclerView mRecyclerView;
    private Button mJoinButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_host, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.deck_recycler_view);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Layout Manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        // Host Recycler Adapter
        HostRecyclerAdapter mHostRecyclerAdapter = new HostRecyclerAdapter();

        mHostRecyclerAdapter.setDeckList(getDeckDisplayName());

        mHostRecyclerAdapter.setJoinListener(this);
        mHostRecyclerAdapter.setDeckListener(this);

        mRecyclerView.setAdapter(mHostRecyclerAdapter);
    }

    @Override
    public void onJoinSelected() {
        showJoinFragment();
    }

    @Override
    public void onDeckSelected(String deckName) {
        Intent intent = new Intent(getContext(), GameActivity.class);
        intent.setFlags(FLAG_ACTIVITY_BROUGHT_TO_FRONT | FLAG_ACTIVITY_CLEAR_TOP);

        DeckAPIClient.newInstance(getContext(), deckName);
        startActivity(intent);
    }

    public void showJoinFragment() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new JoinFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getClass().getSimpleName())
                .commit();
    }

    private HashMap<String, String> getDeckDisplayName() {
        HashMap<String, String> mDeckDisplayName = new HashMap<>();

        mDeckDisplayName.put("isthatafact", "Is That A Fact?");
        mDeckDisplayName.put("wordup", "Word Up!");
        mDeckDisplayName.put("moviebluff", "Movie Bluff!");
        mDeckDisplayName.put("itsthelaw", "It's the Law");
        mDeckDisplayName.put("theplotthickens", "The Plot Thickens");
        mDeckDisplayName.put("namethatshow", "Name that Show!");
        mDeckDisplayName.put("poetry", "Poetry");
        mDeckDisplayName.put("saymyname", "Say My Name");
        mDeckDisplayName.put("proverbs", "Proverbs");
        mDeckDisplayName.put("adultsonly", "Adults Only");
        mDeckDisplayName.put("animals", "Animals");

        return mDeckDisplayName;
    }


}
