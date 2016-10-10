package moe.yukisora.shitake;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import static android.content.Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

/**
 * Created by Delacrix on 22/09/2016.
 */

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mDrawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.drawer_layout, null);
        mNavigationView = (NavigationView) mDrawerLayout.findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        getLayoutInflater().inflate(layoutResID, mNavigationView, true);

        super.setContentView(mDrawerLayout);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account:
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.activity_main_vg_fragment, new SignupFragment())
//                        .commit();
                break;

            case R.id.lobby:
                break;

            case R.id.game:
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.setFlags(FLAG_ACTIVITY_BROUGHT_TO_FRONT | FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

                break;

            case R.id.tutorial:
                break;

            case R.id.about:
                break;

        }

        mDrawerLayout.closeDrawers();
        return false;
    }
}
