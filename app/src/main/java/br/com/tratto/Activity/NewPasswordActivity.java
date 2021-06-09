package br.com.tratto.Activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import br.com.tratto.APIManager.UserManager;
import br.com.tratto.CustomView.FontEditText;
import br.com.tratto.Model.User;
import br.com.tratto.R;
import br.com.tratto.Utils.AppManager;
import br.com.tratto.Utils.UtilFunctions;

public class NewPasswordActivity extends AppCompatActivity implements View.OnClickListener, UserManager.UserCallback {

    private static String TAG = "New password Activity";

    private FontEditText edtNewPassword;
    private Button btnCreateNewpassword;

    private String tempPassword = "";
    private boolean isConfirm = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        edtNewPassword = (FontEditText) findViewById(R.id.edit_new_password);
        btnCreateNewpassword = (Button) findViewById(R.id.btn_create_new_password);

        btnCreateNewpassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!isConfirm) {
            tempPassword = edtNewPassword.getText().toString();
            if (tempPassword.isEmpty()) {
                UtilFunctions.showMessageDialog(this, "Error", "Enter new password");
            } else {
                edtNewPassword.setText("");
                btnCreateNewpassword.setBackgroundResource(R.color.green);
                isConfirm = true;
            }
        } else {
            if (edtNewPassword.getText().toString().isEmpty()) {
                UtilFunctions.showMessageDialog(this, "Error", "Enter confirm password");
            } else {
                attemptToCreatePassword(edtNewPassword.getText().toString());
            }
        }
    }

    private void attemptToCreatePassword(String confirmPass) {
        if (confirmPass.equals(tempPassword)) {
            Log.d(TAG, "Attempt to update password");
            UtilFunctions.showlLoadingDialog(this);
            UserManager userManager = new UserManager(this, this);
            userManager.updatePassword(confirmPass, AppManager.userId);
        } else {
            UtilFunctions.showMessageDialog(this, "Error", "Confirm password dosen't match.");
        }
    }

    @Override
    public void onBackPressed() {
        if (tempPassword.isEmpty()) {
            super.onBackPressed();
        } else {
            edtNewPassword.setText(tempPassword);
            tempPassword = "";
            isConfirm = false;
            btnCreateNewpassword.setBackgroundResource(R.color.button_blue);
        }
    }

    @Override
    public void onSuccess(@Nullable User user) {
        UtilFunctions.dismissLoadingDialog();
    }

    @Override
    public void onFailed(String error) {
        UtilFunctions.dismissLoadingDialog();
        UtilFunctions.showMessageDialog(this, "Error", error);
    }
}
