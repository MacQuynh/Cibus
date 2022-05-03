package dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipesList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;
import dk.au.mad22spring.group04.cibusapp.model.repository.Repository;

public class UserRecipesListViewModel extends AndroidViewModel {
    Repository repoInstance;

    public LiveData<List<RecipeWithSectionsAndInstructionsDTO>> recipes;

    public UserRecipesListViewModel(@NonNull Application application) {
        super(application);
        repoInstance = Repository.getRepositoryInstance(application);
    }

    public LiveData<List<RecipeWithSectionsAndInstructionsDTO>> getUserRecipes(){
        repoInstance.addRecipesDefault();
        recipes = repoInstance.getAllUserRecipes();
        if(recipes == null){
            recipes = new MutableLiveData<List<RecipeWithSectionsAndInstructionsDTO>>();
        }
        return recipes;
    }

    public void searchRecipes(String searchText){
        repoInstance.searchAllUserRecipes(searchText);
    }

    public RecipeWithSectionsAndInstructionsDTO getRecipeByIndex(int index){
        return recipes.getValue().get(index);
    }
}