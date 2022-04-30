package dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListAPI;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.Result;
import dk.au.mad22spring.group04.cibusapp.model.repository.Repository;

public class RecipeListApiViewModel extends AndroidViewModel {

    private LiveData<List<RecipeDTO>> recipeList;
    private Repository repository;

    public RecipeListApiViewModel(Application application) {
        super(application);
        repository = Repository.getRepositoryInstance(application);
    }

    public void getInitialList() {
        repository.getInitialListFromAPI();
    }

    public LiveData<List<Result>> getInitialListBack() {
        return repository.getInitialListBack();
    }

    public void searchRecipes(String search_text) {
        repository.searchDrinks(search_text);
    }
}