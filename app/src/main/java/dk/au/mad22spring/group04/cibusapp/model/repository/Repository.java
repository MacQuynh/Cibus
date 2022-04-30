package dk.au.mad22spring.group04.cibusapp.model.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.au.mad22spring.group04.cibusapp.API.RetrofitClient;
import dk.au.mad22spring.group04.cibusapp.database.RecipeDAO;
import dk.au.mad22spring.group04.cibusapp.database.RecipeDatabase;
import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.IngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.InstructionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.MeasurementDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionDTO;
import dk.au.mad22spring.group04.cibusapp.model.Recipes;
import dk.au.mad22spring.group04.cibusapp.model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private final String TAG = "Repository";

    // Code inspired by code demo from the lecture: "Room Demo"
//    private RecipeDatabase db;              //database
    private ExecutorService executor;       //for asynch processing
    private LiveData<List<RecipeDAO>> recipesLiveData; //livedata
    private static Repository instance;     //for Singleton pattern
    private RecipeDatabase db;
    private Application application;

    private MutableLiveData<List<Result>> recipeList;
    private MutableLiveData<RecipeDAO> recipeMutable;
    final MutableLiveData<List<RecipeWithSectionsAndInstructionsDTO>> recipesDB;
    final MutableLiveData<RecipeWithSectionsAndInstructionsDTO> recipeDB;

    private RetrofitClient retrofitClient;

    //Context - used to inform the user if the drink already exist
    private Context context;

    //Singleton pattern to make sure there is only one instance of the Repository in use
    public static Repository getRepositoryInstance(Application app) {
        if (instance == null) {
            instance = new Repository(app);
        }
        return instance;
    }

    //constructor - takes Application object for context
    private Repository(Application app) {
        this.context = app;
        db = RecipeDatabase.getDatabase(app.getApplicationContext());
        recipeList = new MutableLiveData<List<Result>>();
        recipeMutable = new MutableLiveData<>();
        executor = Executors.newSingleThreadExecutor();                //executor for background processing
        retrofitClient = new RetrofitClient();
        recipesDB = new MutableLiveData<List<RecipeWithSectionsAndInstructionsDTO>>();
        recipeDB = new MutableLiveData<RecipeWithSectionsAndInstructionsDTO>();
        this.application = app;
    }

    public void getInitialListFromAPI() {
        getInitialList();
    }

    public LiveData<List<Result>> getInitialListBack() {
        return recipeList;
    }

    public void getInitialList() {
        retrofitClient.getInstance().getJsonApi().getRandomRecipesFromTheLast30min().enqueue(new Callback<Recipes>() {
            @Override
            public void onResponse(@NonNull Call<Recipes> call, @NonNull Response<Recipes> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Result> list = new ArrayList<>();

                    for (Result r : response.body().getResults()) {
                        Result rm = new Result();
                        rm.setName(r.getName());
                        rm.setThumbnailUrl(r.getThumbnailUrl());
                        rm.setTotalTimeMinutes(r.getTotalTimeMinutes());
                        rm.setCookTimeMinutes(r.getCookTimeMinutes());
                        rm.setPrepTimeMinutes(r.getPrepTimeMinutes());
                        rm.setCountry(r.getCountry());
                        rm.setNumServings(r.getNumServings());
                        rm.setDescription(r.getDescription());
                        rm.setCreatedAt(r.getCreatedAt());
                        rm.setUpdatedAt(r.getUpdatedAt());
                        rm.setInstructions(r.getInstructions());
                        list.add(rm);
                    }
                    //recipeList.postValue(list);
                    Log.d(TAG, "onResponse: " + list);
                    Log.d(TAG, "  list.add(rm); " + list.get(0).getName() + list.get(0).getThumbnailUrl()
                            + list.get(0).getTotalTimeMinutes() + list.get(0).getCookTimeMinutes() + list.get(0).getPrepTimeMinutes() + list.get(0).getCountry()
                            + list.get(0).getDescription() + list.get(0).getNumServings() + list.get(0).getCreatedAt() + list.get(0).getUpdatedAt() + list.get(0).getInstructions()
                            + list.get(0).getUpdatedAt()
                    );
                }
            }

            @Override
            public void onFailure(@NonNull Call<Recipes> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    public LiveData<List<RecipeWithSectionsAndInstructionsDTO>> getAllUserRecipes(){
        return recipesDB;
    }

    public void searchAllUserRecipes(String searchText){
        //inspiration for searching for part of word https://stackoverflow.com/questions/61948455/android-room-query-text-matches-exactly-the-search-string-or-start-with-search
        ListenableFuture<List<RecipeWithSectionsAndInstructionsDTO>> list = db.recipeDAO().getRecipesWithSectionsAndInstructionsFromSearch(searchText + "%", Constants.USER_ID);
        list.addListener(()->{
            try {
                recipesDB.postValue(list.get());

            } catch (Exception e) {
                Log.e(Constants.TAG_REPOSITORY, "searchAllUserRecipes: ", e);
            }
        }, ContextCompat.getMainExecutor(application.getApplicationContext()));

    }

    public void setFullRecipeByName(String name){
        ListenableFuture<RecipeWithSectionsAndInstructionsDTO> recipe = db.recipeDAO().getFullRecipeByName(name);

        recipe.addListener(() -> {
            try {
                recipeDB.postValue(recipe.get());
            } catch (Exception e){
                Log.e(TAG, "Error loading Recipe: ", e);
            }

        }, ContextCompat.getMainExecutor(application.getApplicationContext()));
    }

    public LiveData<RecipeWithSectionsAndInstructionsDTO> getFullRecipeFromDB(){
        return recipeDB;
    }

    public void addRecipesDefault() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                RecipeDTO recipe1 = new RecipeDTO("Risotto",
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
                        Constants.USER_ID
                );
                long idRecipe1 = db.recipeDAO().addRecipe(recipe1);

                InstructionDTO instruc1 = new InstructionDTO("Instruction text 1", 1111, 2222, 1);
                instruc1.recipeCreatorId = idRecipe1;
                db.recipeDAO().addInstruction(instruc1);

                SectionDTO section1 = new SectionDTO("Section 1", 1);
                section1.recipeCreatorIdForSection = idRecipe1;
                long idSection1 = db.recipeDAO().addSection(section1);

                ComponentDTO component1 = new ComponentDTO(1, "Component 1");
                component1.sectionCreatorId = idSection1;
                long idComponent1 = db.recipeDAO().addComponent(component1);

                MeasurementDTO measure1 = new MeasurementDTO("2");
                measure1.componentCreatorId = idComponent1;
                db.recipeDAO().addMeasurement(measure1);

                IngredientDTO ingre1 = new IngredientDTO("Meat", "Meat","Meat");
                ingre1.componentCreatorIdForIngredient = idComponent1;
                db.recipeDAO().addIngredient(ingre1);
            }
        });


        Log.d("TAG", "addRecipesDefault: ");
    }

    public void addNewRecipeToDb(RecipeDTO recipeDTO, InstructionDTO instructionDTO, SectionDTO sectionDTO, ComponentDTO componentDTO, ArrayList<MeasurementDTO> listOfMeasurementDTO, ArrayList<IngredientDTO> listOfIngredientDTO ){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                long idRecipe = db.recipeDAO().addRecipe(recipeDTO);

                instructionDTO.recipeCreatorId = idRecipe;
                db.recipeDAO().addInstruction(instructionDTO);

                sectionDTO.recipeCreatorIdForSection = idRecipe;
                long idSection = db.recipeDAO().addSection(sectionDTO);

                componentDTO.sectionCreatorId = idSection;
                long idComponent = db.recipeDAO().addComponent(componentDTO);

                for (MeasurementDTO measurementDTO : listOfMeasurementDTO){
                    measurementDTO.componentCreatorId = idComponent;
                    db.recipeDAO().addMeasurement(measurementDTO);
                }

                for (IngredientDTO ingredientDTO : listOfIngredientDTO){
                    ingredientDTO.componentCreatorIdForIngredient = idComponent;
                    db.recipeDAO().addIngredient(ingredientDTO);

                }


            }
        });

    }
}
