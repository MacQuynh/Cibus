package dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipeDetails;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.common.util.concurrent.ListenableFuture;

import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentWithMeasurementsAndIngredientDTO;
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

    public LiveData<RecipeWithSectionsAndInstructionsDTO> getFullRecipeByName(String name){
        repoInstance.setFullRecipeByName(name);
        return repoInstance.getFullRecipeFromDB();
    }

    public SectionWithComponentsDTO getSectionWithComponent(int sectionId){
        return repoInstance.setSectionWithComponent(sectionId);
      /*  section.addListener(()->{
            try {
                sectionWithComponentsDTO = section.get();
            } catch (Exception e){
                Log.e("TAG", "getSectionWithComponent: ", e);
            }
        }, ContextCompat.getMainExecutor(getApplication()));*/
    }

    public void updateFullRecipe(RecipeDTO recipe){
        repoInstance.updateFullRecipe(recipe);
    }
}