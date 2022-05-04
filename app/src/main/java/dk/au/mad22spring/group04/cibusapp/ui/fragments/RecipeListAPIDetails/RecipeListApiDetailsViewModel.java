package dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListAPIDetails;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.database.RecipeDAO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.repository.Repository;


public class RecipeListApiDetailsViewModel extends AndroidViewModel {
    private LiveData<List<RecipeDTO>> recipeList;
    private Repository repository;

    public RecipeListApiDetailsViewModel(Application application) {
        super(application);
        repository = Repository.getRepositoryInstance(application);
    }

    public LiveData<RecipeDTO> getRecipeByName(String name) {
        //TODO: get by Id eller index i stedet
        repository.getDrinkByName(name);
        return repository.getRecipe();
    }


}