package dk.au.mad22spring.group04.cibusapp.model.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import dk.au.mad22spring.group04.cibusapp.R;
import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.ui.interfaces.LoginHandler;
import dk.au.mad22spring.group04.cibusapp.ui.interfaces.SignupHandler;

public class FirebaseRepository {
    private static final String TAG = "FirebaseRepository";
    private static FirebaseRepository firebaseRepository;
    private final Context context;

    private final static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static synchronized FirebaseRepository getInstance(Application application){
        if (firebaseRepository == null){
            FirebaseApp.initializeApp(application);
            firebaseRepository = new FirebaseRepository(application);
        }
        return  firebaseRepository;
    }

    private FirebaseRepository(Application application){
        context = application.getApplicationContext();
    }

    /*
     * Code for creating user account and login of user adapted from
     * https://firebase.google.com/docs/auth/android/start?authuser=0&hl=en
     */

    public void createUserAccount(String name, String email, String password, SignupHandler signupHandler){

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
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null){
                            String uid = user.getUid();
                            Constants.USER_ID = uid;
                        }
                        Log.d(TAG, "createUser: success");
                        signupHandler.onSuccess(email);

                    } else{
                        signupHandler.onError();
                        Log.d(TAG, "onComplete: failure", task.getException());
                        // Code adapted from https://stackoverflow.com/questions/37859582/how-to-catch-a-firebase-auth-specific-exceptions
                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                        if (errorCode == "ERROR_EMAIL_ALREADY_IN_USE"){
                               displayToast(context.getString(R.string.msg_email_is_already_in_use));
                        }
                    }
                });
    }

    public  void loginUser(String email, String password, final LoginHandler loginHandler){
        if (email == null || email.length() < 5){
            displayToast(context.getString(R.string.msg_email_is_invalid));
            return;
        }
        if (password == null || password.length() < 6){
            displayToast(context.getString(R.string.msg_password_is_invalid));
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null){
                            String uid = user.getUid();
                            Constants.USER_ID = uid;
                        }
                        loginHandler.onSuccess();
                        displayToast(context.getString(R.string.msg_login_was_successful));
                    } else{
                        loginHandler.onError();
                        displayToast(context.getString(R.string.msg_login_failed));
                        Log.d(TAG, "loginUser: ", task.getException());
                    }
                });
    }

    private void displayToast(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
