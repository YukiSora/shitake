package moe.yukisora.shitake.api;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by yukisora on 11/4/16.
 */

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
        players.put(address, new Player(address, name, picture));
    }

    public void addPlayer(Player player) {
        players.put(player.address, player);
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
    }
}
