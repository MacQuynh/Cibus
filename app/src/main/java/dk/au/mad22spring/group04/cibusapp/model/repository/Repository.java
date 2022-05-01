package dk.au.mad22spring.group04.cibusapp.model.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.common.util.concurrent.ListenableFuture;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import dk.au.mad22spring.group04.cibusapp.API.RetrofitClient;
import dk.au.mad22spring.group04.cibusapp.database.RecipeDAO;
import dk.au.mad22spring.group04.cibusapp.database.RecipeDatabase;
import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentWithMeasurementsAndIngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.IngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.InstructionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.MeasurementDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionWithComponentsDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.UnitDTO;
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
    private MutableLiveData<RecipeDTO> recipeMutable;
    final MutableLiveData<List<RecipeWithSectionsAndInstructionsDTO>> recipesDB;
    final MutableLiveData<RecipeWithSectionsAndInstructionsDTO> recipeDB;
    final MutableLiveData<SectionWithComponentsDTO> sectionWithComponentDB;
    final MutableLiveData<String> ingredientMeasurementText;


    private RecipeDTO finalRecipeFromAPI; //TODO Refactor

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
        sectionWithComponentDB = new MutableLiveData<SectionWithComponentsDTO>();
        ingredientMeasurementText = new MutableLiveData<String>("");
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

                    List<Result> list = getResults(response);
                    recipeList.postValue(list);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Recipes> call, @NonNull Throwable t) {
                Log.d(Constants.TAG_REPOSITORY, "onFailure: " + t);
            }
        });
    }

    public LiveData<List<RecipeWithSectionsAndInstructionsDTO>> getAllUserRecipes() {
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

    public void setFullRecipeByName(long recipeId){
        ListenableFuture<RecipeWithSectionsAndInstructionsDTO> recipe = db.recipeDAO().getFullRecipeById(recipeId);

        recipe.addListener(() -> {
            try {
                recipeDB.postValue(recipe.get());
            } catch (Exception e){
                Log.e(Constants.TAG_REPOSITORY, "Error loading Recipe: ", e);
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
                RecipeDTO recipe1 = new RecipeDTO(null,
                        "Tærte4",
                        "",
                        120,
                        100,
                        10,
                        "Italy",
                        4,
                        "Let’s make the viral TikTok green goddess salad, but purple! With additions like purple kale and purple cabbage, this salad has all of the elements to make a delicious and colorful meal!",
                        1543254,
                        26352454,
                        0,
                        Constants.USER_ID
                );
                long idRecipe1 = db.recipeDAO().addRecipe(recipe1);

                InstructionDTO instruc1 = new InstructionDTO("Instruction text 1", 1111, 2222, 1);
                instruc1.recipeCreatorId = idRecipe1;
                db.recipeDAO().addInstruction(instruc1);
                InstructionDTO instruc2 = new InstructionDTO("Instruction text 2", 1111, 2222, 2);
                instruc2.recipeCreatorId = idRecipe1;
                db.recipeDAO().addInstruction(instruc2);

                SectionDTO section1 = new SectionDTO("Section 1", 1);
                section1.recipeCreatorIdForSection = idRecipe1;
                long idSection1 = db.recipeDAO().addSection(section1);

                ComponentDTO component1 = new ComponentDTO(1, "Component 1");
                component1.sectionCreatorId = idSection1;
                long idComponent1 = db.recipeDAO().addComponent(component1);

                MeasurementDTO measure1 = new MeasurementDTO("2");
                measure1.componentCreatorId = idComponent1;
                long idMeasurement1 = db.recipeDAO().addMeasurement(measure1);

                UnitDTO unit1 = new UnitDTO("g", "grams", "gram");
                unit1.measurementCreatorId = idMeasurement1;
                db.recipeDAO().addUnit(unit1);

                IngredientDTO ingre1 = new IngredientDTO("Meat", "Meat","Meat");
                ingre1.componentCreatorIdForIngredient = idComponent1;
                db.recipeDAO().addIngredient(ingre1);

                ComponentDTO component2 = new ComponentDTO(1, "Component 1");
                component2.sectionCreatorId = idSection1;
                long idComponent2 = db.recipeDAO().addComponent(component2);

                MeasurementDTO measure2 = new MeasurementDTO("5");
                measure2.componentCreatorId = idComponent2;
                long idMeasurement2 = db.recipeDAO().addMeasurement(measure2);

                UnitDTO unit2 = new UnitDTO("ml", "grams", "gram");
                unit2.measurementCreatorId = idMeasurement2;
                db.recipeDAO().addUnit(unit2);

                IngredientDTO ingre2 = new IngredientDTO("Milk", "Milk","Milk");
                ingre2.componentCreatorIdForIngredient = idComponent2;
                db.recipeDAO().addIngredient(ingre2);
            }
        });


        Log.d(Constants.TAG_REPOSITORY, "addRecipesDefault: ");
    }

    public List<ComponentDTO> getSectionWithComponent(int sectionId){
        List<ComponentDTO> list = new ArrayList<>();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                list.addAll(db.recipeDAO().getComponentsFromSectionId(sectionId));
            }
        });
        return list;
    }

    public void updateFullRecipe(RecipeDTO recipe){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.recipeDAO().updateRecipe(recipe);
            }
        });
    }

    public void searchDrinks(String search_text) {
        searchRecipesFromString(search_text);
    }

    public LiveData<List<Result>> searchDrinks() {
        return recipeList;
    }

    private void searchRecipesFromString(String search_text) {
        RetrofitClient.getInstance().getJsonApi().getRecipeFromSearchString(search_text).enqueue(new Callback<Recipes>() {
            @Override
            public void onResponse(@NonNull Call<Recipes> call, @NonNull Response<Recipes> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Result> list = getResults(response);
                    recipeList.postValue(list);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Recipes> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t);
                Toast.makeText(context, "Something went wrong while searching", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    private List<Result> getResults(@NonNull Response<Recipes> response) {
        List<Result> list = new ArrayList<>();
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void getDrinkByName(String name) {
        RetrofitClient.getInstance().getJsonApi().getRecipeFromSearchString(name).enqueue(new Callback<Recipes>() {
            @Override
            public void onResponse(Call<Recipes> call, Response<Recipes> response) {
                if (response.isSuccessful() && response.body() != null) {

                    RecipeDTO recipeDTO = null;
                    if (recipeMutable != null) {
                        recipeDTO = new RecipeDTO(response.body().getResults().get(0).getId(),
                                response.body().getResults().get(0).getName(),
                                response.body().getResults().get(0).getThumbnailUrl(),
                                10.00f, 10.00f, 10.00f,
                               /* response.body().getResults().get(0).getTotalTimeMinutes(),
                                response.body().getResults().get(0).getCookTimeMinutes(),
                                response.body().getResults().get(0).getPrepTimeMinutes(),*/
                                response.body().getResults().get(0).getCountry(),
                                response.body().getResults().get(0).getNumServings(),
                                response.body().getResults().get(0).getDescription(),
                                response.body().getResults().get(0).getCreatedAt(),
                                response.body().getResults().get(0).getUpdatedAt(),
                                response.body().getResults().get(0).getUserRatings().getCountPositive().floatValue(),
                                response.body().getResults().get(0).getId().toString()
                        );
                    }
                    recipeMutable.postValue(recipeDTO);
                }
            }

            @Override
            public void onFailure(Call<Recipes> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    public LiveData<RecipeDTO> getRecipe() {
        return recipeMutable;
    }

    public void addNewRecipeToDb(RecipeDTO recipeDTO, InstructionDTO instructionDTO, SectionDTO sectionDTO, ArrayList<MeasurementDTO> listOfMeasurementDTO, ArrayList<UnitDTO> listOfUnitsDTO, ArrayList<IngredientDTO> listOfIngredientDTO ){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                long idRecipe = db.recipeDAO().addRecipe(recipeDTO);

                instructionDTO.recipeCreatorId = idRecipe;
                db.recipeDAO().addInstruction(instructionDTO);

                sectionDTO.recipeCreatorIdForSection = idRecipe;
                long idSection = db.recipeDAO().addSection(sectionDTO);

                for (int i = 0; i < listOfMeasurementDTO.size(); i++ ){
                    ComponentDTO componentDTO = new ComponentDTO(i+1, "");
                    componentDTO.sectionCreatorId = idSection;
                    long idComponent = db.recipeDAO().addComponent(componentDTO);
                    MeasurementDTO measurementDTO = listOfMeasurementDTO.get(i);
                    measurementDTO.componentCreatorId = idComponent;
                    long idMeasurement = db.recipeDAO().addMeasurement(measurementDTO);
                    UnitDTO unitDTO = listOfUnitsDTO.get(i);
                    unitDTO.measurementCreatorId = idMeasurement;
                    db.recipeDAO().addUnit(unitDTO);

                    if (listOfIngredientDTO.size() <= i + 1){
                        IngredientDTO ingredientDTO = listOfIngredientDTO.get(i);
                        ingredientDTO.componentCreatorIdForIngredient = idComponent;
                        db.recipeDAO().addIngredient(ingredientDTO);
                    }
                }
            }
        });
    }

    public void deleteFullRecipe(RecipeWithSectionsAndInstructionsDTO recipe){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.recipeDAO().deleteRecipe(recipe.recipe);
                for (SectionDTO section :
                        recipe.sections) {
                    int sectionId = section.idSection;
                    List<ComponentDTO> sectionWithComponents = db.recipeDAO().getComponentsFromSectionId(sectionId);
                    for (ComponentDTO component :
                            sectionWithComponents) {
                        List<MeasurementDTO> measurementDTOS = db.recipeDAO().getMeasurementsFromComponentId(component.idComponent);
                        for (MeasurementDTO measurement :
                                measurementDTOS) {
                            db.recipeDAO().deleteMeasurement(measurement);
                        }
                        IngredientDTO ingredientDTO = db.recipeDAO().getIngredientFromComponentId(component.idComponent);
                        db.recipeDAO().deleteIngredient(ingredientDTO);
                        db.recipeDAO().deleteComponent(component);
                    }
                    db.recipeDAO().deleteSection(section);
                }
                for (InstructionDTO instruction :
                        recipe.instructions) {
                    db.recipeDAO().deleteInstruction(instruction);
                }
            }
        });
    }

    //Overrides last text, som returns only last measurement with ingredient and unit.
    //Is not reset when tapping into same/new recipe
    public void setIngredientMeasurementText(RecipeWithSectionsAndInstructionsDTO recipe){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (SectionDTO section :
                        recipe.sections) {
                    List<ComponentDTO> sectionWithComponents = db.recipeDAO().getComponentsFromSectionId(section.idSection);
                    for (ComponentDTO component :
                            sectionWithComponents) {
                        ListenableFuture<List<MeasurementDTO>> measurementDTOS = db.recipeDAO().getMeasurementsFromComponentIdFuture(component.idComponent);
                        measurementDTOS.addListener(()->{
                            try {
                                String t = ingredientMeasurementText.getValue();
                                t += measurementDTOS.get().get(0).getQuantity() + " ";
                                ingredientMeasurementText.postValue(t);

                                ListenableFuture<UnitDTO> unitDTO = db.recipeDAO().getUnitFromMeasurementIdFuture(measurementDTOS.get().get(0).idMeasurement);
                                unitDTO.addListener(()->{
                                    try {
                                        String tUnit = ingredientMeasurementText.getValue();
                                        tUnit += unitDTO.get().getName() + " ";
                                        ingredientMeasurementText.postValue(tUnit);
                                    } catch (Exception e){
                                        Log.e(TAG, "Failed getting unitDTO: ", e);
                                    }
                                }, ContextCompat.getMainExecutor(application.getApplicationContext()));

                                ListenableFuture<IngredientDTO> ingredientDTO = db.recipeDAO().getIngredientFromComponentIdFuture(component.idComponent);
                                ingredientDTO.addListener(()->{
                                    try {
                                        String tIngre = ingredientMeasurementText.getValue();
                                        tIngre += ingredientDTO.get().getName() + "\n";
                                        ingredientMeasurementText.postValue(tIngre);
                                    } catch (Exception e){
                                        Log.e(TAG, "Failed getting ingredientDTO: ", e);
                                    }
                                }, ContextCompat.getMainExecutor(application.getApplicationContext()));

                            }catch (Exception e){
                                Log.e(TAG, "Failed getting measurementDTOS: ", e);
                            }
                        }, ContextCompat.getMainExecutor(application.getApplicationContext()));
                    }
                }
            }
        });
    }

    public LiveData<String> getIngredientMeasurementText(){
        return ingredientMeasurementText;
    }


}
