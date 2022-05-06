package dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListAPI;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dk.au.mad22spring.group04.cibusapp.model.Recipes;
import dk.au.mad22spring.group04.cibusapp.model.repository.Repository;

public class RecipeListApiViewModel extends AndroidViewModel {

    private Repository repository;

    public RecipeListApiViewModel(Application application) {
        super(application);
        repository = Repository.getRepositoryInstance(application);
    }

    public void getInitialList() {
        repository.getInitialListFromAPI();
    }

    public LiveData<Recipes> getInitialListBack() {
        return repository.getInitialListBack();
    }

    public void searchRecipes(String search_text) {
        repository.searchRecipes(search_text);
    }
}