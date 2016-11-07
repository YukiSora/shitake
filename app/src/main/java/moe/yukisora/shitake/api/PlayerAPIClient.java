package moe.yukisora.shitake.api;

import android.graphics.Bitmap;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import moe.yukisora.shitake.ui.lobby.WaitingFragment;

public class PlayerAPIClient {
    private static PlayerAPIClient playerAPIClient;
    private HashMap<String, Player> players;

    private PlayerAPIClient() {
        players = new HashMap<>();
    }

    public static PlayerAPIClient getInstance() {
        if (playerAPIClient == null)
            playerAPIClient = new PlayerAPIClient();

        return playerAPIClient;
    }

    public void addPlayer(String address, String name, Bitmap picture) {
        Player player = new Player(address, name, picture);
        players.put(address, player);
        WaitingFragment.getFragmentHandler().addToList(player);
    }

    public void addPlayer(Player player) {
        players.put(player.address, player);
        WaitingFragment.getFragmentHandler().addToList(player);
    }

    public void removePlayer(String address) {
        WaitingFragment.getFragmentHandler().removeFromList(address);
        players.remove(address);
    }

    public void clearPlayer() {
        players.clear();
    }

    public HashMap<String, Player> getPlayers() {
        return players;
    }

    public class Player {
        public String address;
        public String name;
        public Bitmap picture;

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

        @Override
        public String toString() {
            JSONObject json = new JSONObject();
            try {
                json.put("dataType", Bluetooth.DATA_TYPE_PLAYER_INFORMATION);

                JSONObject data = new JSONObject();
                data.put("address", address);
                data.put("name", name);
                data.put("picture", bitmapToString());

                json.put("data", data);
            } catch (JSONException ignore) {
            }

            return json.toString();
        }
    }
}
