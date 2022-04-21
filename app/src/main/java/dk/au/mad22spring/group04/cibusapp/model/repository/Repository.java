package dk.au.mad22spring.group04.cibusapp.model.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dk.au.mad22spring.group04.cibusapp.API.RetrofitClient;
import dk.au.mad22spring.group04.cibusapp.database.RecipeDAO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
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
    private MutableLiveData<List<Result>> recipeList;
    private MutableLiveData<RecipeDAO> recipeMutable;

    private RetrofitClient retrofitClient;

    //Context - used to inform the user if the drink already exist
    private Context context;

    //Singleton pattern to make sure there is only one instance of the Repository in use
    public static Repository getInstance(Application app) {
        if (instance == null) {
            instance = new Repository(app);
        }
        return instance;
    }

    //constructor - takes Application object for context
    private Repository(Application app) {
        this.context = app;
//        db = DrinkDatabase.getDatabase(app.getApplicationContext());  //initialize database
        recipeList = new MutableLiveData<List<Result>>();
        recipeMutable = new MutableLiveData<>();
        executor = Executors.newSingleThreadExecutor();                //executor for background processing
//        recipesLiveData = db.drinkDAO().getAllDrinks();                             //get LiveData reference to all entries
        retrofitClient = new RetrofitClient();
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
                    recipeList.postValue(list);
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
}
