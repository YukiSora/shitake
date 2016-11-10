package moe.yukisora.shitake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import moe.yukisora.shitake.ui.account.ProfileFragment;

public class AccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_generic);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new ProfileFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        String tag = getSupportFragmentManager().findFragmentById(R.id.activity_main_vg_fragment).getTag();

        if (tag != null && tag.equals("taunt")) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.activity_main_vg_fragment, new ProfileFragment())
                    .commit();
        } else {
            super.onBackPressed();
        }
    }
}
