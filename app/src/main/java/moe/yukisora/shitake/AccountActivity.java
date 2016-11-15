package moe.yukisora.shitake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import moe.yukisora.shitake.ui.account.ProfileFragment;

public class AccountActivity extends AppCompatActivity {

    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_generic);

        goToProfileFragment();
    }

    @Override
    public void onBackPressed() {
        String tag = getSupportFragmentManager().findFragmentById(R.id.activity_main_vg_fragment).getTag();
        String tag2 = tag != null ? tag : "";

        if (tag2.equals("taunt")) {
            goToProfileFragment();
        } else if (tag2.equals("profile")) {
            if (profileFragment == null || profileFragment.canExit()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    private void goToProfileFragment() {
        profileFragment = new ProfileFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, profileFragment, "profile")
                .commit();
    }
}
