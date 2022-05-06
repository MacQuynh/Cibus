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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.au.mad22spring.group04.cibusapp.API.RetrofitClient;
import dk.au.mad22spring.group04.cibusapp.R;
import dk.au.mad22spring.group04.cibusapp.database.RecipeDAO;
import dk.au.mad22spring.group04.cibusapp.database.RecipeDatabase;
import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.model.Component;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentWithMeasurementsAndIngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.IngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.InstructionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.MeasurementDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.UnitDTO;
import dk.au.mad22spring.group04.cibusapp.model.Instruction;
import dk.au.mad22spring.group04.cibusapp.model.Measurement;
import dk.au.mad22spring.group04.cibusapp.model.Recipes;
import dk.au.mad22spring.group04.cibusapp.model.Result;
import dk.au.mad22spring.group04.cibusapp.model.Section;
import dk.au.mad22spring.group04.cibusapp.model.Unit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private final String TAG = "Repository";

    // Code inspired by code demo from the lecture: "Room Demo"
    private ExecutorService executor;       //for asynch processing
    private static Repository instance;     //for Singleton pattern
    private RecipeDatabase db;
    private Application application;

    private MutableLiveData<Recipes> recipeList;
    private MutableLiveData<RecipeDTO> recipeMutable;
    final MutableLiveData<List<RecipeWithSectionsAndInstructionsDTO>> recipesDB;
    final MutableLiveData<String> ingredientMeasurementText;
    final MutableLiveData<List<ComponentWithMeasurementsAndIngredientDTO>> componentDB;

    private Double totalTimeMinutes, cookTimeMinutes, prepTimeMinutes;
    private Float userRating;

    private RetrofitClient retrofitClient;

    //Context - used to inform the user if the recipe already exist
    private Context context;
    private String mySearch;

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
        recipeList = new MutableLiveData<Recipes>();
        recipeMutable = new MutableLiveData<>();
        executor = Executors.newSingleThreadExecutor();                //executor for background processing
        retrofitClient = new RetrofitClient();
        recipesDB = new MutableLiveData<List<RecipeWithSectionsAndInstructionsDTO>>();
        ingredientMeasurementText = new MutableLiveData<String>("");
        componentDB = new MutableLiveData<List<ComponentWithMeasurementsAndIngredientDTO>>();
        this.application = app;
    }

    //Singleton pattern to make sure there is only one instance of the Repository in use
    public static Repository getInstance(Application application) {
        if (instance == null) {
            instance = new Repository(application);
        }
        return instance;
    }

    public LiveData<Recipes> getInitialListBack() {
        return recipeList;
    }

    public void getInitialListFromAPI() {
        retrofitClient.getInstance().getJsonApi().getRandomRecipesFromTheLast30min().enqueue(new Callback<Recipes>() {
            @Override
            public void onResponse(@NonNull Call<Recipes> call, @NonNull Response<Recipes> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Recipes list = response.body();
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

    public void updateDBRecipes() {
        searchAllUserRecipes(mySearch);
    }

    public void searchAllUserRecipes(String searchText) {
        mySearch = searchText;
        //inspiration for searching for part of word https://stackoverflow.com/questions/61948455/android-room-query-text-matches-exactly-the-search-string-or-start-with-search

        ListenableFuture<List<RecipeWithSectionsAndInstructionsDTO>> list = db.recipeDAO().getRecipesWithSectionsAndInstructionsFromSearch(searchText + "%", Constants.USER_ID);
        list.addListener(() -> {
            try {
                recipesDB.postValue(list.get());

            } catch (Exception e) {
                Log.e(Constants.TAG_REPOSITORY, "loading searchAllUserRecipes failed: ", e);
            }
        }, ContextCompat.getMainExecutor(application.getApplicationContext()));

    }

    public RecipeWithSectionsAndInstructionsDTO getFullRecipeFromDB(int id) {
        List<RecipeWithSectionsAndInstructionsDTO> list = recipesDB.getValue();

        RecipeWithSectionsAndInstructionsDTO finalRecipe = null;
        for (RecipeWithSectionsAndInstructionsDTO recipe : list
        ) {
            if (recipe.recipe.idRecipe == id) {
                finalRecipe = recipe;
                break;
            }
        }

        return finalRecipe;
    }

    public void setSectionWithComponentDB(int recipeId) {
        List<RecipeWithSectionsAndInstructionsDTO> recipeList = recipesDB.getValue();

        RecipeWithSectionsAndInstructionsDTO finalRecipe = null;
        for (RecipeWithSectionsAndInstructionsDTO recipe : recipeList
        ) {
            if (recipe.recipe.idRecipe == recipeId) {
                finalRecipe = recipe;
                break;
            }

        }
       int sectionId = finalRecipe.sections.get(0).idSection;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                ListenableFuture<List<ComponentWithMeasurementsAndIngredientDTO>> sectionWithComponentsDTO = db.recipeDAO().getComponentsFromSectionIdFuture(sectionId);
                sectionWithComponentsDTO.addListener(() -> {
                    try {
                        componentDB.postValue(sectionWithComponentsDTO.get());

                    } catch (Exception e) {
                        Log.e(TAG, "run: ", e);
                    }

                }, ContextCompat.getMainExecutor(application.getApplicationContext()));
            }
        });
    }

    public LiveData<List<ComponentWithMeasurementsAndIngredientDTO>> getSectionWithComponentDB() {
        return componentDB;
    }

    public void addRecipesDefault() {
        retrofitClient.getInstance().getJsonApi().getRandomRecipesFromTheLast30min().enqueue(new Callback<Recipes>() {
            @Override
            public void onResponse(@NonNull Call<Recipes> call, @NonNull Response<Recipes> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Result> firstTenInList = response.body().getResults().subList(0, 10);

                    parseRecipesAndSaveToDB(firstTenInList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Recipes> call, @NonNull Throwable t) {
                Log.d(Constants.TAG_REPOSITORY, "onFailure: " + t);
            }
        });
    }

    private void parseRecipesAndSaveToDB(List<Result> resultList) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (Result result :
                        resultList) {
                    RecipeDTO recipe = new RecipeDTO(
                            result.getId(),
                            result.getName(),
                            result.getThumbnailUrl(),
                            result.getTotalTimeMinutes(),
                            result.getCookTimeMinutes(),
                            result.getPrepTimeMinutes(),
                            result.getCountry(),
                            result.getNumServings(),
                            result.getDescription(),
                            result.getCreatedAt(),
                            result.getUpdatedAt(),
                            0,
                            Constants.USER_ID
                    );
                    long idRecipe = db.recipeDAO().addRecipe(recipe);

                    if (result.getInstructions() != null || result.getInstructions().size() > 0) {
                        for (Instruction instruction :
                                result.getInstructions()) {
                            InstructionDTO instructionObj = new InstructionDTO(
                                    instruction.getDisplayText(),
                                    instruction.getStartTime(),
                                    instruction.getEndTime(),
                                    instruction.getPosition());
                            instructionObj.recipeCreatorId = idRecipe;
                            db.recipeDAO().addInstruction(instructionObj);
                        }
                    }
                    if (result.getSections() != null || result.getSections().size() > 0) {
                        for (Section section :
                                result.getSections()) {
                            SectionDTO sectionObj = new SectionDTO(section.getName(), section.getPosition());
                            sectionObj.recipeCreatorIdForSection = idRecipe;
                            long idSection = db.recipeDAO().addSection(sectionObj);

                            if (section.getComponents() != null || section.getComponents().size() > 0) {
                                for (Component component :
                                        section.getComponents()) {
                                    ComponentDTO componentObj = new ComponentDTO(component.getPosition(), component.getRawText());
                                    componentObj.sectionCreatorId = idSection;
                                    long idComponent = db.recipeDAO().addComponent(componentObj);

                                    if (component.getMeasurements() != null || component.getMeasurements().size() > 0) {
                                        for (Measurement measurement :
                                                component.getMeasurements()) {
                                            MeasurementDTO measurementObj = new MeasurementDTO(measurement.getQuantity());
                                            measurementObj.componentCreatorId = idComponent;
                                            long idMeasurement = db.recipeDAO().addMeasurement(measurementObj);

                                            if (measurement.getUnit() != null) {
                                                UnitDTO unitObj = new UnitDTO(measurement.getUnit().getName(), measurement.getUnit().getDisplayPlural(), measurement.getUnit().getDisplaySingular());
                                                unitObj.measurementCreatorId = idMeasurement;
                                                db.recipeDAO().addUnit(unitObj);
                                            }
                                        }

                                        IngredientDTO ingredientObj = new IngredientDTO(component.getIngredient().getName(), component.getIngredient().getDisplayPlural(), component.getIngredient().getDisplaySingular());
                                        ingredientObj.componentCreatorIdForIngredient = idComponent;
                                        db.recipeDAO().addIngredient(ingredientObj);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

    }

    public void updateFullRecipe(RecipeDTO recipe) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.recipeDAO().updateRecipe(recipe);
            }
        });
        displayToast(application.getApplicationContext().getString(R.string.recipe_updated));
    }

    public void searchRecipes(String search_text) {
        searchRecipesFromString(search_text);
    }

    private void searchRecipesFromString(String search_text) {
        RetrofitClient.getInstance().getJsonApi().getRecipeFromSearchString(search_text).enqueue(new Callback<Recipes>() {
            @Override
            public void onResponse(@NonNull Call<Recipes> call, @NonNull Response<Recipes> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Recipes list = response.body();
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

    public Result getRecipeFromApiByIndex(int index) {
        return recipeList.getValue().getResults().get(index);
    }

    public void addNewRecipeToLibrary(RecipeDTO recipeDTO, InstructionDTO instructionDTO, SectionDTO sectionDTO, ArrayList<MeasurementDTO> listOfMeasurementDTO, ArrayList<UnitDTO> listOfUnitsDTO, ArrayList<IngredientDTO> listOfIngredientDTO) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                long idRecipe = db.recipeDAO().addRecipe(recipeDTO);

                instructionDTO.recipeCreatorId = idRecipe;
                db.recipeDAO().addInstruction(instructionDTO);

                sectionDTO.recipeCreatorIdForSection = idRecipe;
                long idSection = db.recipeDAO().addSection(sectionDTO);

                for (int i = 0; i < listOfMeasurementDTO.size(); i++) {
                    ComponentDTO componentDTO = new ComponentDTO(i + 1, "");
                    componentDTO.sectionCreatorId = idSection;
                    long idComponent = db.recipeDAO().addComponent(componentDTO);
                    MeasurementDTO measurementDTO = listOfMeasurementDTO.get(i);
                    measurementDTO.componentCreatorId = idComponent;
                    long idMeasurement = db.recipeDAO().addMeasurement(measurementDTO);
                    UnitDTO unitDTO = listOfUnitsDTO.get(i);
                    unitDTO.measurementCreatorId = idMeasurement;
                    db.recipeDAO().addUnit(unitDTO);

                    if (listOfIngredientDTO.size() >= i + 1) {
                        IngredientDTO ingredientDTO = listOfIngredientDTO.get(i);
                        ingredientDTO.componentCreatorIdForIngredient = idComponent;
                        db.recipeDAO().addIngredient(ingredientDTO);
                    }
                }
            }
        });
        displayToast(application.getApplicationContext().getString(R.string.added_recipe));
    }

    public void deleteFullRecipe(RecipeWithSectionsAndInstructionsDTO recipe) {
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
        displayToast(application.getApplicationContext().getString(R.string.recipe_deleted));
    }

    public void addRecipeFromAPItoDB(Result recipeAddedDB) {
        List<Result> resultList = new ArrayList<>();
        resultList.add(recipeAddedDB);
        parseRecipesAndSaveToDB(resultList);
        displayToast(application.getApplicationContext().getString(R.string.added_recipe));
    }

    public RecipeDTO getRandomRecipeFromDB() {
        Random randomNumber = new Random();
        List<RecipeDTO> recipesFromDB = db.recipeDAO().getAllRecipesFromDB(Constants.USER_ID);
        int length = recipesFromDB.size();
        int high = length;
        int randomIndex = randomNumber.nextInt(high);

        return recipesFromDB.get(randomIndex);
    }

    public RecipeWithSectionsAndInstructionsDTO getFirstRecipeFromDB() {
        return recipesDB.getValue().get(0);
    }

    private void displayToast(String message) {
        Toast.makeText(application.getApplicationContext().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
