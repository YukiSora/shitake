package moe.yukisora.shitake;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

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
    private static Activity activity;
    private static String bluetoothAddress;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    public static String getBluetoothAddress() {
        return bluetoothAddress;
    }

    public static Activity getActivity() {
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        //get own Bluetooth address
        bluetoothAddress = android.provider.Settings.Secure.getString(getContentResolver(), "bluetooth_address");

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView)findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        setupDrawerLayout();

        // Singleton
        GameAPIClient.newInstance(this);
        DeckAPIClient.newInstance(this, "isthatafact");
        PlayerAPIClient.getInstance();

        //Configure bluetooth
        Bluetooth bluetooth = Bluetooth.getInstance();
        if (!bluetooth.isAvailable())
            new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.bluetooth_is_not_available)).setMessage(getResources().getString(R.string.bluetooth_requested)).setPositiveButton("Ok", null).show();
        if (!bluetooth.isEnabled())
            bluetooth.enableBluetooth(this);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new MainFragment())
                .commit();
    }

    public void setupDrawerLayout() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(getResources().getString(R.string.app_name));
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            super.onBackPressed();
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

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
