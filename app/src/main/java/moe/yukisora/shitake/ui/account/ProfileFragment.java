package moe.yukisora.shitake.ui.account;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.model.UserManager;

public class ProfileFragment extends Fragment {

    private UserManager userManager;

    private ImageView ivProfilePicture;
    private EditText etNickname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userManager = new UserManager(getActivity());

        etNickname = ((EditText) view.findViewById(R.id.et_profile_nickname));
        etNickname.setText(userManager.getName());

        etNickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    userManager.setName(etNickname.getText().toString());
                }
            }
        });

        String picturePath = userManager.getProfilePicture();
        ((TextView) view.findViewById(R.id.tvtest)).setText("A"+picturePath);
        ivProfilePicture = (ImageView) getView().findViewById(R.id.iv_profile_picture);
        if (!picturePath.equals("")) {
            ivProfilePicture.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        } else {
            ivProfilePicture.setImageResource(R.drawable.no_picture);
        }

        view.findViewById(R.id.bt_profile_change_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_main_vg_fragment, new TauntFragment(), "taunt")
                        .commit();
            }
        });
    }

}
