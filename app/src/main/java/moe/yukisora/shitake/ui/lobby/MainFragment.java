package moe.yukisora.shitake.ui.lobby;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.api.Bluetooth;

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

        view.findViewById(R.id.main_btn_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Bluetooth.getInstance().isEnabled(getActivity())) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.activity_main_vg_fragment, new JoinFragment())
                            .addToBackStack("main")
                            .commit();
                }
                else {
                    new AlertDialog.Builder(getActivity()).setTitle(getResources().getString(R.string.bluetooth_is_not_available)).setMessage(getResources().getString(R.string.bluetooth_requested)).setPositiveButton("Ok", null).show();
                }
            }
        });

        view.findViewById(R.id.main_btn_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Bluetooth.getInstance().isEnabled(getActivity())) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.activity_main_vg_fragment, new HostFragment())
                            .addToBackStack("main")
                            .commit();
                }
                else {
                    new AlertDialog.Builder(getActivity()).setTitle(getResources().getString(R.string.bluetooth_is_not_available)).setMessage(getResources().getString(R.string.bluetooth_requested)).setPositiveButton("Ok", null).show();
                }
            }
        });

        view.findViewById(R.id.main_btn_tutorial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("poi", "I am going to Tutorial Fragment.");
            }
        });

        view.findViewById(R.id.main_btn_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("poi", "I am going to About Fragment.");
            }
        });
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
