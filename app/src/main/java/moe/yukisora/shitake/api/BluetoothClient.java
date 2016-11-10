package moe.yukisora.shitake.api;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.UUID;

import moe.yukisora.shitake.MainActivity;
import moe.yukisora.shitake.ui.lobby.WaitingFragment;

public class BluetoothClient extends Thread {
    private BluetoothSocket server;
    private BufferedReader in;
    private OutputStreamWriter out;

    BluetoothClient(String address) {
        try {
            BluetoothDevice device = Bluetooth.getInstance().getBluetoothAdapter().getRemoteDevice(address);
            server = device.createRfcommSocketToServiceRecord(UUID.fromString(Bluetooth.UUID_VALUE));
        } catch (IOException e) {
            close();
        }
    }

    @Override
    public void run() {
        try {
            server.connect();

            //stream
            in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            out = new OutputStreamWriter(server.getOutputStream());

            //send self player information to server
            write(Bluetooth.wrapMessage(Bluetooth.DATA_TYPE_PLAYER_INFORMATION, PlayerAPIClient.getInstance().get(MainActivity.getBluetoothAddress()).toJSON()));
        } catch (IOException | JSONException e) {
            close();
        }

        read();
    }

    private void parseMessage(String message) {
        try {
            JSONObject json = new JSONObject(message);
            switch (json.getInt("dataType")) {
                case Bluetooth.DATA_TYPE_PLAYER_INFORMATION:
                    playerInformationProcess(json.getJSONObject("data"));
                    break;
                case Bluetooth.DATA_TYPE_START_GAME:
                    startGame();
                    break;
            }
        } catch (JSONException ignore) {
        }
    }

    private void playerInformationProcess(JSONObject data) throws JSONException {
        String address = data.getString("address");
        String name = data.getString("name");
        byte[] byteArray = Base64.decode(data.getString("picture"), Base64.DEFAULT);
        Bitmap picture = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        PlayerAPIClient.getInstance().addPlayer(PlayerAPIClient.getInstance().new Player(address, name, picture));
    }

    private void startGame() {
        WaitingFragment.getFragmentTask().startGame();
    }

    private void read() {
        try {
            while (true) {
                String message = in.readLine();
                parseMessage(message);
            }
        } catch (IOException e) {
            close();
        }
    }

    public void write(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    out.write(s + "\n");
                    out.flush();
                } catch (IOException e) {
                    close();
                }
            }
        }).start();
    }

    public void close() {
        try {
            server.close();
        } catch (IOException ignore) {
        }
    }
}
