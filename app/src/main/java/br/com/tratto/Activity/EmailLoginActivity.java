package br.com.tratto.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import br.com.tratto.APIManager.LoginManager;
import br.com.tratto.APIManager.ProfileManager;
import br.com.tratto.CustomView.FontButton;
import br.com.tratto.CustomView.FontEditText;
import br.com.tratto.Interface.DialogButtonCallback;
import br.com.tratto.Model.Profile;
import br.com.tratto.Model.Response.UserResponse;
import br.com.tratto.Model.User;
import br.com.tratto.R;
import br.com.tratto.Utils.AppManager;
import br.com.tratto.Utils.UtilFunctions;

public class EmailLoginActivity extends AppCompatActivity implements View.OnClickListener, LoginManager.LoginCallback, ProfileManager.ProfileManagerCallback, DialogButtonCallback {

    private static String TAG = "EamilLoginActivity";

    private FontEditText edtEmail, edtPassword;
    private FontButton btnSignIn, btnForgotPassword;
    private LoginManager loginManager;
    private ProfileManager profileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        edtEmail = findViewById(R.id.edt_login_email);
        edtPassword = findViewById(R.id.edt_login_password);
        btnSignIn = findViewById(R.id.btn_email_login_confirm);
        btnForgotPassword = findViewById(R.id.btn_login_forget_password);

        btnSignIn.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);
    }

    private void attemptLogIn() {
        String email = edtEmail.getText().toString().trim();
        if (email.isEmpty() || edtPassword.getText().toString().isEmpty()) {
            UtilFunctions.showMessageDialog(this, "Error", "Please fill email and password.");
        } else {
            UtilFunctions.showlLoadingDialog(this);
            loginManager = new LoginManager(this, this);
            Log.d(TAG, getDeviceID());
            loginManager.login(email, edtPassword.getText().toString(), getDeviceID());
        }
    }

    private void attemptForgotPassword() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void attemptToPhoneVerify() {
        Intent intent = new Intent(this, PhoneVerificationActivity.class);
        startActivity(intent);
    }

    private void attemptToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void getAllProfiles() {
        UtilFunctions.showlLoadingDialog(this);
        profileManager = new ProfileManager(this, this);
        profileManager.getAllProfile();
    }

    private String getDeviceID() {
        return Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSignIn) {
            attemptLogIn();
        } else {
            attemptForgotPassword();
        }
    }

    @Override
    public void onSuccess(UserResponse response) {
        UtilFunctions.dismissLoadingDialog();
        if (response.userId != null) {
            Log.d(TAG, "Require phone number verification");
            AppManager.userId = response.userId;
            attemptToPhoneVerify();
        } else {
            if (response.user != null){
                Log.d(TAG, "Login Success");
                User user = response.user;
                UtilFunctions.setCurrentUser(this, user);
                if (user.profileIds.size() > 0) {
                    getAllProfiles();
                } else {
                    //Attempt to create new profile
                }
            } else {
                UtilFunctions.showMessageDialog(this, "Error", "Unknown error.");
            }
        }
    }

    @Override
    public void onSuccess(ArrayList<Profile> profiles, boolean isLastPage, int page) {
        UtilFunctions.dismissLoadingDialog();
        // Save profiles
        AppManager.profiles.clear();
        for (int i = 0; i < profiles.size(); i++) {
            AppManager.profiles.add(profiles.get(i));
        }
        UtilFunctions.showProfileChooseDialog(this, profiles, this);
    }

    @Override
    public void onFailed(String error) {
        UtilFunctions.dismissLoadingDialog();
        UtilFunctions.showMessageDialog(this, "Error", error);
    }

    @Override
    public void onContinue(Profile profile) {
        UtilFunctions.setCurrentProfile(this, profile);
        attemptToMain();
    }
}
