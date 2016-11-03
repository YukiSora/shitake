package moe.yukisora.shitake.ui.lobby;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import moe.yukisora.shitake.R;

/**
 * Created by Delacrix on 10/10/2016.
 */

public class MainFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private ArrayList<String> setDeckList() {
        ArrayList<String> mDeckList = new ArrayList<>();

        mDeckList.add("Is That A Fact?");
        mDeckList.add("Word Up!");
        mDeckList.add("Movie Bluff!");
        mDeckList.add("It's the Law");
        mDeckList.add("The Plot Thickens");
        mDeckList.add("Name that Show!");
        mDeckList.add("Poetry");
        mDeckList.add("Say My Name");
        mDeckList.add("Proverbs");
        mDeckList.add("Adults Only");
        mDeckList.add("Animals");

        return mDeckList;
    }
}
