package moe.yukisora.shitake.ui.game;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import moe.yukisora.shitake.R;


/**
 * Created by Delacrix on 22/09/2016.
 */

public class QuestionFragment extends Fragment {

    private Button submitButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question, container, false);

        submitButton = (Button) rootView.findViewById(R.id.submit_button);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionFragment.this.showPendingFragment();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    public void showPendingFragment(){
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new PendingFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack("QuestionFragment")
                .commit();
    }
}
