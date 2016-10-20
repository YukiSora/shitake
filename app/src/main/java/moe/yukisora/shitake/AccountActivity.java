package moe.yukisora.shitake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import moe.yukisora.shitake.ui.account.TauntFragment;

public class AccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_generic);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new TauntFragment())
                .commit();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
