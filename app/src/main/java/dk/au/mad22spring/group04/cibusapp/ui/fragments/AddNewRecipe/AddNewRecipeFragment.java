package dk.au.mad22spring.group04.cibusapp.ui.fragments.AddNewRecipe;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import dk.au.mad22spring.group04.cibusapp.R;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.InstructionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;

//Code for using scrollView adapted from https://stackoverflow.com/questions/6674341/how-to-use-scrollview-in-android

public class AddNewRecipeFragment extends Fragment {

    private AddNewRecipeViewModel addNewRecipeViewModel;
    private EditText recipeNameEditText, measure1EditText,measure2EditText,measure3EditText,measure4EditText,measure5EditText;
    private EditText ingredient1EditText, ingredient2EditText, ingredient3EditText, ingredient4EditText, ingredient5EditText, instructionsEditText;
    private Button addBtn;

    public static AddNewRecipeFragment newInstance() {
        return new AddNewRecipeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_recipe_fragment, container, false);
        setupUIWidgets(view);

        return view;
    }

    private void setupUIWidgets(View view) {
        recipeNameEditText = view.findViewById(R.id.addNew_recipeName_editText);
        measure1EditText = view.findViewById(R.id.addNew_measure1_editText);
        measure2EditText = view.findViewById(R.id.addNew_measure2_editText);
        measure3EditText = view.findViewById(R.id.addNew_measure3_editText);
        measure4EditText = view.findViewById(R.id.addNew_measure4_editText);
        measure5EditText = view.findViewById(R.id.addNew_measure5_editText);
        ingredient1EditText = view.findViewById(R.id.addNew_ingredient1_editText);
        ingredient2EditText = view.findViewById(R.id.addNew_ingredient2_editText);
        ingredient3EditText = view.findViewById(R.id.addNew_ingredient3_editText);
        ingredient4EditText = view.findViewById(R.id.addNew_ingredient4_editText);
        ingredient5EditText = view.findViewById(R.id.addNew_ingredient5_editText);
        addBtn = view.findViewById(R.id.addNew_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRecipeToLibrary();
            }
        });
    }

    private void addRecipeToLibrary() {
        String recipeName = recipeNameEditText.getText().toString();
        String measure1 = measure1EditText.getText().toString();
        String measure2 = measure2EditText.getText().toString();
        String measure3 = measure3EditText.getText().toString();
        String measure4 = measure4EditText.getText().toString();
        String measure5 = measure5EditText.getText().toString();
        String ingredient1 = ingredient1EditText.getText().toString();
        String ingredient2 = ingredient2EditText.getText().toString();
        String ingredient3 = ingredient3EditText.getText().toString();
        String ingredient4 = ingredient4EditText.getText().toString();
        String ingredient5 = ingredient5EditText.getText().toString();
        String instructions = instructionsEditText.getText().toString();

        RecipeDTO recipeDTO = new RecipeDTO(recipeName,"","",75, 30,45, "", 4, "", 	1619608605, 1651144605,0.0);
        InstructionDTO instructionDTO = new InstructionDTO( recipeDTO)

        addNewRecipeViewModel.addNewRecipe(recipeName, measure1, measure2, measure3, measure4, measure5, ingredient1, ingredient2, ingredient3, ingredient4,ingredient5,instructions );

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addNewRecipeViewModel = new ViewModelProvider(this).get(AddNewRecipeViewModel.class);
    }



}