package br.com.tratto.Fragment.Profile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.tratto.APIManager.UserManager;
import br.com.tratto.CustomView.FontEditText;
import br.com.tratto.Fragment.BaseFragment;
import br.com.tratto.Model.Profile;
import br.com.tratto.Model.User;
import br.com.tratto.R;
import br.com.tratto.Utils.UtilFunctions;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends BaseFragment implements View.OnClickListener, UserManager.UserCallback {

    private static String TAG = "ChangePasswordFragment";

    private FontEditText mOldPassowrdField, mNewPasswordField, mConfirmPasswordField;

    private Profile currentProfile;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentProfile = UtilFunctions.getCurrentProfile(getActivity());
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        mOldPassowrdField = view.findViewById(R.id.text_old_password);
        mNewPasswordField = view.findViewById(R.id.text_new_password);
        mConfirmPasswordField = view.findViewById(R.id.text_confirm_password);

        view.findViewById(R.id.btn_update_password).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_password:
                updatePassword();
                break;
        }
    }

    private void updatePassword() {
        String oldPassword = mOldPassowrdField.getText().toString().trim();
        String newPassword = mNewPasswordField.getText().toString().trim();
        String confPassword = mConfirmPasswordField.getText().toString().trim();

        if (newPassword.length() < 6 || newPassword.length() > 32) {
            UtilFunctions.showMessageDialog(getActivity(), "Error", "Password length must be from 6 to 32 characters, at least one uppercase, at least one lowercase, at least one digit.");
            return;
        } else if (!UtilFunctions.isPasswordCorrect(newPassword)) {
            UtilFunctions.showMessageDialog(getActivity(), "Error", "Password must contain at least one uppercase, at least one lowercase, at least one digit.");
            return;
        }

        if (confPassword.equals(newPassword)) {
            Log.d(TAG, "Attempt to update password");
            UtilFunctions.showlLoadingDialog(getActivity());
            UserManager userManager = new UserManager(getActivity(), this);
            userManager.updatePassword(confPassword, String.valueOf(currentProfile.userId));
        } else {
            UtilFunctions.showMessageDialog(getActivity(), "Error", "Confirm password doesn't match.");
        }
    }

    @Override
    public void onFailed(String error) {
        UtilFunctions.dismissLoadingDialog();
        UtilFunctions.showMessageDialog(getActivity(), "Error", error);
    }

    @Override
    public void onSuccess(@Nullable User user) {
        UtilFunctions.dismissLoadingDialog();
    }
}
