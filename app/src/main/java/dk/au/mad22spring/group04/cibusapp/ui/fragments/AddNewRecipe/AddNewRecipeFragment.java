package dk.au.mad22spring.group04.cibusapp.ui.fragments.AddNewRecipe;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.LinearGradient;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import dk.au.mad22spring.group04.cibusapp.R;
import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.IngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.InstructionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.MeasurementDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionDTO;

//Code for using scrollView adapted from https://stackoverflow.com/questions/6674341/how-to-use-scrollview-in-android

public class AddNewRecipeFragment extends Fragment {

    private AddNewRecipeViewModel addNewRecipeViewModel;
    private EditText recipeNameEditText, measure1EditText,measure2EditText,measure3EditText,measure4EditText,measure5EditText;
    private EditText ingredient1EditText, ingredient2EditText, ingredient3EditText, ingredient4EditText, ingredient5EditText, instructionsEditText;
    private EditText totalTimeEditText, cookTimeEditText, prepTimeEditText, numberOfServingsEditText;
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
        numberOfServingsEditText = view.findViewById(R.id.addNew_numberOf_editText);
        totalTimeEditText = view.findViewById(R.id.addNew_total_editText);
        cookTimeEditText = view.findViewById(R.id.addNew_cookTime_editText);
        prepTimeEditText = view.findViewById(R.id.addNew_prepTime_editText);
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
        instructionsEditText = view.findViewById(R.id.addNew_instructions_editText);
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
        Integer numberOfServings = Integer.valueOf(numberOfServingsEditText.getText().toString());
        Float cookTime = Float.valueOf(cookTimeEditText.getText().toString());
        Float prepTime = Float.valueOf(prepTimeEditText.getText().toString());
        Float totalTime = cookTime+prepTime;
        totalTimeEditText.setText("" + totalTime);
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

        RecipeDTO recipeDTO = new RecipeDTO(null, recipeName,"", totalTime, cookTime, prepTime, "", numberOfServings, "",1619608605, 1651144605,0, Constants.USER_ID);

        InstructionDTO instructionDTO = new InstructionDTO(instructions, 00,00, 1);

        SectionDTO sectionDTO = new SectionDTO("",1 );

        ComponentDTO componentDTO = new ComponentDTO(1, "");

        MeasurementDTO measurementDTO1 = new MeasurementDTO(measure1);
        MeasurementDTO measurementDTO2 = new MeasurementDTO(measure2);
        MeasurementDTO measurementDTO3 = new MeasurementDTO(measure3);
        MeasurementDTO measurementDTO4 = new MeasurementDTO(measure4);
        MeasurementDTO measurementDTO5 = new MeasurementDTO(measure5);

        ArrayList<MeasurementDTO> listOfMeasures = new ArrayList<MeasurementDTO>();
        listOfMeasures.add(measurementDTO1);
        listOfMeasures.add(measurementDTO2);
        listOfMeasures.add(measurementDTO3);
        listOfMeasures.add(measurementDTO4);
        listOfMeasures.add(measurementDTO5);

        IngredientDTO ingredientDTO1 = new IngredientDTO(ingredient1,"", "");
        IngredientDTO ingredientDTO2 = new IngredientDTO(ingredient2,"", "");
        IngredientDTO ingredientDTO3 = new IngredientDTO(ingredient3,"", "");
        IngredientDTO ingredientDTO4 = new IngredientDTO(ingredient4,"", "");
        IngredientDTO ingredientDTO5 = new IngredientDTO(ingredient5,"", "");

        ArrayList<IngredientDTO> listOfIngredients = new ArrayList<IngredientDTO>();
        listOfIngredients.add(ingredientDTO1);
        listOfIngredients.add(ingredientDTO2);
        listOfIngredients.add(ingredientDTO3);
        listOfIngredients.add(ingredientDTO4);
        listOfIngredients.add(ingredientDTO5);

        addNewRecipeViewModel.addNewRecipe(recipeDTO,instructionDTO,sectionDTO,componentDTO,listOfMeasures,listOfIngredients);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addNewRecipeViewModel = new ViewModelProvider(this).get(AddNewRecipeViewModel.class);
    }



}