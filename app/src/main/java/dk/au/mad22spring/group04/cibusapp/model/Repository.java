package dk.au.mad22spring.group04.cibusapp.model;

import android.app.Application;

import java.util.ArrayList;

import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;

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

    public void addRecipesDefault() {
        ArrayList<RecipeDTO> recipeArray = new ArrayList<RecipeDTO>();
        recipeArray.add(new RecipeDTO("Lasagne",
                "",
                "",
                120,
                100,
                10,
                "Italy",
                4,
                "Very good lasagna",
                1543254,
                26352454,
                0.0));
    }
}
