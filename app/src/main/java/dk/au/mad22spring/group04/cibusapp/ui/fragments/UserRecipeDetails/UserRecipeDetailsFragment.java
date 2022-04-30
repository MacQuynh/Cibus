package dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipeDetails;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.R;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;
import dk.au.mad22spring.group04.cibusapp.ui.adapters.UserRecipesListAdapter;
import dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipesList.UserRecipesListViewModel;

public class UserRecipeDetailsFragment extends Fragment {

    private UserRecipeDetailsViewModel mViewModel;
    private String recipeName;

    public static UserRecipeDetailsFragment newInstance() {
        return new UserRecipeDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_recipe_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserRecipeDetailsViewModel.class);

        mViewModel.getFullRecipeByName(recipeName).observe(getViewLifecycleOwner(), new Observer<RecipeWithSectionsAndInstructionsDTO>() {
            @Override
            public void onChanged(RecipeWithSectionsAndInstructionsDTO recipeWithSectionsAndInstructionsDTO) {
                if(mViewModel.recipeWithSectionsAndInstructionsDTO == null || !recipeName.equals(mViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getName())){
                    mViewModel.recipeWithSectionsAndInstructionsDTO = recipeWithSectionsAndInstructionsDTO;
                }

            }
        });


    }


}