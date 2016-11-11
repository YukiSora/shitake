package moe.yukisora.shitake.api;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.MainActivity;
import moe.yukisora.shitake.ui.lobby.WaitingFragment;

public class PlayerAPIClient {
    private static PlayerAPIClient playerAPIClient;
    private ArrayList<String> playersInOrder;
    private HashMap<String, Player> players;
    private Handler handler;

    private PlayerAPIClient() {
        playersInOrder = new ArrayList<>();
        players = new HashMap<>();
        handler = new Handler();
    }

    public static PlayerAPIClient getInstance() {
        if (playerAPIClient == null)
            playerAPIClient = new PlayerAPIClient();

        return playerAPIClient;
    }

    public void addSelf(Resources resources) {
        String address = MainActivity.getBluetoothAddress();
        String name = "Yuki Sora";
        Bitmap picture = BitmapFactory.decodeResource(resources, R.drawable.picture);

        Player player = new Player(address, name, picture);
        players.put(address, player);
        if (!playersInOrder.contains(address))
            playersInOrder.add(address);
    }

    public void addPlayer(String address, String name, Bitmap picture) {
        Player player = new Player(address, name, picture);
        players.put(address, player);
        if (!playersInOrder.contains(address))
            playersInOrder.add(address);

        handler.post(new Runnable() {
            @Override
            public void run() {
                WaitingFragment.getAdapter().notifyItemInserted(players.size() - 1);
            }
        });
    }

    public void addPlayer(Player player) {
        players.put(player.address, player);
        if (!playersInOrder.contains(player.address))
            playersInOrder.add(player.address);

        handler.post(new Runnable() {
            @Override
            public void run() {
                WaitingFragment.getAdapter().notifyItemInserted(players.size() - 1);
            }
        });
    }

    public void removePlayer(String address) {
        final int index = playersInOrder.indexOf(address);
        players.remove(address);
        playersInOrder.remove(address);

        handler.post(new Runnable() {
            @Override
            public void run() {
                WaitingFragment.getAdapter().notifyItemRemoved(index);
            }
        });
    }

    public void clearPlayer() {
        players.clear();
        playersInOrder.clear();
    }

    public HashMap<String, Player> getPlayers() {
        return players;
    }

    public Player get(int position) {
        return players.get(playersInOrder.get(position));
    }

    public Player get(String address) {
        return players.get(address);
    }

    public class Player {
        public String address;
        public String name;
        public Bitmap picture;
        public int score;

        public Player(String address, String name, Bitmap picture) {
            this.address = address;
            this.name = name;
            this.picture = picture;
        }

        private String bitmapToString() {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 100, out);
            byte[] byteArray = out.toByteArray();

            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }

        public JSONObject toJSON() {
            JSONObject json = new JSONObject();
            try {
                json.put("address", address);
                json.put("name", name);
                json.put("picture", bitmapToString());
            } catch (JSONException ignore) {
            }

            return json;
        }
    }
}
