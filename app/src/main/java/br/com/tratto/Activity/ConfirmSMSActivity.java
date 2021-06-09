package br.com.tratto.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alimuzaffar.lib.pin.PinEntryEditText;

import java.util.ArrayList;

import br.com.tratto.APIManager.ProfileManager;
import br.com.tratto.APIManager.UserManager;
import br.com.tratto.CustomView.FontButton;
import br.com.tratto.CustomView.FontTextView;
import br.com.tratto.Interface.DialogButtonCallback;
import br.com.tratto.Model.Profile;
import br.com.tratto.Model.User;
import br.com.tratto.R;
import br.com.tratto.Utils.AppManager;
import br.com.tratto.Utils.UtilFunctions;

public class ConfirmSMSActivity extends AppCompatActivity implements View.OnClickListener, UserManager.UserCallback, ProfileManager.ProfileManagerCallback, DialogButtonCallback {

    private static String TAG = "ConfirmSMS Activity";
    private FontButton btnConfirmSMS;
    private UserManager userManager;
    private String pinValue = "";

    private int parent = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_sms);

        PinEntryEditText edtSMSNumber = findViewById(R.id.edit_confirm_sms_number);
        FontTextView btnResend = findViewById(R.id.btn_confirm_resend_sms);
        btnConfirmSMS = findViewById(R.id.btn_confirm_sms);

        btnConfirmSMS.setOnClickListener(this);
        btnResend.setOnClickListener(this);

        edtSMSNumber.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
            @Override
            public void onPinEntered(CharSequence str) {
                pinValue = str.toString().trim();
                if (str.length() == 0) {
                    btnConfirmSMS.setEnabled(false);
                } else {
                    btnConfirmSMS.setEnabled(true);
                }
            }
        });

        userManager = new UserManager(this, this);
        parent = getIntent().getIntExtra("parent", 0);
    }

    private void attemptToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void getAllProfiles() {
        UtilFunctions.showlLoadingDialog(this);

        ProfileManager profileManager;
        profileManager = new ProfileManager(this, this);
        profileManager.getAllProfile();
    }

    private String getDeviceID() {
        return Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
    }

    @Override
    public void onClick(View v) {
        if (v == btnConfirmSMS) {
            if (pinValue.isEmpty()) {
                UtilFunctions.showMessageDialog(this, "Error", "Enter SMS code.");
            } else {
                switch (parent) {
                    case 1:
                        UtilFunctions.showlLoadingDialog(this);
                        userManager.confirmSMSResetPassword(AppManager.userId, pinValue);
                        break;
                    case 2:
                        UtilFunctions.showlLoadingDialog(this);
                        userManager.loginWithSMS(AppManager.userId, pinValue, getDeviceID());
                        break;
                    case 3:
                        // Create user
                        UtilFunctions.showlLoadingDialog(this);
                        userManager.loginWithSMS(AppManager.userId, pinValue, getDeviceID());
                        break;
                }
            }
        } else {
            UtilFunctions.showlLoadingDialog(this);
            userManager.phoneVerify(AppManager.userId);
        }
    }

    @Override
    public void onSuccess(@Nullable User user) {
        UtilFunctions.dismissLoadingDialog();
        if (user != null) {
            UtilFunctions.setCurrentUser(this, user);
            if (user.profileIds.size() > 0) {
                getAllProfiles();
            } else {
                //Attempt to create new profile
            }
        } else {
            Intent intent = new Intent(this, NewPasswordActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onSuccess(ArrayList<Profile> profiles, boolean isLastPage, int page) {
        // Save profiles
        AppManager.profiles.clear();
        for (int i = 0; i < profiles.size(); i++) {
            AppManager.profiles.add(profiles.get(i));
        }
        UtilFunctions.dismissLoadingDialog();
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
