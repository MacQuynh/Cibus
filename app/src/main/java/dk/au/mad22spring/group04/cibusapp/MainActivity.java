package dk.au.mad22spring.group04.cibusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationBarView;

import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.ui.interfaces.ApiRecipeSelectorInterface;
import dk.au.mad22spring.group04.cibusapp.ui.interfaces.UserRecipeSelectorInterface;
import dk.au.mad22spring.group04.cibusapp.ui.fragments.AddNewRecipe.AddNewRecipeFragment;
import dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListAPIDetails.RecipeListApiDetailsFragment;
import dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListApi.RecipeListApiFragment;
import dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipeDetails.UserRecipeDetailsFragment;
import dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipesList.UserRecipesListFragment;


//Navigation bar: https://www.geeksforgeeks.org/bottom-navigation-bar-in-android/
// https://material.io/components/bottom-navigation/android#using-bottom-navigation

//Master Detail inspiration: Demo from lecture "FragmentsArnieMovies"
public class MainActivity extends AppCompatActivity implements UserRecipeSelectorInterface, ApiRecipeSelectorInterface {

    public enum PhoneOrientation {PORTRAIT, LANDSCAPE}
    private PhoneOrientation phoneOrientation;
    public enum Mode {USER_RECIPE_LIST, USER_RECIPE_DETAILS, API_RECIPE_LIST, API_RECIPE_DETAILS, ADD_RECIPE} //TODO: add rest of modes (api)
    private Mode mode;

    //UI Widgets
    private NavigationBarView navigationBarView;

    //Tags for fragments
    private static final String USER_RECIPE_LIST_FRAG = "USER_RECIPE_LIST_FRAG";
    private static final String USER_RECIPE_DETAIL_FRAG = "USER_RECIPE_DETAIL_FRAG";
    private static final String RECIPE_API_LIST_FRAG = "RECIPE_API_LIST_FRAG";
    private static final String RECIPE_API_DETAIL_FRAG = "RECIPE_API_DETAIL_FRAG";
    private static final String ADD_RECIPE_FRAG = "ADD_RECIPE_FRAG";


    // Fragments:
    private UserRecipesListFragment userRecipesListFragment;
    private UserRecipeDetailsFragment userRecipeDetailsFragment;
    private RecipeListApiFragment recipeListApiFragment;
    private RecipeListApiDetailsFragment recipeListApiDetailsFragment;
    private AddNewRecipeFragment addNewRecipeFragment;


    //Containers for fragments:
    private LinearLayout recipeList;
    private LinearLayout recipeDetails;

    private long selectedUserRecipeId;
    private int selectedApiRecipeIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view setup:
        navigationBarView = findViewById(R.id.bottomNavigationView);
        navigationBarView.setSelectedItemId(R.id.home);
        recipeList = findViewById(R.id.mainActivityListLayout);
        recipeDetails = findViewById(R.id.mainActivityDetailLayout);

        //determine orientation
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            phoneOrientation = PhoneOrientation.PORTRAIT;
        } else {
            phoneOrientation = PhoneOrientation.LANDSCAPE;
        }

        if(savedInstanceState == null){
            selectedUserRecipeId = 0;
            selectedApiRecipeIndex = 0;
            mode = Mode.API_RECIPE_LIST;

            //Initialize fragments
            userRecipeDetailsFragment = new UserRecipeDetailsFragment();
            userRecipesListFragment = new UserRecipesListFragment();
            recipeListApiFragment = new RecipeListApiFragment();
            recipeListApiDetailsFragment = new RecipeListApiDetailsFragment();
            addNewRecipeFragment = new AddNewRecipeFragment();

            userRecipeDetailsFragment.setSelectedRecipe(selectedUserRecipeId);

            recipeListApiDetailsFragment.setSelectedRecipe(selectedApiRecipeIndex);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainActivityListLayout, userRecipesListFragment, USER_RECIPE_LIST_FRAG)
                    .replace(R.id.mainActivityListLayout, recipeListApiFragment, RECIPE_API_LIST_FRAG)
                    .commit();
        } else{

            mode = (Mode) savedInstanceState.getSerializable(Constants.MODE);
            if(mode == null){
                mode = Mode.API_RECIPE_LIST;
            }

            if(recipeListApiFragment == null){
                recipeListApiFragment = new RecipeListApiFragment();
            }
            if(recipeListApiDetailsFragment == null){
                recipeListApiDetailsFragment = new RecipeListApiDetailsFragment();
            }
            if(userRecipesListFragment== null){
                userRecipesListFragment = new UserRecipesListFragment();
            }
            if(userRecipeDetailsFragment == null){
                userRecipeDetailsFragment = new UserRecipeDetailsFragment();
            }
            if(addNewRecipeFragment == null){
                addNewRecipeFragment = new AddNewRecipeFragment();
            }
            updateFragmentView(mode);
        }

        //https://stackoverflow.com/questions/68021770/setonnavigationitemselectedlistener-deprecated
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.user:
                        mode = Mode.USER_RECIPE_LIST;
                        switchFragment();
                        return true;
                    case R.id.home:
                        mode = Mode.API_RECIPE_LIST;
                        switchFragment();
                        return true;
                    case R.id.add:
                        mode = Mode.ADD_RECIPE;
                        switchFragment();
                        return true;
                }
                return true;
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(Constants.MODE, mode);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onUserRecipeSelected(long id) {
        selectedUserRecipeId = id;
        userRecipeDetailsFragment.setSelectedRecipe(id);
        mode = Mode.USER_RECIPE_DETAILS;
        switchFragment();
    }

    @Override
    public void onApiRecipeSelected(int index) {
        selectedApiRecipeIndex = index;
        recipeListApiDetailsFragment.setSelectedRecipe(index);
        mode = Mode.API_RECIPE_DETAILS;
        switchFragment();
    }

    private void updateFragmentView(Mode mode){
        this.mode = mode;
        switchFragment();
    }

    private void switchFragment() {
        if (phoneOrientation == PhoneOrientation.PORTRAIT) {
            switch (mode){
                case USER_RECIPE_LIST:
                    recipeList.setVisibility(View.VISIBLE);
                    recipeDetails.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainActivityListLayout, userRecipesListFragment, USER_RECIPE_LIST_FRAG)
                            .commit();
                    break;
                case USER_RECIPE_DETAILS:
                    recipeList.setVisibility(View.GONE);
                    recipeDetails.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainActivityDetailLayout, userRecipeDetailsFragment, USER_RECIPE_DETAIL_FRAG)
                            .commit();
                    break;
                case ADD_RECIPE:
                    recipeDetails.setVisibility(View.GONE);
                    recipeList.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainActivityListLayout, addNewRecipeFragment, ADD_RECIPE_FRAG)
                            .commit();
                    break;
                case API_RECIPE_DETAILS:
                    recipeList.setVisibility(View.GONE);
                    recipeDetails.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainActivityDetailLayout, recipeListApiDetailsFragment, RECIPE_API_DETAIL_FRAG)
                            .commit();
                    break;
                default: //case API_RECIPE_LIST:
                    recipeList.setVisibility(View.VISIBLE);
                    recipeDetails.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainActivityListLayout, recipeListApiFragment, RECIPE_API_LIST_FRAG)
                            .commit();
                    break;
            }
        } else  {
            switch (mode){
                case USER_RECIPE_LIST:
                case USER_RECIPE_DETAILS:
                    recipeList.setVisibility(View.VISIBLE);
                    recipeDetails.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainActivityListLayout, userRecipesListFragment, USER_RECIPE_LIST_FRAG)
                            .commit();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainActivityDetailLayout, userRecipeDetailsFragment, USER_RECIPE_DETAIL_FRAG)
                            .commit();
                    break;
                case ADD_RECIPE:

                    //TODO: ved ikke helt hvad vi gør her med containerne. Der er jo to, men vi skal kun have én til dette view..
                    recipeDetails.setVisibility(View.GONE);
                    recipeList.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainActivityListLayout, addNewRecipeFragment, ADD_RECIPE_FRAG)
                            .commit();
                    break;
                default: //case API_RECIPE_LIST: and API_RECIPE_DETAILS:
                    recipeList.setVisibility(View.VISIBLE);
                    recipeDetails.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainActivityDetailLayout, recipeListApiFragment, RECIPE_API_LIST_FRAG)
                            .commit();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainActivityDetailLayout, recipeListApiDetailsFragment, RECIPE_API_DETAIL_FRAG)
                            .commit();
                    break;
            }
        }
    }
}