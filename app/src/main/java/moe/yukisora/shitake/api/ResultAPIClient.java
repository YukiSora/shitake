package moe.yukisora.shitake.api;

import java.util.ArrayList;

import moe.yukisora.shitake.ui.game.PendingFragment;

public class ResultAPIClient {
    private static ResultAPIClient resultAPIClient;
    private ArrayList<String> addresses;

    private ResultAPIClient() {
        addresses = new ArrayList<>();
    }

    public static ResultAPIClient getResultAPIClient() {
        if (resultAPIClient == null)
            resultAPIClient = new ResultAPIClient();

        return resultAPIClient;
    }

    public ArrayList<String> getAddresses() {
        return addresses;
    }

    public void addAddress(String address) {
        addresses.add(address);
        if (PendingFragment.getFragmentTask() != null)
            PendingFragment.getFragmentTask().done(address);
    }
}
