package dk.au.mad22spring.group04.cibusapp.ui.interfaces;

public interface SignupHandler {
    void onSuccess(String name, String email);
    void onError();
}
