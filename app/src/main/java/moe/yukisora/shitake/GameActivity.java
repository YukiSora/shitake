package moe.yukisora.shitake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import moe.yukisora.shitake.api.Bluetooth;
import moe.yukisora.shitake.api.GameAPIClient;
import moe.yukisora.shitake.ui.game.QuestionFragment;

/**
 * Created by Delacrix on 22/09/2016.
 */

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_generic);

        GameAPIClient.getInstance().startGame();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new QuestionFragment())
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Bluetooth.getInstance().closeClient();
        Bluetooth.getInstance().closeServer();
    }
}
