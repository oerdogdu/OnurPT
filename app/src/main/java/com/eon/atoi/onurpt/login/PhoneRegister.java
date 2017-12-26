package com.eon.atoi.onurpt.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.eon.atoi.onurpt.activities.MainActivity;
import com.eon.atoi.onurpt.R;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Atoi on 1.12.2017.
 */

public class PhoneRegister extends Activity {

    private Button loginButton;
    private EditText etName, etMail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_register);

        etName = (EditText)findViewById(R.id.input_name);
        etMail = (EditText)findViewById(R.id.input_email);

        loginButton = (Button)findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMyLoggedInActivity();
                //onSMSLoginFlow(v);
            }
        });
    }

    public void onSMSLoginFlow(View view) {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.CODE); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0, 10));
                }

                String name = etName.getText().toString();
                String email = etMail.getText().toString();

                if(TextUtils.isEmpty(name))
                {
                    name = "default";
                }

                if(TextUtils.isEmpty(email))
                {
                    email = "default";
                }

                saveToDatabase(name, email);
                goToMyLoggedInActivity();
            }
        }
    }

    private void saveToDatabase(String name, String email) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference nameRef = database.getReference("name");
        nameRef.setValue(name);
        DatabaseReference emailRef = database.getReference("email");
        emailRef.setValue(email);
    }

    private void goToMyLoggedInActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
