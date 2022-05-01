package dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipeDetails;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentWithMeasurementsAndIngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.IngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionWithComponentsDTO;
import dk.au.mad22spring.group04.cibusapp.model.repository.Repository;

public class UserRecipeDetailsViewModel extends AndroidViewModel {
    Repository repoInstance;

    public RecipeWithSectionsAndInstructionsDTO recipeWithSectionsAndInstructionsDTO;
    public SectionWithComponentsDTO sectionWithComponentsDTO;

    public UserRecipeDetailsViewModel(@NonNull Application application) {
        super(application);
        repoInstance = Repository.getRepositoryInstance(application);
    }

    public LiveData<RecipeWithSectionsAndInstructionsDTO> getFullRecipeById(long recipeId){
        repoInstance.setFullRecipeByName(recipeId);
        return repoInstance.getFullRecipeFromDB();
    }

    public List<ComponentDTO> getSectionWithComponent(int sectionId){
        return repoInstance.getSectionWithComponent(sectionId);
    }

/*    public List<IngredientDTO> getIngredientFromComponentId(int componentId){
        return repoInstance.getIngredientsFromComponentId(componentId);
    }*/

    public void updateFullRecipe(RecipeDTO recipe){
        repoInstance.updateFullRecipe(recipe);
    }

    public void deleteFullRecipe(RecipeWithSectionsAndInstructionsDTO recipe) {
        repoInstance.deleteFullRecipe(recipe);
    }
}