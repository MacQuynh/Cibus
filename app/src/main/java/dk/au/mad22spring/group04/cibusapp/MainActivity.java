package dk.au.mad22spring.group04.cibusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;

import dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipesList.UserRecipesListFragment;

import dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListApi.RecipeListApiFragment;

public class MainActivity extends AppCompatActivity {


    // Fragments:
    private UserRecipesListFragment userRecipesListFragment;

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

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragConViewUserRecipesList, UserRecipesListFragment.newInstance())
                    .commitNow();

        }
    }
}