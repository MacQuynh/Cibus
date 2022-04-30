package dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipeDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;
import dk.au.mad22spring.group04.cibusapp.model.repository.Repository;

public class UserRecipeDetailsViewModel extends AndroidViewModel {
    Repository repoInstance;

    public RecipeWithSectionsAndInstructionsDTO recipeWithSectionsAndInstructionsDTO;

    public UserRecipeDetailsViewModel(@NonNull Application application) {
        super(application);
        repoInstance = Repository.getRepositoryInstance(application);
    }

    public LiveData<RecipeWithSectionsAndInstructionsDTO> getFullRecipeByName(String name){
        repoInstance.setFullRecipeByName(name);
        return repoInstance.getFullRecipeFromDB();
    }
}