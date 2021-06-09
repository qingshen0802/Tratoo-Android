package br.com.tratto.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import br.com.tratto.CustomView.FontButton;
import br.com.tratto.R;
import br.com.tratto.Utils.UtilFunctions;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    private FontButton btnEmailSignUp;
    private View btnEmailLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnEmailLogin = findViewById(R.id.btn_email_login);
        btnEmailSignUp = findViewById(R.id.btn_email_signup);

        btnEmailSignUp.setOnClickListener(this);
        btnEmailLogin.setOnClickListener(this);

        if (UtilFunctions.getCurrentUser(this) != null) {
            if (UtilFunctions.getCurrentProfile(this) != null) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                //Attempt to create new profile
            }
        }

        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnEmailLogin) {
            Intent intent = new Intent(this, EmailLoginActivity.class);
            startActivity(intent);
        } else if (v == btnEmailSignUp) {
            Intent intent = new Intent(this, RegisterPersonActivity.class);
            startActivity(intent);
        } else {
        }
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA"};
            int permsRequestCode = 200;
            ActivityCompat.requestPermissions(this, perms, permsRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        finish();
                    }
                }
                break;
        }
    }
}
