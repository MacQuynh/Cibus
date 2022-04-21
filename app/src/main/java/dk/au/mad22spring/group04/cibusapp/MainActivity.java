package dk.au.mad22spring.group04.cibusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipesList.UserRecipesListFragment;

import dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListApi.RecipeListApiFragment;

//Navigation bar: https://www.geeksforgeeks.org/bottom-navigation-bar-in-android/

public class MainActivity extends AppCompatActivity {

    //UI Widgets
    private BottomNavigationView bottomNavigationMenuView;

    // Fragments:
    private UserRecipesListFragment userRecipesListFragment;
    private RecipeListApiFragment recipeListApiFragment;

    //Containers for fragments:
    private FragmentContainerView userRecipeListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.recipe_list_api, RecipeListApiFragment.newInstance()).commitNow();
        }

        //view setup:
        userRecipeListContainer = findViewById(R.id.fragConViewUserRecipesList);
        bottomNavigationMenuView = findViewById(R.id.bottomNavigationView);
        bottomNavigationMenuView.setSelectedItemId(R.id.home);

        //https://stackoverflow.com/questions/67641594/bottomnavigation-view-onnavigationitemselectedlistener-is-deprecated
        bottomNavigationMenuView.setOnClickListener(item ->{
            switch (item.getId()){
                case R.id.user:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragConViewUserRecipesList, UserRecipesListFragment.newInstance())
                            .commit();
                case R.id.home:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_list_api, RecipeListApiFragment.newInstance())
                            .commit();
            }
        });

/*        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragConViewUserRecipesList, UserRecipesListFragment.newInstance())
                    .commitNow();

        }*/
    }
}