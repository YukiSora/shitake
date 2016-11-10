package moe.yukisora.shitake;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import moe.yukisora.shitake.api.Bluetooth;
import moe.yukisora.shitake.api.DeckAPIClient;
import moe.yukisora.shitake.api.GameAPIClient;
import moe.yukisora.shitake.api.PlayerAPIClient;
import moe.yukisora.shitake.ui.lobby.MainFragment;

import static android.content.Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

/**
 * Created by Delacrix on 22/09/2016.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static String sBluetoothAddress;
    private DrawerLayout mDrawerLayout;

    public static String getBluetoothAddress() {
        return sBluetoothAddress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Own Bluetooth Address
        sBluetoothAddress = android.provider.Settings.Secure.getString(getContentResolver(), "bluetooth_address");

        // Singleton
        DeckAPIClient.newInstance(this);
        GameAPIClient.newInstance(this);
        PlayerAPIClient.getInstance();

        // Temporary Testing
        DeckAPIClient.getInstance().setCurrentDeck("Word Up!");

        // Configure Bluetooth
        Bluetooth bluetooth = Bluetooth.getInstance();
        if (!bluetooth.isAvailable())
            new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.bluetooth_is_not_available)).setMessage(getResources().getString(R.string.bluetooth_requested)).setPositiveButton("Ok", null).show();
        if (!bluetooth.isEnabled())
            bluetooth.enableBluetooth(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, MainFragment.newInstance(), "main")
                .commit();

        // Temporary - Drawer Layout
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView mNavigationView = (NavigationView)findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        
    }

    @Override
    public void onBackPressed() {
        String tag = getSupportFragmentManager().findFragmentById(R.id.activity_main_vg_fragment).getTag();
        if (tag != null && tag.equals("main")) {
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main_vg_fragment, new MainFragment(), "main")
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new MainFragment(), "main")
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Bluetooth.getInstance().closeClient();
        Bluetooth.getInstance().closeServer();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.account:
                intent = new Intent(getApplicationContext(), AccountActivity.class);
                intent.setFlags(FLAG_ACTIVITY_BROUGHT_TO_FRONT | FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                break;

            case R.id.lobby:
                intent = new Intent(getApplicationContext(), LobbyActivity.class);
                intent.setFlags(FLAG_ACTIVITY_BROUGHT_TO_FRONT | FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                break;

            case R.id.game:
                intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.setFlags(FLAG_ACTIVITY_BROUGHT_TO_FRONT | FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

                break;

            case R.id.tutorial:
                intent = new Intent(getApplicationContext(), AccountActivity.class);
                intent.setFlags(FLAG_ACTIVITY_BROUGHT_TO_FRONT | FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

                break;

            case R.id.about:
                intent = new Intent(getApplicationContext(), AccountActivity.class);
                intent.setFlags(FLAG_ACTIVITY_BROUGHT_TO_FRONT | FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                break;

        }

        mDrawerLayout.closeDrawers();
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}
