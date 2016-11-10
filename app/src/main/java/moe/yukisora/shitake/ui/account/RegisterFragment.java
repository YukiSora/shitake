package moe.yukisora.shitake.ui.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import moe.yukisora.shitake.R;

public class RegisterFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etNickname = (EditText) getView().findViewById(R.id.et_register_nickname);
                String nickname = etNickname.getText().toString();
                System.out.println(nickname);
                etNickname.setText("");
            }
        });
    }


}
