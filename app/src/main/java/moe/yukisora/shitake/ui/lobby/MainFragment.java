package moe.yukisora.shitake.ui.lobby;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        final Bluetooth bluetooth = Bluetooth.getInstance();

        //Join Game
        view.findViewById(R.id.main_btn_join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bluetooth.isEnabled()) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.activity_main_vg_fragment, new JoinFragment())
                            .addToBackStack("main")
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
                            .replace(R.id.activity_main_vg_fragment, new HostFragment())
                            .addToBackStack("main")
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
