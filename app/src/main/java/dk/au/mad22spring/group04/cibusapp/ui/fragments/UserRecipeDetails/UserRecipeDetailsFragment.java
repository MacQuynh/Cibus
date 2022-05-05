package dk.au.mad22spring.group04.cibusapp.ui.fragments.UserRecipeDetails;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.R;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentWithMeasurementsAndIngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionDTO;
import dk.au.mad22spring.group04.cibusapp.ui.interfaces.UserRecipeSelectorInterface;

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
            txtInstructions;
    private Button btnDelete, btnSave, btnShare;
    private RatingBar ratingBar;
    private ImageView imgRecipe;

    private UserRecipeDetailsViewModel detailsViewModel;
    private RecipeWithSectionsAndInstructionsDTO recipe;
    private static int recipeIndex = 0;
    private String ingredientMeasurementText = "";


    private UserRecipeSelectorInterface recipeSelectorInterface;

    public static UserRecipeDetailsFragment newInstance() {
        return new UserRecipeDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_recipe_details_fragment, container, false);

        setUIWidgets(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        detailsViewModel = new ViewModelProvider(getActivity()).get(UserRecipeDetailsViewModel.class);
        setSelectedRecipe(recipeIndex);

        detailsViewModel.getComponent().observe(getViewLifecycleOwner(), new Observer<List<ComponentWithMeasurementsAndIngredientDTO>>() {
            @Override
            public void onChanged(List<ComponentWithMeasurementsAndIngredientDTO> componentWithMeasurementsAndIngredientDTOS) {
                ingredientMeasurementText = "";
                for (ComponentWithMeasurementsAndIngredientDTO component :
                        componentWithMeasurementsAndIngredientDTOS) {
                    if(component.measurements != null){

                        ingredientMeasurementText += component.measurements.get(0).getQuantity() + " ";
                    }
                    if(component.ingredient != null){
                        ingredientMeasurementText += component.ingredient.getName() + "\n";

                    }
                }
                txtIngredients.setText(ingredientMeasurementText);
            }
        });

        listeners();
    }

    //Master Detail inspiration: Demo from lecture "FragmentsArnieMovies"
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            recipeSelectorInterface = (UserRecipeSelectorInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement UserRecipe interface");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUIData();
    }

    public void setSelectedRecipe(int index){
        recipeIndex = index;

        if(detailsViewModel != null){
            setUIData();
        }
    }

    private void setUIWidgets(View view){
        imgRecipe = view.findViewById(R.id.userRecipeDetail_img);
        
        txtRecipeName = view.findViewById(R.id.userRecipeDetail_NameHeader);
        txtNumOfServings = view.findViewById(R.id.userRecipeDetail_servings);
        txtCountry = view.findViewById(R.id.userRecipeDetail_Country);
        txtDescription = view.findViewById(R.id.userRecipeDetail_Description);
        txtPrepTime = view.findViewById(R.id.userRecipeDetail_prepTime);
        txtCookTime = view.findViewById(R.id.userRecipeDetail_cookTime);
        txtTotalTime = view.findViewById(R.id.userRecipeDetail_totalTime);
        txtIngredients = view.findViewById(R.id.userRecipeDetail_Ingredients);
        txtInstructions = view.findViewById(R.id.userRecipeDetail_Instructions);

        ratingBar = view.findViewById(R.id.userRecipeDetail_ratingBar);

        btnDelete = view.findViewById(R.id.userRecipeDetail_btnDelete);
        btnSave = view.findViewById(R.id.userRecipeDetail_btnSave);
        btnShare = view.findViewById(R.id.userRecipeDetail_btnShare);

    }

    private void listeners(){
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.setUserRatings(v);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSave();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShare();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDelete();
            }
        });
    }

    private void onDelete() {
        detailsViewModel.deleteFullRecipe(detailsViewModel.recipeWithSectionsAndInstructionsDTO);
        recipeSelectorInterface.onBackFromUserRecipeDetails();
        setSelectedRecipe(recipeIndex-1);
    }

    private void onShare() {
        //TODO
    }

    private void onSave() {
        detailsViewModel.updateFullRecipe(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe);
        recipeSelectorInterface.onBackFromUserRecipeDetails();
    }

    private void setUIData(){
        RecipeWithSectionsAndInstructionsDTO fetchedRecipe = detailsViewModel.getFullRecipeByIndex(recipeIndex);

        if(detailsViewModel.recipeWithSectionsAndInstructionsDTO == null){
            detailsViewModel.recipeWithSectionsAndInstructionsDTO = fetchedRecipe;
            detailsViewModel.setComponent();
        } else if (detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.idRecipe != fetchedRecipe.recipe.idRecipe){
            detailsViewModel.recipeWithSectionsAndInstructionsDTO = fetchedRecipe;
            detailsViewModel.setComponent();
        }


        if(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getApiId() == null){
            //get color resource: https://www.codegrepper.com/code-examples/java/get+color+resource+android
            btnShare.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.disabledGrey));
            btnShare.setClickable(false);
        }
        
        if(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getThumbnailUrl().equals("") 
                || detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getThumbnailUrl() == null){
            imgRecipe.setImageResource(R.drawable.default_recipe_image);
        } else {
            Glide.with(imgRecipe.getContext()).load(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getThumbnailUrl()).into(imgRecipe);
        }

        txtRecipeName.setText(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getName());
        txtNumOfServings.setText(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getNumServings() + "");
        txtCountry.setText(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getCountry());
        txtDescription.setText(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getDescription());
        txtPrepTime.setText(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getPrepTimeMinutes() + " min");
        txtCookTime.setText(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getCookTimeMinutes() + " min");
        txtTotalTime.setText(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getTotalTimeMinutes() + " min");

        ratingBar.setRating(detailsViewModel.recipeWithSectionsAndInstructionsDTO.recipe.getUserRatings());

        String instructionText = "";
        if(detailsViewModel.recipeWithSectionsAndInstructionsDTO.instructions.size() > 0){
            for (int i = 0; i < detailsViewModel.recipeWithSectionsAndInstructionsDTO.instructions.size(); i++) {
                if(!detailsViewModel.recipeWithSectionsAndInstructionsDTO.instructions.get(i).getDisplayText().equals("")) {
                    instructionText += i + ": " + detailsViewModel.recipeWithSectionsAndInstructionsDTO.instructions.get(i).getDisplayText() + "\n";
                }
            }
            txtInstructions.setText(instructionText);
        }
        if (instructionText.equals("")) {
            txtInstructions.setText(String.format(getResources().getString(R.string.instructions_empty)));
        }

    }
}