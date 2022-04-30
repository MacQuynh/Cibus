package dk.au.mad22spring.group04.cibusapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dk.au.mad22spring.group04.cibusapp.MainActivity;
import dk.au.mad22spring.group04.cibusapp.R;
import dk.au.mad22spring.group04.cibusapp.ui.interfaces.SignupHandler;
import dk.au.mad22spring.group04.cibusapp.ui.viewModels.SignupViewModel;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private EditText nameEditText, emailEditText, passwordEditText;
    private Button backBtn, signupBtn;

    private SignupViewModel signupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);

        setupUIWidgets();
        setupOnClickListeners();
    }

    private void setupUIWidgets(){
        nameEditText = findViewById(R.id.signup_name_editText);
        emailEditText = findViewById(R.id.signup_email_editText);
        passwordEditText = findViewById(R.id.signup_password_editText);
        backBtn = findViewById(R.id.signup_back_btn);
        signupBtn = findViewById(R.id.signup_signup_btn);

    }

    private void setupOnClickListeners() {
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUserAccount();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goToLoginActivity();

            }
        });
    }

    private void createUserAccount() {
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (email.length() < 1 || password.length() < 6) {
            displayToast(getString(R.string.msg_email_or_password_is_invalid));
            return;
        }

        signupViewModel.createUserAccount(name, email, password, new SignupHandler() {
            @Override
            public void onSuccess(String email) {
                Log.d(TAG, "onSuccess: " + email);
                displayToast(getString(R.string.msg_you_are_now_signed_up));
                goToStartPage();
            }

            @Override
            public void onError() {
                displayToast(getString(R.string.msg_signup_failed));
            }
        });

    }

    private void goToStartPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToLoginActivity() {
        Intent intent =  new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
