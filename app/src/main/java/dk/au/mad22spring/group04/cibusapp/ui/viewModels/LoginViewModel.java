package dk.au.mad22spring.group04.cibusapp.ui.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import dk.au.mad22spring.group04.cibusapp.model.repository.FirebaseRepository;
import dk.au.mad22spring.group04.cibusapp.ui.interfaces.LoginHandler;

public class LoginViewModel extends AndroidViewModel {

    private final FirebaseRepository firebaseRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        firebaseRepository = FirebaseRepository.getInstance(application);
    }

    public void login(String email, String password, final LoginHandler loginHandler){
        firebaseRepository.loginUser(email, password,loginHandler);

    }

}
