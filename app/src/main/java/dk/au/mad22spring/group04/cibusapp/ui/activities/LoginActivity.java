package dk.au.mad22spring.group04.cibusapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dk.au.mad22spring.group04.cibusapp.MainActivity;
import dk.au.mad22spring.group04.cibusapp.R;
import dk.au.mad22spring.group04.cibusapp.ui.interfaces.LoginHandler;
import dk.au.mad22spring.group04.cibusapp.ui.viewModels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginBtn, signupBtn;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        setupUIWidgets();
        setupOnClickListeners();
    }

    private void setupUIWidgets() {
        emailEditText = findViewById(R.id.login_email_editText);
        passwordEditText = findViewById(R.id.login_password_editText);
        loginBtn = findViewById(R.id.login_login_btn);
        signupBtn = findViewById(R.id.login_signup_btn);
    }

    private void setupOnClickListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotToSignupActivity();
            }
        });
    }

    private void login(){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        loginViewModel.login(email, password, new LoginHandler() {
            @Override
            public void onSuccess() {
                goToStartPage();
            }

            @Override
            public void onError() {
                displayToast("Failed to login");
                passwordEditText.setText("");
            }
        });

    }

    private void gotToSignupActivity() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
        finish();

    }

    private void goToStartPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}