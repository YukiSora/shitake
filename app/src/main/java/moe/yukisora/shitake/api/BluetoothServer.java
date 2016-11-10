package moe.yukisora.shitake.api;

import android.bluetooth.BluetoothServerSocket;
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
import java.util.ArrayList;
import java.util.UUID;

import moe.yukisora.shitake.MainActivity;

public class BluetoothServer extends Thread {
    private BluetoothServerSocket server;
    private ArrayList<Client> clients;

    BluetoothServer() {
        clients = new ArrayList<>();
    }

    @Override
    public void run() {
        //looping to accept client
        while (true) {
            try (BluetoothServerSocket server = Bluetooth.getInstance().getBluetoothAdapter().listenUsingRfcommWithServiceRecord("shitake", UUID.fromString(Bluetooth.UUID_VALUE))) {
                this.server = server;

                BluetoothSocket client;
                while ((client = server.accept()) == null)
                    ;
                clients.add(new Client(client));
            } catch (IOException e) {
                close();
            }
        }
    }

    private void parseMessage(String message, Client thisClient) {
        try {
            JSONObject json = new JSONObject(message);
            switch (json.getInt("dataType")) {
                case Bluetooth.DATA_TYPE_PLAYER_INFORMATION:
                    playerInformationProcess(json.getJSONObject("data"), thisClient);
                    break;
            }
        } catch (JSONException ignore) {
        }
    }

    private void playerInformationProcess(JSONObject data, Client thisClient) throws JSONException {
        //get new player info
        String address = data.getString("address");
        String name = data.getString("name");
        byte[] byteArray = Base64.decode(data.getString("picture"), Base64.DEFAULT);
        Bitmap picture = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        //create new Player
        PlayerAPIClient.Player player = PlayerAPIClient.getInstance().new Player(address, name, picture);
        PlayerAPIClient.getInstance().addPlayer(player);

        //send other players information to new player
        for (Client client : clients)
            if (client != thisClient)
                sendTo(thisClient, Bluetooth.wrapMessage(Bluetooth.DATA_TYPE_PLAYER_INFORMATION, PlayerAPIClient.getInstance().get(client.getAddress()).toJSON()));
        sendTo(thisClient, Bluetooth.wrapMessage(Bluetooth.DATA_TYPE_PLAYER_INFORMATION, PlayerAPIClient.getInstance().get(MainActivity.getBluetoothAddress()).toJSON()));

        //send new player information to other players
        sendExclude(thisClient, Bluetooth.wrapMessage(Bluetooth.DATA_TYPE_PLAYER_INFORMATION, player.toJSON()));
    }

    public void sendTo(Client thisClient, String s) {
        thisClient.write(s);
    }

    public void sendExclude(Client thisClient, String s) {
        for (Client client : clients) {
            if (client != thisClient)
                client.write(s);
        }
    }

    public void close() {
        try {
            server.close();
        } catch (IOException ignore) {
        }

        for (Client client : clients) {
            client.close();
        }
    }

    private class Client {
        private BluetoothSocket client;
        private BufferedReader in;
        private OutputStreamWriter out;
        private String address;

        Client(final BluetoothSocket client) {
            this.client = client;
            address = client.getRemoteDevice().getAddress();

            try {
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new OutputStreamWriter(client.getOutputStream());
            } catch (IOException e) {
                close();
            }

            read();
        }

        private void read() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String message = in.readLine();
                            parseMessage(message, Client.this);
                        }
                    } catch (IOException e) {
                        close();
                    }
                }
            }).start();
        }

        private void write(final String s) {
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

        private void close() {
            try {
                clients.remove(this);
                PlayerAPIClient.getInstance().removePlayer(client.getRemoteDevice().getAddress());
                client.close();
            } catch (IOException ignore) {
            }
        }

        private String getAddress() {
            return address;
        }
    }
}