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
import dk.au.mad22spring.group04.cibusapp.database.RecipeDAO;
import dk.au.mad22spring.group04.cibusapp.database.RecipeDatabase;
import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.model.Component;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.IngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.InstructionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.MeasurementDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionWithComponentsDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.UnitDTO;
import dk.au.mad22spring.group04.cibusapp.model.Instruction;
import dk.au.mad22spring.group04.cibusapp.model.Ingredient;
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
//    private RecipeDatabase db;              //database
    private ExecutorService executor;       //for asynch processing
    private LiveData<List<RecipeDAO>> recipesLiveData; //livedata
    private static Repository instance;     //for Singleton pattern
    private RecipeDatabase db;
    private Application application;

    private MutableLiveData<List<Instruction>> listInstructionMutable;
    private MutableLiveData<List<Component>> listSectionMutable;
    private MutableLiveData<List<Result>> recipeList;
    private MutableLiveData<Result> recipeApi;
    private MutableLiveData<RecipeDTO> recipeMutable;
    final MutableLiveData<List<RecipeWithSectionsAndInstructionsDTO>> recipesDB;
    final MutableLiveData<RecipeWithSectionsAndInstructionsDTO> recipeDB;
    final MutableLiveData<String> ingredientMeasurementText;


    private RecipeDTO finalRecipeFromAPI; //TODO Refactor
    private Double totalTimeMinutes, cookTimeMinutes, prepTimeMinutes;
    private Float userRating;

    private RetrofitClient retrofitClient;

    //Context - used to inform the user if the recipe already exist
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
        listSectionMutable = new MutableLiveData<List<Component>>();
        listInstructionMutable = new MutableLiveData<List<Instruction>>();
        recipeList = new MutableLiveData<List<Result>>();
        recipeApi = new MutableLiveData<Result>();
        recipeMutable = new MutableLiveData<>();
        executor = Executors.newSingleThreadExecutor();                //executor for background processing
        retrofitClient = new RetrofitClient();
        recipesDB = new MutableLiveData<List<RecipeWithSectionsAndInstructionsDTO>>();
        recipeDB = new MutableLiveData<RecipeWithSectionsAndInstructionsDTO>();
        ingredientMeasurementText = new MutableLiveData<String>("");
        this.application = app;
    }

    //Singleton pattern to make sure there is only one instance of the Repository in use
    public static Repository getInstance(Application application) {
        if (instance == null) {
            instance = new Repository(application);
        }
        return instance;
    }

    public LiveData<List<Result>> getInitialListBack() {
        return recipeList;
    }

    public void getInitialListFromAPI() {
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
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.recipeDAO().deleteAllingre();
                db.recipeDAO().deleteAllinstr();
                db.recipeDAO().deleteAllmeas();
                db.recipeDAO().deleteAllrecipe();
                db.recipeDAO().deleteAllsecti();
                db.recipeDAO().deleteAlluni();
                db.recipeDAO().deleteAllComponents();
            }
        });
        return recipesDB;
    }

    public void searchAllUserRecipes(String searchText) {
        //inspiration for searching for part of word https://stackoverflow.com/questions/61948455/android-room-query-text-matches-exactly-the-search-string-or-start-with-search
        ListenableFuture<List<RecipeWithSectionsAndInstructionsDTO>> list = db.recipeDAO().getRecipesWithSectionsAndInstructionsFromSearch(searchText + "%", Constants.USER_ID);
        list.addListener(() -> {
            try {
                recipesDB.postValue(list.get());

            } catch (Exception e) {
                Log.e(Constants.TAG_REPOSITORY, "searchAllUserRecipes: ", e);
            }
        }, ContextCompat.getMainExecutor(application.getApplicationContext()));

    }

/*    public void setFullRecipeByName(long recipeId) {
        ListenableFuture<RecipeWithSectionsAndInstructionsDTO> recipe = db.recipeDAO().getFullRecipeById(recipeId);

        recipe.addListener(() -> {
            try {
                recipeDB.postValue(recipe.get());
            } catch (Exception e) {
                Log.e(Constants.TAG_REPOSITORY, "Error loading Recipe: ", e);
            }

        }, ContextCompat.getMainExecutor(application.getApplicationContext()));
    }*/

    public RecipeWithSectionsAndInstructionsDTO getFullRecipeFromDB(int index) {
        return recipesDB.getValue().get(index);
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
                            0,
                            0,
                            0,
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

    public void addRecipesDefault2() {

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

                IngredientDTO ingre1 = new IngredientDTO("Meat", "Meat", "Meat");
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

                IngredientDTO ingre2 = new IngredientDTO("Milk", "Milk", "Milk");
                ingre2.componentCreatorIdForIngredient = idComponent2;
                db.recipeDAO().addIngredient(ingre2);
            }
        });


        Log.d(Constants.TAG_REPOSITORY, "addRecipesDefault: ");
    }

    public void updateFullRecipe(RecipeDTO recipe) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.recipeDAO().updateRecipe(recipe);
            }
        });
    }

    public void searchRecipes(String search_text) {
        searchRecipesFromString(search_text);
    }

    public LiveData<List<Result>> searchRecipes() {
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

    public Result getRecipeFromApiByIndex(int index) {
        return recipeList.getValue().get(index);
    }

    //TODO: not used
    public void getRecipeByName(String name) {
        RetrofitClient.getInstance().getJsonApi().getRecipeFromSearchString(name).enqueue(new Callback<Recipes>() {
            @Override
            public void onResponse(@NonNull Call<Recipes> call, @NonNull Response<Recipes> response) {
                if (response.isSuccessful() && response.body() != null) {

                    try {
                        /*Recipe*/
                        RecipeDTO recipeDTO = null;
                        List<Instruction> instructionList = new ArrayList<>();
                        List<Component> componentList = new ArrayList<>();

                        totalTimeMinutes = (Double) response.body().getResults().get(0).getTotalTimeMinutes();
                        cookTimeMinutes = (Double) response.body().getResults().get(0).getCookTimeMinutes();
                        prepTimeMinutes = (Double) response.body().getResults().get(0).getPrepTimeMinutes();
                        userRating = response.body().getResults().get(0).getUserRatings().getCountPositive().floatValue();

                        if (totalTimeMinutes == null || cookTimeMinutes == null || prepTimeMinutes == null || userRating == null) {
                            totalTimeMinutes = 0.0;
                            cookTimeMinutes = 0.0;
                            prepTimeMinutes = 0.0;
                            userRating = 0.0f;
                        }
                        if (recipeMutable != null) {
                            recipeDTO = new RecipeDTO(null, response.body().getResults().get(0).getName(),
                                    response.body().getResults().get(0).getThumbnailUrl(),
                                    totalTimeMinutes.floatValue(), cookTimeMinutes.floatValue(), prepTimeMinutes.floatValue(),
                                    response.body().getResults().get(0).getCountry(),
                                    response.body().getResults().get(0).getNumServings(),
                                    response.body().getResults().get(0).getDescription(),
                                    response.body().getResults().get(0).getCreatedAt(),
                                    response.body().getResults().get(0).getUpdatedAt(),
                                    userRating,
                                    response.body().getResults().get(0).getId().toString()
                            );
                        }

                        /*Instruction*/
                        for (Instruction instruction : response.body().getResults().get(0).getInstructions()) {
                            Instruction i = new Instruction();
                            i.setDisplayText(instruction.getDisplayText());
                            i.setTemperature(instruction.getTemperature());
                            i.setEndTime(instruction.getEndTime());
                            i.setStartTime(instruction.getStartTime());
                            i.setId(instruction.getId());
                            instructionList.add(i);
                        }

                        /*Section & Component*/
                        try {
                            for (Component component : response.body().getResults().get(0).getSections().get(0).getComponents()) {
                                Component c = new Component();
                                c.setRawText(component.getIngredient().getName());
                                c.setIngredient(component.getIngredient());
                                c.setMeasurements(component.getMeasurements());
                                componentList.add(c);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        recipeMutable.postValue(recipeDTO);
                        listInstructionMutable.postValue(instructionList);
                        listSectionMutable.postValue(componentList);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Recipes> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    public LiveData<RecipeDTO> getRecipe() {
        return recipeMutable;
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

                    if (listOfIngredientDTO.size() <= i + 1) {
                        IngredientDTO ingredientDTO = listOfIngredientDTO.get(i);
                        ingredientDTO.componentCreatorIdForIngredient = idComponent;
                        db.recipeDAO().addIngredient(ingredientDTO);
                    }
                }
            }
        });
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
    }

    //Overrides last text, som returns only last measurement with ingredient and unit.
    //Is not reset when tapping into same/new recipe
    public void setIngredientMeasurementText(RecipeWithSectionsAndInstructionsDTO recipe) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (SectionDTO section :
                        recipe.sections) {
                    List<ComponentDTO> sectionWithComponents = db.recipeDAO().getComponentsFromSectionId(section.idSection);
                    for (ComponentDTO component :
                            sectionWithComponents) {
                        ListenableFuture<List<MeasurementDTO>> measurementDTOS = db.recipeDAO().getMeasurementsFromComponentIdFuture(component.idComponent);
                        measurementDTOS.addListener(() -> {
                            try {
                                String t = ingredientMeasurementText.getValue();
                                t += measurementDTOS.get().get(0).getQuantity() + " ";
                                ingredientMeasurementText.postValue(t);

                                ListenableFuture<UnitDTO> unitDTO = db.recipeDAO().getUnitFromMeasurementIdFuture(measurementDTOS.get().get(0).idMeasurement);
                                unitDTO.addListener(() -> {
                                    try {
                                        String tUnit = ingredientMeasurementText.getValue();
                                        tUnit += unitDTO.get().getName() + " ";
                                        ingredientMeasurementText.postValue(tUnit);
                                    } catch (Exception e) {
                                        Log.e(TAG, "Failed getting unitDTO: ", e);
                                    }
                                }, ContextCompat.getMainExecutor(application.getApplicationContext()));

                                ListenableFuture<IngredientDTO> ingredientDTO = db.recipeDAO().getIngredientFromComponentIdFuture(component.idComponent);
                                ingredientDTO.addListener(() -> {
                                    try {
                                        String tIngre = ingredientMeasurementText.getValue();
                                        tIngre += ingredientDTO.get().getName() + "\n";
                                        ingredientMeasurementText.postValue(tIngre);
                                    } catch (Exception e) {
                                        Log.e(TAG, "Failed getting ingredientDTO: ", e);
                                    }
                                }, ContextCompat.getMainExecutor(application.getApplicationContext()));

                            } catch (Exception e) {
                                Log.e(TAG, "Failed getting measurementDTOS: ", e);
                            }
                        }, ContextCompat.getMainExecutor(application.getApplicationContext()));
                    }
                }
            }
        });
    }

    public LiveData<String> getIngredientMeasurementText() {
        return ingredientMeasurementText;
    }


    public LiveData<List<Instruction>> getInstruction() {
        return listInstructionMutable;
    }

    public LiveData<List<Component>> getSectionWithComponentAPI() {
        return listSectionMutable;
    }

    //TODO: not used
    public void getRecipe(String name) {
        getRecipeByName(name);
    }

    public void addRecipeFromAPItoDB(String recipeAddedDB) {
        retrofitClient.getJsonApi().getRecipeFromSearchString(recipeAddedDB).enqueue(new Callback<Recipes>() {
            @Override
            public void onResponse(Call<Recipes> call, Response<Recipes> response) {
                /*Recipe*/
                RecipeDTO recipeDTO = null;
                totalTimeMinutes = (Double) response.body().getResults().get(0).getTotalTimeMinutes();
                cookTimeMinutes = (Double) response.body().getResults().get(0).getCookTimeMinutes();
                prepTimeMinutes = (Double) response.body().getResults().get(0).getPrepTimeMinutes();
                userRating = response.body().getResults().get(0).getUserRatings().getCountPositive().floatValue();

                if (totalTimeMinutes == null || cookTimeMinutes == null || prepTimeMinutes == null || userRating == null) {
                    totalTimeMinutes = 0.0;
                    cookTimeMinutes = 0.0;
                    prepTimeMinutes = 0.0;
                    userRating = 0.0f;
                }
                if (recipeMutable != null) {
                    recipeDTO = new RecipeDTO(null, response.body().getResults().get(0).getName(),
                            response.body().getResults().get(0).getThumbnailUrl(),
                            totalTimeMinutes.floatValue(), cookTimeMinutes.floatValue(), prepTimeMinutes.floatValue(),
                            response.body().getResults().get(0).getCountry(),
                            response.body().getResults().get(0).getNumServings(),
                            response.body().getResults().get(0).getDescription(),
                            response.body().getResults().get(0).getCreatedAt(),
                            response.body().getResults().get(0).getUpdatedAt(),
                            userRating,
                            response.body().getResults().get(0).getId().toString()
                    );
                }
                List<Instruction> instructionList = new ArrayList<>();
                for (Instruction instruction : response.body().getResults().get(0).getInstructions()) {
                    Instruction i = new Instruction();
                    i.setDisplayText(instruction.getDisplayText());
                    i.setTemperature(instruction.getTemperature());
                    i.setEndTime(instruction.getEndTime());
                    i.setStartTime(instruction.getStartTime());
                    i.setId(instruction.getId());
                    instructionList.add(i);
                }

                String instructions = "";
                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < instructionList.size(); i++) {
                    stringBuilder.append(i + 1 + ") ").append(instructionList.get(i).getDisplayText())
                            .append("\n\n");
                    instructions = stringBuilder.toString();
                }

                InstructionDTO instructionDTO = new InstructionDTO(instructions, 00, 00, 1);

                SectionDTO sectionDTO = new SectionDTO("", 1);

                List<MeasurementDTO> measurementDTOS = new ArrayList<MeasurementDTO>();
                for (Measurement measurement : response.body().getResults().get(0).getSections().get(0).getComponents().get(0).getMeasurements()) {
                    Measurement m = new Measurement();
                    m.setId(measurement.getId());
                    m.setQuantity(measurement.getQuantity());
                    measurementDTOS.add(new MeasurementDTO(measurement.getQuantity()));
                }


                ArrayList<UnitDTO> listOfUnitsDTO = new ArrayList<UnitDTO>();
                for (Measurement m : response.body().getResults().get(0).getSections().get(0).getComponents().get(0).getMeasurements()) {
                    Unit u = new Unit();
                    u.setName(m.getUnit().getName());
                    u.setDisplayPlural(m.getUnit().getDisplayPlural());
                    u.setDisplaySingular(m.getUnit().getDisplaySingular());
                    listOfUnitsDTO.add(new UnitDTO(u.getName(), u.getDisplayPlural(), u.getDisplaySingular()));
                }

                ArrayList<IngredientDTO> ingredientDTOS = new ArrayList<IngredientDTO>();
                for (Component i : response.body().getResults().get(0).getSections().get(0).getComponents()) {
                    Component c = new Component();
                    c.setRawText(i.getIngredient().getName());
                    c.setIngredient(i.getIngredient());
                    c.setMeasurements(i.getMeasurements());
                    ingredientDTOS.add(new IngredientDTO(i.getIngredient().getName(), i.getIngredient().getDisplayPlural(), i.getIngredient().getDisplaySingular()));
                }

                Log.d(TAG, "onResponse: " + listOfUnitsDTO + measurementDTOS + ingredientDTOS);

                RecipeDTO finalRecipeDTO = recipeDTO;
                try {
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            RecipeDTO recipeDTO = new RecipeDTO(finalRecipeDTO.idRecipe, finalRecipeDTO.getName(), finalRecipeDTO.getThumbnailUrl(),
                                    finalRecipeDTO.getTotalTimeMinutes(), finalRecipeDTO.getCookTimeMinutes(), finalRecipeDTO.getPrepTimeMinutes(),
                                    finalRecipeDTO.getCountry(), finalRecipeDTO.getNumServings(), finalRecipeDTO.getDescription(), finalRecipeDTO.getCreatedAtUnix(),
                                    finalRecipeDTO.getUpdatedAtUnix(), finalRecipeDTO.getUserRatings(), Constants.USER_ID);

                            long idRecipe = db.recipeDAO().addRecipe(recipeDTO);

                            instructionDTO.recipeCreatorId = idRecipe;
                            db.recipeDAO().addInstruction(instructionDTO);

                            sectionDTO.recipeCreatorIdForSection = idRecipe;
                            long idSection = db.recipeDAO().addSection(sectionDTO);


                            for (int i = 0; i < measurementDTOS.size(); i++) {
                                ComponentDTO componentDTO = new ComponentDTO(i, "");
                                componentDTO.sectionCreatorId = idSection;
                                long idComponent = db.recipeDAO().addComponent(componentDTO);
                                MeasurementDTO measurementDTO = measurementDTOS.get(i);
                                measurementDTO.componentCreatorId = idComponent;
                                long idMeasurement = db.recipeDAO().addMeasurement(measurementDTO);
                                UnitDTO unitDTO = listOfUnitsDTO.get(i);
                                unitDTO.measurementCreatorId = idMeasurement;
                                db.recipeDAO().addUnit(unitDTO);

                                if (ingredientDTOS.size() <= i) {
                                    IngredientDTO ingredientDTO = ingredientDTOS.get(i);
                                    ingredientDTO.componentCreatorIdForIngredient = idComponent;
                                    db.recipeDAO().addIngredient(ingredientDTO);
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Recipes> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });

    }

    public RecipeDTO getRandomRecipeFromDB() {
        Random randomNumber = new Random();
        List<RecipeDTO> recipesFromDB = db.recipeDAO().getAllRecipesFromDB();
        int length = recipesFromDB.size();
        int high = length;
        int randomIndex = randomNumber.nextInt(high);

        return recipesFromDB.get(randomIndex);
    }
}
