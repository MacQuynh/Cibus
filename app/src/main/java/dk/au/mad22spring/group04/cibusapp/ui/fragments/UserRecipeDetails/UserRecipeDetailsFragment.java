package dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipeDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.R;
import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionWithComponentsDTO;
import dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipesList.UserRecipesListFragment;

public class UserRecipeDetailsFragment extends Fragment {

    //UI Widgets
    private TextView txtRecipeName,
            txtNumOfServings,
            txtCountry,
            txtDescription,
            txtPrepTime,
            txtCookTime,
            txtTotalTime,
            txtIngredients,
            txtInstructions,
            txtRating;
    private Button btnDelete, btnSave, btnShare;
    private SeekBar skbRating;


    private UserRecipeDetailsViewModel detailsViewModel;
    private static String recipeName;

    public static UserRecipeDetailsFragment newInstance() {
        return new UserRecipeDetailsFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_recipe_details_fragment, container, false);

        //Pass arguments inspiration: https://stackoverflow.com/questions/12739909/send-data-from-activity-to-fragment-in-android
        recipeName = getArguments().getString(Constants.RECIPE_NAME);
        setUIWidgets(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailsViewModel = new ViewModelProvider(this).get(UserRecipeDetailsViewModel.class);

        detailsViewModel.getFullRecipeByName(recipeName).observe(getViewLifecycleOwner(), new Observer<RecipeWithSectionsAndInstructionsDTO>() {
            @Override
            public void onChanged(RecipeWithSectionsAndInstructionsDTO recipeWithSectionsAndInstructionsDTO) {
                if(detailsViewModel.recipeWithSectionsAndInstructionsDTO == null || !recipeName.equals(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getName())){
                    detailsViewModel.recipeWithSectionsAndInstructionsDTO = recipeWithSectionsAndInstructionsDTO;
                }
                setUIData();
            }
        });
    }

    private void setUIWidgets(View view){
        txtRecipeName = view.findViewById(R.id.userRecipeDetail_NameHeader);
        txtNumOfServings = view.findViewById(R.id.userRecipeDetail_servings);
        txtCountry = view.findViewById(R.id.userRecipeDetail_Country);
        txtDescription = view.findViewById(R.id.userRecipeDetail_Description);
        txtPrepTime = view.findViewById(R.id.userRecipeDetail_prepTime);
        txtCookTime = view.findViewById(R.id.userRecipeDetail_cookTime);
        txtTotalTime = view.findViewById(R.id.userRecipeDetail_totalTime);
        txtIngredients = view.findViewById(R.id.userRecipeDetail_Ingredients);
        txtInstructions = view.findViewById(R.id.userRecipeDetail_Instructions);
        txtRating = view.findViewById(R.id.userRecipeDetail_Rating);

        skbRating = view.findViewById(R.id.userRecipeDetail_skbRating);

        btnDelete = view.findViewById(R.id.userRecipeDetail_btnDelete);
        btnSave = view.findViewById(R.id.userRecipeDetail_btnSave);
        btnShare = view.findViewById(R.id.userRecipeDetail_btnShare);
    }

    private void setUIData(){
        txtRecipeName.setText(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getName());
        txtNumOfServings.setText(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getNumServings() + "");
        txtCountry.setText(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getCountry());
        txtDescription.setText(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getDescription());
        txtPrepTime.setText(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getPrepTimeMinutes() + " min");
        txtCookTime.setText(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getCookTimeMinutes() + " min");
        txtTotalTime.setText(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getTotalTimeMinutes() + " min");
        txtRating.setText(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getUserRatings() + "");

        String instructionText = "";
        for (int i = 0; i < detailsViewModel.recipeWithSectionsAndInstructionsDTO.instructions.size(); i++) {
            instructionText += i + ": " + detailsViewModel.recipeWithSectionsAndInstructionsDTO.instructions.get(i).getDisplayText() + "\n";
        }
        txtInstructions.setText(instructionText);

        //getIngredients();
    }

    //Fejler med timing...
    private void getIngredients(){
        String ingredientText = "";
        for (SectionDTO section:
             detailsViewModel.recipeWithSectionsAndInstructionsDTO.sections) {
            int id = section.idSection;
            SectionWithComponentsDTO sectio = detailsViewModel.getSectionWithComponent(id);
            List<ComponentDTO> componentDTOS = detailsViewModel.sectionWithComponentsDTO.components;
            ingredientText += componentDTOS.get(0).getRawText();
        };
        txtIngredients.setText(ingredientText);
    }


}