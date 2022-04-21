package dk.au.mad22spring.group04.cibusapp.ui.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import dk.au.mad22spring.group04.cibusapp.model.FirebaseRepository;
import dk.au.mad22spring.group04.cibusapp.ui.interfaces.SignupHandler;

public class SignupViewModel extends AndroidViewModel {

    private final FirebaseRepository firebaseRepository;

    public SignupViewModel(@NonNull Application application) {
        super(application);
        firebaseRepository = FirebaseRepository.getInstance(application);
    }

    public void createUserAccount(String email, String password, SignupHandler signupHandler){
        firebaseRepository.createUserAccount(email, password, signupHandler);
    }
}
