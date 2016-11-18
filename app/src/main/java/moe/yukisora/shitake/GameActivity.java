package moe.yukisora.shitake;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import moe.yukisora.shitake.api.Bluetooth;
import moe.yukisora.shitake.ui.game.QuestionFragment;

/**
 * Created by Delacrix on 22/09/2016.
 */

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_generic);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, QuestionFragment.newInstance())
                .commit();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.quit_game))
                .setMessage(getResources().getString(R.string.are_you_sure_quit_this_game))
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        superOnBackPressed();
                    }
                })
                .setPositiveButton("No", null)
                .show();
    }

    private void superOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Bluetooth.getInstance().closeClient();
        Bluetooth.getInstance().closeServer();
    }
}
