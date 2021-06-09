package br.com.tratto.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.tratto.APIManager.UserManager;
import br.com.tratto.CustomView.FontButton;
import br.com.tratto.CustomView.FontEditText;
import br.com.tratto.Model.User;
import br.com.tratto.R;
import br.com.tratto.Utils.AppManager;
import br.com.tratto.Utils.Constants;
import br.com.tratto.Utils.UtilFunctions;

public class PhoneVerificationActivity extends AppCompatActivity implements View.OnClickListener, UserManager.UserCallback {

    private static String TAG = "Phone Verify Activity";
    private FontEditText edtPhoneNumber;
    private FontButton btnContinue;

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        edtPhoneNumber = findViewById(R.id.text_verify_phone_number);
        btnContinue = findViewById(R.id.btn_verify_continue);

        btnContinue.setOnClickListener(this);

//        Intent intent = new Intent(this, ConfirmSMSActivity.class);
//        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (edtPhoneNumber.getText().toString().isEmpty()) {
            UtilFunctions.showMessageDialog(this, "Error", "Phone number field is blank.");
        } else {
            UtilFunctions.showlLoadingDialog(this);
            userManager = new UserManager(this, this);
            userManager.phoneVerify(AppManager.userId);
        }
    }

    @Override
    public void onSuccess(@Nullable User user) {
        UtilFunctions.dismissLoadingDialog();
        Intent intent = new Intent(this, ConfirmSMSActivity.class);
        intent.putExtra("parent", Constants.PARENT_IS_PHONEVERIFY);
        startActivity(intent);
    }

    @Override
    public void onFailed(String error) {
        UtilFunctions.dismissLoadingDialog();
        UtilFunctions.showMessageDialog(this, "Error", error);
    }
}
