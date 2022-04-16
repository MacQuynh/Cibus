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
    private MutableLiveData<List<RecipeDAO>> recipeList;
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
        recipeList = new MutableLiveData<List<RecipeDAO>>();
        recipeMutable = new MutableLiveData<>();
        executor = Executors.newSingleThreadExecutor();                //executor for background processing
//        recipesLiveData = db.drinkDAO().getAllDrinks();                             //get LiveData reference to all entries
        retrofitClient = new RetrofitClient();
    }

    public void getInitialListFromAPI() {
        retrofitClient.getInstance().getJsonApi().getRandomRecipesFromTheLast30min().enqueue(new Callback<Recipes>() {
            @Override
            public void onResponse(@NonNull Call<Recipes> call, @NonNull Response<Recipes> response) {
                Log.d(TAG, "onResponse: " + response);
                if (response.isSuccessful() && response.body() != null) {

                    List<Result> list = new ArrayList<>();

                    for (Result r : response.body().getResults()) {
                        Result rm = new Result();
                        rm.setSeoTitle(r.getSeoTitle());
                        rm.setInstructions(r.getInstructions());
                        //TODO insert the rest

                        list.add(rm);
                    }
//                    recipeList.postValue(list); //TODO: Re-insert again.
                } else {
                    Log.d(TAG, "onResponse: " + "Something went wrong in DB");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Recipes> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }
}
