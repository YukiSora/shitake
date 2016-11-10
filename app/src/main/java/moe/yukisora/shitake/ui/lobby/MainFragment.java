package moe.yukisora.shitake.ui.lobby;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moe.yukisora.shitake.AccountActivity;
import moe.yukisora.shitake.R;
import moe.yukisora.shitake.api.Bluetooth;

/**
 * Created by Delacrix on 10/10/2016.
 */

public class MainFragment extends Fragment {

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Bluetooth bluetooth = Bluetooth.getInstance();

        view.findViewById(R.id.main_btn_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AccountActivity.class);
                startActivity(intent);
            }
        });

        //Join Game
        view.findViewById(R.id.main_btn_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bluetooth.isEnabled()) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.activity_main_vg_fragment, JoinFragment.newInstance())
                            .commit();
                }
                else {
                    enableBluetooth();
                }
            }
        });

        //Create Game
        view.findViewById(R.id.main_btn_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bluetooth.isEnabled()) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.activity_main_vg_fragment, HostFragment.newInstance())
                            .commit();
                }
                else {
                    enableBluetooth();
                }
            }
        });

        //Tutorial
        view.findViewById(R.id.main_btn_tutorial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("poi", "I am going to Tutorial Fragment.");
            }
        });

        //About Us
        view.findViewById(R.id.main_btn_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("poi", "I am going to About Fragment.");
            }
        });
    }

    public void enableBluetooth() {
        Bluetooth bluetooth = Bluetooth.getInstance();

        if (bluetooth.isAvailable()) {
            bluetooth.enableBluetooth(getActivity());
        }
        else {
            new AlertDialog.Builder(getActivity())
                    .setTitle(getResources().getString(R.string.bluetooth_is_not_available))
                    .setMessage(getResources().getString(R.string.bluetooth_requested))
                    .setPositiveButton("Ok", null)
                    .show();
        }
    }
}
