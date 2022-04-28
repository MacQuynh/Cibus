package dk.au.mad22spring.group04.cibusapp.model;

import android.app.Application;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.google.common.util.concurrent.ListenableFuture;

import dk.au.mad22spring.group04.cibusapp.database.RecipeDatabase;
import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.IngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.InstructionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.MeasurementDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionDTO;

public class Repository {

    private static Repository repoInstance;
    private RecipeDatabase db;
    private ExecutorService executer;
    private Application application;

    final MutableLiveData<List<RecipeWithSectionsAndInstructionsDTO>> recipesDB;

    public Repository(Application application) {
        db = RecipeDatabase.getDatabase(application.getApplicationContext());
        executer = Executors.newSingleThreadExecutor();
        recipesDB = new MutableLiveData<List<RecipeWithSectionsAndInstructionsDTO>>();
        this.application = application;
    }

    public static Repository getRepositoryInstance(Application application){
        if(repoInstance == null){
            repoInstance = new Repository(application);
        }
        return repoInstance;
    }

    public LiveData<List<RecipeWithSectionsAndInstructionsDTO>> getAllUserRecipes(){
        return recipesDB;
    }

    public void searchAllUserRecipes(String searchText){
        //inspiration for searching for part of word https://stackoverflow.com/questions/61948455/android-room-query-text-matches-exactly-the-search-string-or-start-with-search
        ListenableFuture<List<RecipeWithSectionsAndInstructionsDTO>> list = db.recipeDAO().getRecipesWithSectionsAndInstructionsFromSearch(searchText + "%");
        list.addListener(()->{
            try {
                recipesDB.postValue(list.get());

            } catch (Exception e) {
                Log.e(Constants.TAG_REPOSITORY, "searchAllUserRecipes: ", e);
            }
        }, ContextCompat.getMainExecutor(application.getApplicationContext()));

    }

    public void addRecipesDefault() {
        executer.execute(new Runnable() {
            @Override
            public void run() {
                RecipeDTO recipe1 = new RecipeDTO("Vegetar ret",
                        "",
                        "",
                        120,
                        100,
                        10,
                        "Italy",
                        4,
                        "Very good lasagna",
                        1543254,
                        26352454,
                        0.0,
                        ""
                );
                long idRecipe1 = db.recipeDAO().addRecipe(recipe1);

                InstructionDTO instruc1 = new InstructionDTO(idRecipe1, "Instruction text 1", 1111, 2222, 1);
                db.recipeDAO().addInstruction(instruc1);

                SectionDTO section1 = new SectionDTO(idRecipe1, "Section 1", 1);
                long idSection1 = db.recipeDAO().addSection(section1);

                ComponentDTO component1 = new ComponentDTO(idSection1, 1, "Component 1");
                long idComponent1 = db.recipeDAO().addComponent(component1);

                MeasurementDTO measure1 = new MeasurementDTO(idComponent1, "2");
                db.recipeDAO().addMeasurement(measure1);

                IngredientDTO ingre1 = new IngredientDTO(idComponent1, "Meat", "Meat","Meat");
                db.recipeDAO().addIngredient(ingre1);
            }
        });


        Log.d("TAG", "addRecipesDefault: ");
    }
}
