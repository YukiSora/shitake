package moe.yukisora.shitake;

import android.os.Bundle;

/**
 * Created by Delacrix on 22/09/2016.
 */

public class GameActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new QuestionFragment())
                .commit();
    }
}
