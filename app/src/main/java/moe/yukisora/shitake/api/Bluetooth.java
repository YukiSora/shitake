package moe.yukisora.shitake.api;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
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

public class Bluetooth {
    private static final int REQUEST_ENABLE_BLUETOOTH = 1;
    public static final int DATA_TYPE_PLAYER_INFORMATION = 0;
    private static final String UUID_VALUE = "859f02dd-e6f5-4d56-826c-40f1e1bceea8";
    private static Bluetooth bluetooth;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothClient client;
    private BluetoothServer server;

    private Bluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public static Bluetooth getInstance() {
        if (bluetooth == null)
            bluetooth = new Bluetooth();

        return bluetooth;
    }

    public boolean isAvailable() {
        return bluetoothAdapter != null;
    }

    public boolean isEnabled() {
        return isAvailable() && bluetoothAdapter.isEnabled();
    }

    public void enableBluetooth(Activity activity) {
        if (isAvailable())
            activity.startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BLUETOOTH);
    }

    public void setDiscoverable(Activity activity) {
        if (bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
            activity.startActivity(intent);
        }
    }

    public void startDiscovery() {
        bluetoothAdapter.startDiscovery();
    }

    public void stopDiscovery() {
        bluetoothAdapter.cancelDiscovery();
    }

    public BluetoothServer newBluetoothServer() {
        server = new BluetoothServer();
        server.start();

        return server;
    }

    public BluetoothClient newBluetoothClient(String address) {
        client = new BluetoothClient(address);
        client.start();

        return client;
    }

    public BluetoothClient getClient() {
        return client;
    }

    public BluetoothServer getServer() {
        return server;
    }

    public void closeClient() {
        if (client != null) {
            client.close();
        }
    }

    public void closeServer() {
        if (server != null) {
            server.close();
        }
    }

    public class BluetoothServer extends Thread {
        private BluetoothServerSocket server;
        private ArrayList<Client> clients;

        BluetoothServer() {
            clients = new ArrayList<>();
        }

        public void run() {
            while (true) {
                try (BluetoothServerSocket server = bluetoothAdapter.listenUsingRfcommWithServiceRecord("shitake", UUID.fromString(UUID_VALUE))) {
                    this.server = server;
                    BluetoothSocket client;
                    while ((client = server.accept()) == null)
                        ;
                    clients.add(new Client(client));
                } catch (IOException e) {
                    break;
                }
            }
        }

        private void parseMessage(String message, Client thisClient) {
            try {
                JSONObject json = new JSONObject(message);
                switch (json.getInt("dataType")) {
                    case DATA_TYPE_PLAYER_INFORMATION:
                        //get new player info
                        JSONObject data = json.getJSONObject("data");
                        String address = data.getString("address");
                        String name = data.getString("name");
                        byte[] byteArray = Base64.decode(data.getString("picture"), Base64.DEFAULT);
                        Bitmap picture = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                        PlayerAPIClient.Player player = PlayerAPIClient.getInstance().new Player(address, name, picture);
                        PlayerAPIClient.getInstance().addPlayer(player);

                        //send other player info to new player
                        for (Client client : clients)
                            if (client != thisClient)
                                write(thisClient, PlayerAPIClient.getInstance().getPlayers().get(client.getClient().getRemoteDevice().getAddress()).toString());
                        write(thisClient, PlayerAPIClient.getInstance().getPlayers().get(MainActivity.getBluetoothAddress()).toString());

                        //send new player to other player
                        for (Client client : clients)
                            if (client != thisClient)
                                write(client, player.toString());

                        break;
                }
            } catch (JSONException ignore) {
            }
        }

        public void write(String s) {
            for (Client client : clients) {
                try {
                    client.out.write(s + "\n");
                    client.out.flush();
                } catch (IOException e) {
                    close();
                }
            }
        }

        public void write(Client client, String s) {
            try {
                client.out.write(s + "\n");
                client.out.flush();
            } catch (IOException e) {
                close();
            }
        }

        private void close() {
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

            Client(final BluetoothSocket client) {
                this.client = client;

                try {
                    in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    out = new OutputStreamWriter(client.getOutputStream());
                } catch (IOException e) {
                    close();
                }

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

            private void close() {
                try {
                    clients.remove(this);
                    PlayerAPIClient.getInstance().removePlayer(client.getRemoteDevice().getAddress());
                    client.close();
                } catch (IOException ignore) {
                }
            }

            public BluetoothSocket getClient() {
                return client;
            }
        }
    }

    public class BluetoothClient extends Thread {
        private BluetoothSocket server;
        private BufferedReader in;
        private OutputStreamWriter out;
        private boolean isConnected;

        BluetoothClient(String address) {
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);

            try {
                server = device.createRfcommSocketToServiceRecord(UUID.fromString(UUID_VALUE));
            } catch (IOException e) {
                close();
            }
        }

        public void run() {
            try {
                server.connect();
                in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                out = new OutputStreamWriter(server.getOutputStream());
                isConnected = true;
                write(PlayerAPIClient.getInstance().getPlayers().get(MainActivity.getBluetoothAddress()).toString());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                String message = in.readLine();
                                parseMessage(message);
                            }
                        } catch (IOException e) {
                            close();
                        }
                    }
                }).start();
            } catch (IOException e) {
                close();
            }
        }

        private void parseMessage(String message) {
            try {
                JSONObject json = new JSONObject(message);
                switch (json.getInt("dataType")) {
                    case DATA_TYPE_PLAYER_INFORMATION:
                        JSONObject data = json.getJSONObject("data");
                        String address = data.getString("address");
                        String name = data.getString("name");
                        byte[] byteArray = Base64.decode(data.getString("picture"), Base64.DEFAULT);
                        Bitmap picture = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                        PlayerAPIClient.getInstance().addPlayer(PlayerAPIClient.getInstance().new Player(address, name, picture));

                        break;
                }
            } catch (JSONException ignore) {
            }
        }

        public void write(String s) {
            if (isConnected) {
                try {
                    out.write(s + "\n");
                    out.flush();
                } catch (IOException e) {
                    close();
                }
            }
        }

        private void close() {
            try {
                server.close();
            } catch (IOException ignore) {
            }
        }
    }
}
