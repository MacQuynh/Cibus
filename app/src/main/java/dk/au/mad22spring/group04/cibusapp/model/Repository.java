package dk.au.mad22spring.group04.cibusapp.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad22spring.group04.cibusapp.database.RecipeDatabase;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;

public class Repository {

    private static Repository repoInstance;
    private RecipeDatabase db;

    public Repository(Application application) {

    }

    public static Repository getRepositoryInstance(Application application){
        if(repoInstance == null){
            repoInstance = new Repository(application);
        }
        return repoInstance;
    }

    public LiveData<List<RecipeWithSectionsAndInstructionsDTO>> getAllUserRecipes(){
        return db.recipeDAO().getRecipeWithSectionsAndInstructions();
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
