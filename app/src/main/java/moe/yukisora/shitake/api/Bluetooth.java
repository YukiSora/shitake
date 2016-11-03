package moe.yukisora.shitake.api;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.UUID;

public class Bluetooth {
    private static final int REQUEST_ENABLE_BLUETOOTH = 1;
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

    public class BluetoothServer extends Thread {
        private ArrayList<Client> clients;

        BluetoothServer() {
            clients = new ArrayList<>();
        }
        public void run() {
            while (true) {
                try (BluetoothServerSocket server = bluetoothAdapter.listenUsingRfcommWithServiceRecord("shitake", UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"))) {
                    BluetoothSocket client;
                    while ((client = server.accept()) == null)
                        ;
                    clients.add(new Client(client));
                } catch (IOException ignore) {
                }
            }
        }

        private class Client {
            private BluetoothSocket client;
            private BufferedReader in;
            private OutputStreamWriter out;

            Client(BluetoothSocket client) {
                this.client = client;

                try {
                    in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    out = new OutputStreamWriter(client.getOutputStream());
                } catch (IOException ignore) {
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Log.i("poi", in.readLine());
                            } catch (IOException ignore) {
                            }
                        }
                    }
                }).start();
            }
        }
    }

    public class BluetoothClient extends Thread {
        private BluetoothSocket server;

        public BluetoothClient(String address) {
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);

            try {
                server = device.createRfcommSocketToServiceRecord(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"));
            } catch (IOException e) {
            }
        }

        public void run() {
            try {
                server.connect();
                Log.i("poi", "success");
                new OutputStreamWriter(server.getOutputStream()).write("Poiiiii");
            } catch (IOException ignore) {
            }
        }
    }
}
