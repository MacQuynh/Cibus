package dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipeDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentWithMeasurementsAndIngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;
import dk.au.mad22spring.group04.cibusapp.model.repository.Repository;

public class UserRecipeDetailsViewModel extends AndroidViewModel {
    Repository repoInstance;

    public RecipeWithSectionsAndInstructionsDTO recipeWithSectionsAndInstructionsDTO;
    final MutableLiveData<String> ingredientMeasurementText;

    public UserRecipeDetailsViewModel(@NonNull Application application) {
        super(application);
        repoInstance = Repository.getRepositoryInstance(application);
        ingredientMeasurementText = new MutableLiveData<String>("");
    }

    public LiveData<RecipeWithSectionsAndInstructionsDTO> getFullRecipeById(Integer id) {
        if (id != null) {
            repoInstance.setFullRecipeFromDB(id);
        } else {
            repoInstance.setFirstRecipeFromDB();
        }

        return repoInstance.getRecipeFromDB();
    }

    public LiveData<RecipeWithSectionsAndInstructionsDTO> getRecipeFromDB() {
        repoInstance.setFirstRecipeFromDB();
        return repoInstance.getRecipeFromDB();
    }

    /*    public LiveData<RecipeWithSectionsAndInstructionsDTO> getFullRecipeById(long id){
        repoInstance.getFullRecipeFromDBById(id);
        return repoInstance.getRecipeFromDB();
    }*/

 /*   public String getIngredientText(){
        return repoInstance.getSectionWithComponentDB();
    }*/

    public LiveData<List<ComponentWithMeasurementsAndIngredientDTO>> getComponent() {
        return repoInstance.getSectionWithComponentDB();
    }

    public void setComponent() {
        repoInstance.setSectionWithComponentDB();
    }

    public void updateFullRecipe(RecipeDTO recipe) {
        repoInstance.updateFullRecipe(recipe);
        repoInstance.updateDBRecipes();
    }

    public void deleteFullRecipe(RecipeWithSectionsAndInstructionsDTO recipe) {
        repoInstance.deleteFullRecipe(recipe);
        repoInstance.updateDBRecipes();
    }
}