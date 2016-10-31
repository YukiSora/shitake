package moe.yukisora.shitake.ui.game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import moe.yukisora.shitake.R;


/**
 * Created by Delacrix on 22/09/2016.
 */

public class PendingFragment extends Fragment {
    private Button nextButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pending, container, false);

        nextButton = (Button) rootView.findViewById(R.id.next_button);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PendingFragment.this.showVoteFragment();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    public void showVoteFragment() {
        PendingFragment.this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new VoteFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getClass().getSimpleName())
                .commit();
    }
}
