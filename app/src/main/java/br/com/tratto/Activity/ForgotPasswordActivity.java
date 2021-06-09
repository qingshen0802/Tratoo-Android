package br.com.tratto.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.hbb20.CountryCodePicker;

import br.com.tratto.APIManager.UserManager;
import br.com.tratto.CustomView.FontButton;
import br.com.tratto.CustomView.FontEditText;
import br.com.tratto.Model.User;
import br.com.tratto.R;
import br.com.tratto.Utils.Constants;
import br.com.tratto.Utils.UtilFunctions;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener, UserManager.UserCallback {

    private static String TAG = "ForgotPassword Activity";
    private FontButton btnConfirm;
    private FontEditText edtPhoneNumber;
    private CountryCodePicker ccp;
    private TextInputLayout phoneLayout;

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btnConfirm = findViewById(R.id.btn_forgot_password_continue);
        edtPhoneNumber = findViewById(R.id.text_forgot_password_phone_number);
        ccp = findViewById(R.id.ccp);
        phoneLayout = findViewById(R.id.layout_full_name);

        btnConfirm.setOnClickListener(this);
        edtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                phoneLayout.setError(null);
                phoneLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        String phone = edtPhoneNumber.getText().toString().trim();
        if (phone.isEmpty()) {
            phoneLayout.setError("Preencha seu número de celular");
            phoneLayout.setErrorEnabled(true);
        } else {
            attemptPassword();
        }
    }

    private void attemptPassword() {
        UtilFunctions.showlLoadingDialog(this);
        userManager = new UserManager(this, this);
        userManager.requestForgotPassword(ccp.getSelectedCountryCodeWithPlus() + edtPhoneNumber.getText().toString());
    }

    @Override
    public void onSuccess(@Nullable User user) {
        UtilFunctions.dismissLoadingDialog();
        Intent intent = new Intent(this, ConfirmSMSActivity.class);
        intent.putExtra("parent", Constants.PARENT_IS_FORGOT);
        startActivity(intent);
    }

    @Override
    public void onFailed(String error) {
        UtilFunctions.dismissLoadingDialog();
        UtilFunctions.showMessageDialog(this, "Error", error);
    }
}
