package moe.yukisora.shitake.ui.account;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import moe.yukisora.shitake.R;
import moe.yukisora.shitake.model.UserManager;

import static android.content.ContentValues.TAG;

public class ProfileFragment extends Fragment {

    private UserManager mUserManager;
    private ImageView ivProfilePicture;
    private EditText etNickname;
    private Button mSaveButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        mUserManager = new UserManager(getActivity());

        etNickname = (EditText) rootView.findViewById(R.id.et_profile_nickname);
        mSaveButton = (Button) rootView.findViewById(R.id.bt_profile_change_picture);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTextNickname();

        String picturePath = mUserManager.getProfilePicture();

        ((TextView) view.findViewById(R.id.tvtest)).setText("A" + picturePath);
        ivProfilePicture = (ImageView) getView().findViewById(R.id.iv_profile_picture);

        Log.d(TAG, "onViewCreated: " + picturePath);

        if (!picturePath.equals("")) {
            ivProfilePicture.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        } else {
            ivProfilePicture.setImageResource(R.drawable.no_picture);
        }

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_main_vg_fragment, new TauntFragment(), "taunt")
                        .commit();
            }
        });
    }

    public void setTextNickname(){
        etNickname.setText(mUserManager.getName());

        etNickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    mUserManager.setName(etNickname.getText().toString());
                }
            }
        });
    }

}
