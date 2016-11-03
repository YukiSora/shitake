package moe.yukisora.shitake;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import moe.yukisora.shitake.api.DeckAPIClient;
import moe.yukisora.shitake.api.GameAPIClient;
import moe.yukisora.shitake.ui.lobby.HostFragment;

import static android.content.Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

/**
 * Created by Delacrix on 22/09/2016.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        setupDrawerLayout();

        // Singleton
        GameAPIClient.newInstance(this);
        DeckAPIClient.newInstance(this, "isthatafact");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new HostFragment())
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
