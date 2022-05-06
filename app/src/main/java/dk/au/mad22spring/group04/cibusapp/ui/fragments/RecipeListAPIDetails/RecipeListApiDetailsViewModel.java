package dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListAPIDetails;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.model.Component;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.InstructionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.Instruction;
import dk.au.mad22spring.group04.cibusapp.model.Result;
import dk.au.mad22spring.group04.cibusapp.model.Section;
import dk.au.mad22spring.group04.cibusapp.model.repository.Repository;


public class RecipeListApiDetailsViewModel extends AndroidViewModel {
    private Repository repository;

    public RecipeListApiDetailsViewModel(Application application) {
        super(application);
        repository = Repository.getRepositoryInstance(application);
    }

    public Result getRecipeByIndex(int index){
        return repository.getRecipeFromApiByIndex(index);
    }

    public void addRecipeFromAPItoDB(Result recipeToAddDB) {
        repository.addRecipeFromAPItoDB(recipeToAddDB);
    }
}