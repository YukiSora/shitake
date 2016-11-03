package moe.yukisora.shitake.api;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

public class Bluetooth {
    private static final int REQUEST_ENABLE_BLUETOOTH = 1;
    private static Bluetooth bluetooth;
    private BluetoothAdapter bluetoothAdapter;

    private Bluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public static Bluetooth getInstance() {
        if (bluetooth == null)
            bluetooth = new Bluetooth();

        return bluetooth;
    }

    public boolean isEnabled(Activity activity) {
        if (bluetoothAdapter == null)
            return false;

        if (!bluetoothAdapter.isEnabled())
            activity.startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BLUETOOTH);

        return bluetoothAdapter.isEnabled();
    }

    public void setDiscoverable(Activity activity) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        activity.startActivity(intent);
    }

    public void search() {
        bluetoothAdapter.startDiscovery();
    }
}
