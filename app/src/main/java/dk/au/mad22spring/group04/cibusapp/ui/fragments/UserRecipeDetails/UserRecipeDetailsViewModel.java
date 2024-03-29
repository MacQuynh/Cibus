package dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipeDetails;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentWithMeasurementsAndIngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.IngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionWithComponentsDTO;
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

    public RecipeWithSectionsAndInstructionsDTO getFullRecipeById(int id){
        return repoInstance.getFullRecipeFromDB(id);
    }

    public RecipeWithSectionsAndInstructionsDTO getFirstRecipeFromDB() {
        return repoInstance.getFirstRecipeFromDB();
    }

    public LiveData<List<ComponentWithMeasurementsAndIngredientDTO>> getComponent(){
        return repoInstance.getSectionWithComponentDB();
    }
    public void setComponent(int recipeId){
        repoInstance.setSectionWithComponentDB(recipeId);
    }

    public void updateFullRecipe(RecipeDTO recipe){
        repoInstance.updateFullRecipe(recipe);
        repoInstance.updateDBRecipes();
    }

    public void deleteFullRecipe(RecipeWithSectionsAndInstructionsDTO recipe) {
        repoInstance.deleteFullRecipe(recipe);
        repoInstance.updateDBRecipes();
    }
}