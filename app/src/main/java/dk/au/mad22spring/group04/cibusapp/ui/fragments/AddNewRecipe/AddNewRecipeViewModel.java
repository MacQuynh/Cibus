package dk.au.mad22spring.group04.cibusapp.ui.fragments.AddNewRecipe;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import dk.au.mad22spring.group04.cibusapp.model.Repository;

public class AddNewRecipeViewModel extends AndroidViewModel {
    private final Repository repository;

    public AddNewRecipeViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepositoryInstance(application);
    }

    public void addNewRecipe(String recipeName, String measure1, String measure2, String measure3, String measure4, String measure5, String ingredient1, String ingredient2, String ingredient3, String ingredient4, String ingredient5, String instructions  ){

    }
}