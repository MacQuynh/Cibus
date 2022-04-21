package dk.au.mad22spring.group04.cibusapp.model;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

import dk.au.mad22spring.group04.cibusapp.R;
import dk.au.mad22spring.group04.cibusapp.ui.interfaces.SignupHandler;
// Code for implementation af authentication adapted from  https://firebase.google.com/docs/auth/android/start?authuser=0&hl=en
public class FirebaseRepository {
    private static final String TAG = "FirebaseRepository";
    private static FirebaseRepository firebaseRepository;
    private final Context context;

    private final static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static synchronized FirebaseRepository getInstance(Application application){
        if (firebaseRepository == null){
            firebaseRepository = new FirebaseRepository(application);
        }
        return  firebaseRepository;
    }

    private FirebaseRepository(Application application){
        context = application.getApplicationContext();
    }

    public void createUserAccount(String email, String password, SignupHandler signupHandler){
        Log.d(TAG, "createUserAccount: " + email);
        if (email == null || email.length() < 5){
            displayToast(context.getString(R.string.msg_email_is_invalid));
            return;
        }
        else if (password == null || password.length() < 6){
            displayToast(context.getString(R.string.msg_password_is_invalid));
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((task)-> {
                    if (task.isSuccessful()){
                        Log.d(TAG, "createUser: success");
                        FirebaseUser user = mAuth.getCurrentUser();

                    } else{
                        Log.d(TAG, "onComplete: failure", task.getException());
                        displayToast(context.getString(R.string.msg_authentication_failed));
                    }
                });

    }

    private void displayToast(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
