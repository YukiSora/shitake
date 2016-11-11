package moe.yukisora.shitake.ui.game;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import moe.yukisora.shitake.MainActivity;
import moe.yukisora.shitake.R;
import moe.yukisora.shitake.api.AnswerAPIClient;

/**
 * Created by Delacrix on 10/10/2016.
 */

public class VoteFragment extends Fragment{
    private LinearLayout voteAnswerLinearLayout;

    public static VoteFragment newInstance() {
        Bundle args = new Bundle();
        VoteFragment fragment = new VoteFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vote, container, false);

        voteAnswerLinearLayout = (LinearLayout)view.findViewById(R.id.voteAnswerLinearLayout);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        populateAnswers();
    }

    public void populateAnswers() {
        for (String address : AnswerAPIClient.getInstance().getAnswers().keySet())
            if (!address.equals("correct") && !address.equals(MainActivity.getBluetoothAddress()))
                addPendingViewFromLayoutResource(voteAnswerLinearLayout, address, AnswerAPIClient.getInstance().getAnswers().get(address));
    }

    public View addPendingViewFromLayoutResource(LinearLayout linearLayout, final String address, String answer) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_answer, linearLayout, false);

        Button answerButton = (Button)view.findViewById(R.id.view_btn_answer);
        answerButton.setAllCaps(true);
        answerButton.setText(answer);
        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("poi", address);
            }
        });

        linearLayout.addView(view);

        return view;
    }

    public void showAnswerFragment() {
        VoteFragment.this.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main_vg_fragment, new AnswerFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(getClass().getSimpleName())
                .commit();
    }
}
