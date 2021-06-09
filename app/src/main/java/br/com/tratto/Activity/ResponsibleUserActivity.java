package br.com.tratto.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.apache.commons.lang3.text.WordUtils;

import br.com.tratto.CustomView.FontButton;
import br.com.tratto.CustomView.FontEditText;
import br.com.tratto.R;
import br.com.tratto.Utils.AppManager;
import br.com.tratto.Utils.UtilFunctions;

public class ResponsibleUserActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "ResponsibleUser";

    private FontEditText edtUsername, edtCPF, edtEmail;
    private FontButton btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsible_user);

        edtCPF = findViewById(R.id.text_responsible_user_cpf);
        edtUsername = findViewById(R.id.text_responsible_user_full_name);
        edtEmail = findViewById(R.id.text_responsible_user_email);
        btnContinue = findViewById(R.id.btn_create_responsible_user_profile);

        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!edtCPF.getText().toString().isEmpty() && !edtUsername.getText().toString().isEmpty() && !edtEmail.getText().toString().isEmpty()) {
            attemptCreate();
        } else {
            UtilFunctions.showMessageDialog(this, "Error", "Fill all fields.");
        }
    }

    private void attemptCreate() {
        AppManager.gUserCPF = edtCPF.getText().toString();
        AppManager.gUserName = WordUtils.capitalizeFully(edtUsername.getText().toString());
        AppManager.gUserEmail = edtEmail.getText().toString();
        finish();
    }
}
