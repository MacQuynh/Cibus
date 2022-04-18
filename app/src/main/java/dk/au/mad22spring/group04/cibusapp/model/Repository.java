package dk.au.mad22spring.group04.cibusapp.model;

import android.app.Application;

public class Repository {

    private static Repository repoInstance;

    public Repository(Application application) {

    }

    public static Repository getRepositoryInstance(Application application){
        if(repoInstance == null){
            repoInstance = new Repository(application);
        }
        return repoInstance;
    }
}
