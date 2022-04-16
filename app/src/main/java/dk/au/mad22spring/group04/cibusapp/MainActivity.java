package dk.au.mad22spring.group04.cibusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dk.au.mad22spring.group04.cibusapp.ui.fragments.RecipeListApi.RecipeListApiFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.recipe_list_api, RecipeListApiFragment.newInstance()).commitNow();
        }
    }
}