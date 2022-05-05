package dk.au.mad22spring.group04.cibusapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationBarView;

import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.ui.fragments.AddNewRecipe.AddNewRecipeFragment;
import dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListApi.RecipeListApiFragment;
import dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListAPIDetails.RecipeListApiDetailsFragment;
import dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipeDetails.UserRecipeDetailsFragment;
import dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipesList.UserRecipesListFragment;
import dk.au.mad22spring.group04.cibusapp.ui.interfaces.ApiRecipeSelectorInterface;
import dk.au.mad22spring.group04.cibusapp.ui.interfaces.UserRecipeSelectorInterface;
import dk.au.mad22spring.group04.cibusapp.ui.viewModels.MainActivityViewModel;


//Navigation bar: https://www.geeksforgeeks.org/bottom-navigation-bar-in-android/
// https://material.io/components/bottom-navigation/android#using-bottom-navigation

//Master Detail inspiration: Demo from lecture "FragmentsArnieMovies"
public class MainActivity extends AppCompatActivity implements UserRecipeSelectorInterface, ApiRecipeSelectorInterface {

    private static final String TAG = "MainActivity";

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


    //Containers and UI widgets for fragments:
    private LinearLayout recipeList;
    private LinearLayout recipeDetails;
    private LinearLayout recipeFullLandScape;
    private View dividerLandscape;

    //lists of data
    private int selectedUserRecipeId;
    private int selectedApiRecipeIndex;

    //Foreground service:
    RecipeService recipeService;

    //ViewModel
    MainActivityViewModel mainVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeService = new RecipeService();
        mainVM = new ViewModelProvider(this).get(MainActivityViewModel.class);

        //view setup:
        navigationBarView = findViewById(R.id.bottomNavigationView);
        navigationBarView.setSelectedItemId(R.id.home);
        recipeList = findViewById(R.id.mainActivityListLayout);
        recipeDetails = findViewById(R.id.mainActivityDetailLayout);
        recipeFullLandScape = findViewById(R.id.mainActivityFullLayout);
        dividerLandscape = findViewById(R.id.mainActivityDivider);

        //determine orientation
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            phoneOrientation = PhoneOrientation.PORTRAIT;
        } else {
            phoneOrientation = PhoneOrientation.LANDSCAPE;
        }

/*        //Get list data
        mainVM = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainVM.getUserRecipes().observe(this, new Observer<List<RecipeWithSectionsAndInstructionsDTO>>() {
            @Override
            public void onChanged(List<RecipeWithSectionsAndInstructionsDTO> recipeWithSectionsAndInstructionsDTOS) {
                userRecipeList = recipeWithSectionsAndInstructionsDTOS;
                if(userRecipeList.size() <=0){
                    mainVM.addDefaultRecipes();
                }
            }
        });*/

        if (savedInstanceState == null) {
            mode = Mode.API_RECIPE_LIST;

            //Initialize fragments
            userRecipeDetailsFragment = new UserRecipeDetailsFragment();
            userRecipesListFragment = new UserRecipesListFragment();
            recipeListApiFragment = new RecipeListApiFragment();
            recipeListApiDetailsFragment = new RecipeListApiDetailsFragment();
            addNewRecipeFragment = new AddNewRecipeFragment();

            // userRecipeDetailsFragment.setSelectedRecipe(selectedUserRecipeIndex);


            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainActivityListLayout, userRecipesListFragment, USER_RECIPE_LIST_FRAG)
                    .replace(R.id.mainActivityListLayout, recipeListApiFragment, RECIPE_API_LIST_FRAG)
                    .commit();
        } else {

            mode = (Mode) savedInstanceState.getSerializable(Constants.MODE);
            if (mode == null) {
                mode = Mode.API_RECIPE_LIST;
            }

            recipeListApiFragment = (RecipeListApiFragment) getSupportFragmentManager().findFragmentByTag(RECIPE_API_LIST_FRAG);
            if (recipeListApiFragment == null) {
                recipeListApiFragment = new RecipeListApiFragment();
            }
            recipeListApiDetailsFragment = (RecipeListApiDetailsFragment) getSupportFragmentManager().findFragmentByTag(RECIPE_API_DETAIL_FRAG);
            if (recipeListApiDetailsFragment == null) {
                recipeListApiDetailsFragment = new RecipeListApiDetailsFragment();
            }
            userRecipesListFragment = (UserRecipesListFragment) getSupportFragmentManager().findFragmentByTag(USER_RECIPE_LIST_FRAG);
            if (userRecipesListFragment == null) {
                userRecipesListFragment = new UserRecipesListFragment();
            }
            userRecipeDetailsFragment = (UserRecipeDetailsFragment) getSupportFragmentManager().findFragmentByTag(USER_RECIPE_DETAIL_FRAG);
            if (userRecipeDetailsFragment == null) {
                userRecipeDetailsFragment = new UserRecipeDetailsFragment();
            }
            addNewRecipeFragment = (AddNewRecipeFragment) getSupportFragmentManager().findFragmentByTag(ADD_RECIPE_FRAG);
            if (addNewRecipeFragment == null) {
                addNewRecipeFragment = new AddNewRecipeFragment();
            }
            updateFragmentView(mode);
        }


        //https://stackoverflow.com/questions/68021770/setonnavigationitemselectedlistener-deprecated
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
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

        //Start foreground service:
        Intent foregroundServiceIntent = new Intent(this, RecipeService.class);
        startService(foregroundServiceIntent);
    }

    @Override
    public void onBackPressed() {
        if (phoneOrientation == PhoneOrientation.PORTRAIT) {
            switch (mode) {
                case USER_RECIPE_LIST:
                case ADD_RECIPE:
                case API_RECIPE_DETAILS:
                    navigationBarView.setSelectedItemId(androidx.appcompat.R.id.home);
                    break;
                case USER_RECIPE_DETAILS:
                    mode = Mode.USER_RECIPE_LIST;
                    break;
                default: //case API_RECIPE_LIST:
                    finish();
                    break;
            }
        } else {
            switch (mode) {
                case USER_RECIPE_LIST:
                case USER_RECIPE_DETAILS:
                case ADD_RECIPE:
                    navigationBarView.setSelectedItemId(androidx.appcompat.R.id.home);
                    break;
                default: //case API_RECIPE_LIST: and API_RECIPE_DETAILS:
                    finish();
                    break;
            }
        }
        switchFragment();
    }

    //TODO: move to a viewModel
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(Constants.MODE, mode);
        super.onSaveInstanceState(outState);
    }

    //UserRecipeSelectorInterface implementations
    @Override
    public void onUserRecipeSelected(int id) {
        selectedUserRecipeId = id;
        userRecipeDetailsFragment.setSelectedRecipe(id);
        mode = Mode.USER_RECIPE_DETAILS;
        switchFragment();
    }

    @Override
    public void onBackFromUserRecipeDetails() {
        mode = Mode.USER_RECIPE_LIST;
        switchFragment();
    }

    //ApiRecipeSelectorInterface implementations
    @Override
    public void onApiRecipeSelected(int index) {
        selectedApiRecipeIndex = index;
        recipeListApiDetailsFragment.setSelectedRecipe(index);
        mode = Mode.API_RECIPE_DETAILS;
        switchFragment();
    }

    private void updateFragmentView(Mode mode) {
        this.mode = mode;
        switchFragment();
    }

    private void switchFragment() {
        if (phoneOrientation == PhoneOrientation.PORTRAIT) {
            switch (mode) {
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
                            .remove(addNewRecipeFragment)
                            .commit();
                    getSupportFragmentManager().executePendingTransactions();
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
        } else {
            switch (mode) {
                case USER_RECIPE_LIST:
                case USER_RECIPE_DETAILS:
                    recipeList.setVisibility(View.VISIBLE);
                    recipeDetails.setVisibility(View.VISIBLE);
                    recipeFullLandScape.setVisibility(View.GONE);
                    dividerLandscape.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainActivityListLayout, userRecipesListFragment, USER_RECIPE_LIST_FRAG)
                            .commit();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainActivityDetailLayout, userRecipeDetailsFragment, USER_RECIPE_DETAIL_FRAG)
                            .commit();
                    break;
                case ADD_RECIPE:
                    recipeDetails.setVisibility(View.GONE);
                    recipeList.setVisibility(View.GONE);
                    recipeFullLandScape.setVisibility(View.VISIBLE);
                    dividerLandscape.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction()
                            .remove(addNewRecipeFragment)
                            .commit();
                    getSupportFragmentManager().executePendingTransactions();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainActivityFullLayout, addNewRecipeFragment, ADD_RECIPE_FRAG)
                            .commit();
                    break;
                default: //case API_RECIPE_LIST: and API_RECIPE_DETAILS:
                    recipeList.setVisibility(View.VISIBLE);
                    recipeDetails.setVisibility(View.VISIBLE);
                    recipeFullLandScape.setVisibility(View.GONE);
                    dividerLandscape.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainActivityListLayout, recipeListApiFragment, RECIPE_API_LIST_FRAG)
                            .commit();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mainActivityDetailLayout, recipeListApiDetailsFragment, RECIPE_API_DETAIL_FRAG)
                            .commit();
                    break;
            }
        }
    }
}