package dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListApi;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.model.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.repository.Repository;

public class RecipeListApiViewModel extends AndroidViewModel {

    private LiveData<List<RecipeDTO>> recipeList;
    private Repository repository;

    public RecipeListApiViewModel(Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public void getInitialListFromAPI() {
        repository.getInitialListFromAPI();
    }
}