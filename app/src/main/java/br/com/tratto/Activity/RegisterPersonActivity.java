package br.com.tratto.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import br.com.tratto.APIManager.UserManager;
import br.com.tratto.CustomView.FontButton;
import br.com.tratto.CustomView.FontCheckBox;
import br.com.tratto.CustomView.FontEditText;
import br.com.tratto.CustomView.FontEditTextMasked;
import br.com.tratto.Model.NewUser;
import br.com.tratto.Model.Request.RequestNewUser;
import br.com.tratto.Model.User;
import br.com.tratto.R;
import br.com.tratto.Utils.AppManager;
import br.com.tratto.Utils.Constants;
import br.com.tratto.Utils.UtilFunctions;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterPersonActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, UserManager.UserCallback {

    private static String TAG = "RegisterPerson Activity";

    private CircleImageView imgProfile;
    private FontEditText edtFullName, edtEmail, edtPassword, edtUsername, edtPhoneNumber;
    private FontEditTextMasked edtCPF;
    private FontCheckBox chkUnder16, chkTerms;
    private FontButton btnContinue, btnTerms;

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_person);

        imgProfile = findViewById(R.id.image_register_personal_profile);
        edtFullName = findViewById(R.id.text_person_full_name);
        edtEmail = findViewById(R.id.text_person_email);
        edtCPF = findViewById(R.id.text_person_cpf);
        edtPassword = findViewById(R.id.text_person_password);
        chkUnder16 = findViewById(R.id.check_under_16);
        chkTerms = findViewById(R.id.check_license);
        btnContinue = findViewById(R.id.btn_create_person_profile);
        btnTerms = findViewById(R.id.btn_person_terms);
        edtUsername = findViewById(R.id.text_person_user_name);
        edtPhoneNumber = findViewById(R.id.text_person_phone_number);

        btnTerms.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        chkUnder16.setOnCheckedChangeListener(this);

        userManager = new UserManager(this, this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnTerms) {
            Intent intent = new Intent(this, TermsActivity.class);
            startActivity(intent);
        } else if (v == imgProfile) {
            //Open Camera
        }else {
            if (!edtEmail.getText().toString().isEmpty()
                    && !edtPassword.getText().toString().isEmpty()
                    && !edtCPF.getText().toString().isEmpty()
                    && !edtFullName.getText().toString().isEmpty()
                    && chkTerms.isChecked()) {
                attemptCreateProfile();
            } else {
                UtilFunctions.showMessageDialog(this, "Error", "Fill all fields.");
            }
        }
    }

    private void attemptCreateProfile() {
        Log.d(TAG, "Create profile");

        String password = edtPassword.getText().toString();
        if (password.length() < 6 || password.length() > 32) {
            UtilFunctions.showMessageDialog(this, "Error", "Password length must be from 6 to 32 characters, at least one uppercase, at least one lowercase, at least one digit.");
            return;
        } else if (!UtilFunctions.isPasswordCorrect(password)) {
            UtilFunctions.showMessageDialog(this, "Error", "Password must contain at least one uppercase, at least one lowercase, at least one digit.");
            return;
        }

        RequestNewUser newUser = new RequestNewUser();
        NewUser user = new NewUser();
        user.email = edtEmail.getText().toString();
        user.userName = edtUsername.getText().toString();
        user.fullName = edtFullName.getText().toString();
        user.phoneNumber = edtPhoneNumber.getText().toString();
        user.password = password;
        user.passwordConfirmation = password;
        user.deviceNumber = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
        user.documentIdNumber = edtCPF.getText().toString().trim();
        user.under16 = chkUnder16.isChecked();
        user.guarantorEmail = AppManager.gUserEmail;
        user.guarantorFullName = AppManager.gUserName;
        user.guarantorCPF = AppManager.gUserCPF;
        UtilFunctions.showlLoadingDialog(this);
        newUser.user = user;
        userManager.createUser(newUser);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == chkUnder16 && isChecked) {
            Intent intent = new Intent(this, ResponsibleUserActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onSuccess(@Nullable User user) {
        UtilFunctions.dismissLoadingDialog();
        Intent intent = new Intent(this, ConfirmSMSActivity.class);
        intent.putExtra("parent", Constants.PARENT_IS_CREATE_USER);
        startActivity(intent);
    }

    @Override
    public void onFailed(String error) {
        UtilFunctions.dismissLoadingDialog();
        UtilFunctions.showMessageDialog(this, "Error", error);
    }
}
