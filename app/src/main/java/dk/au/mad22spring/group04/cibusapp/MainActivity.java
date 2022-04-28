package dk.au.mad22spring.group04.cibusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

import dk.au.mad22spring.group04.cibusapp.ui.fragments.AddNewRecipe.AddNewRecipeFragment;
import dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipesList.UserRecipesListFragment;

import dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListApi.RecipeListApiFragment;

//Navigation bar: https://www.geeksforgeeks.org/bottom-navigation-bar-in-android/
// https://material.io/components/bottom-navigation/android#using-bottom-navigation

public class MainActivity extends AppCompatActivity {

    //UI Widgets
    private NavigationBarView navigationBarView;

    // Fragments:
    private UserRecipesListFragment userRecipesListFragment;
    private RecipeListApiFragment recipeListApiFragment;

    //Containers for fragments:
    private FragmentContainerView userRecipeListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view setup:
        //userRecipeListContainer = findViewById(R.id.fragConViewUserRecipesList);
        navigationBarView = findViewById(R.id.bottomNavigationView);
        navigationBarView.setSelectedItemId(R.id.home);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainActitvityFragmentHolder, recipeListApiFragment.newInstance())
                    .commitNow();
        }

        //https://stackoverflow.com/questions/68021770/setonnavigationitemselectedlistener-deprecated
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.user:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.mainActitvityFragmentHolder, UserRecipesListFragment.newInstance())
                                .commit();
                        return true;
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.mainActitvityFragmentHolder, RecipeListApiFragment.newInstance())
                                .commit();
                        return true;
                    case R.id.add:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.mainActitvityFragmentHolder, AddNewRecipeFragment.newInstance())
                                .commit();
                        return true;
                }
                return false;
            }
        });

    }

}