package dk.au.mad22spring.group04.cibusapp.ui.fragments.AddNewRecipe;

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
import android.widget.EditText;
import android.widget.LinearLayout;

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

    private static final String TAG = "TAG";
    private AddNewRecipeViewModel addNewRecipeViewModel;
    private EditText recipeNameEditText, measure1EditText,measure2EditText,measure3EditText,measure4EditText,measure5EditText;
    private EditText ingredient1EditText, ingredient2EditText, ingredient3EditText, ingredient4EditText, ingredient5EditText, instructionsEditText;
    private EditText totalTimeEditText, cookTimeEditText, prepTimeEditText, numberOfServingsEditText;
    private EditText extraMeasureEditText, extraIngredientEditText;
    private Button addBtn, extraFieldBtn;

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
        extraFieldBtn = view.findViewById(R.id.addNew_addField_btn_);
        extraFieldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addFieldsDynamically();
            }
        });
    }


    //Code adapted from https://stackoverflow.com/questions/37764422/add-edittext-dynamically-with-if-possible-string-id-into-a-fragment
    private void addFieldsDynamically() {
        int numberOfEditTexts = 0 ;
        Context context = getActivity();
        extraMeasureEditText = new EditText(context);
        extraMeasureEditText.setHint(R.string.measurements_editText);
        extraIngredientEditText = new EditText(context);
        extraIngredientEditText.setHint(R.string.ingredient_editText);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        extraMeasureEditText.setLayoutParams(params);
        extraIngredientEditText.setLayoutParams(params);
        LinearLayout layout = (LinearLayout)getView().findViewById(R.id.addNew_linearLayout);
        layout.addView(extraMeasureEditText,0);
        layout.addView(extraIngredientEditText,1);
        numberOfEditTexts ++;
    }

    private void addRecipeToLibrary() {
        String recipeName = recipeNameEditText.getText().toString();
        //Code for handling NumberFormatException adapted from https://stackoverflow.com/questions/11113238/getting-a-numberformatexception-from-a-numerical-edittext-field
        String numberOfServingsString = numberOfServingsEditText.getText().toString();
        Integer numberOfServings = 0;
        try {
            numberOfServings = Integer.valueOf(numberOfServingsString);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        String cookTimeString = cookTimeEditText.getText().toString();
        Float cookTime = 0.0f;
        try{
            cookTime = Float.valueOf(cookTimeString);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        String prepTimeString = prepTimeEditText.getText().toString();
        Float prepTime = 0.0f;
        try{
            prepTime = Float.valueOf(prepTimeString);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        String totalTimeString = totalTimeEditText.getText().toString();
        Float totalTime = 0.0f;
        try{
            totalTime = Float.valueOf(totalTimeString);
        } catch (NumberFormatException e){
            e.printStackTrace();

        }

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

        ArrayList<MeasurementDTO> listOfMeasures = new ArrayList<MeasurementDTO>();
        listOfMeasures.add(new MeasurementDTO(measure1));
        listOfMeasures.add(new MeasurementDTO(measure2));
        listOfMeasures.add(new MeasurementDTO(measure3));
        listOfMeasures.add(new MeasurementDTO(measure4));
        listOfMeasures.add(new MeasurementDTO(measure5));

        ArrayList<IngredientDTO> listOfIngredients = new ArrayList<IngredientDTO>();
        listOfIngredients.add(new IngredientDTO(ingredient1,"", ""));
        listOfIngredients.add(new IngredientDTO(ingredient2,"", ""));
        listOfIngredients.add(new IngredientDTO(ingredient3,"", ""));
        listOfIngredients.add(new IngredientDTO(ingredient4,"", ""));
        listOfIngredients.add(new IngredientDTO(ingredient5,"", ""));

        addNewRecipeViewModel.addNewRecipe(recipeDTO,instructionDTO,sectionDTO,componentDTO,listOfMeasures,listOfIngredients);

        clearInputFieldsAfterRecipeIsAdded();

    }

    private void clearInputFieldsAfterRecipeIsAdded() {
        recipeNameEditText.setText("");
        numberOfServingsEditText.setText("");
        totalTimeEditText.setText("");
        cookTimeEditText.setText("");
        prepTimeEditText.setText("");
        measure1EditText.setText("");
        measure2EditText.setText("");
        measure3EditText.setText("");
        measure4EditText.setText("");
        measure5EditText.setText("");
        ingredient1EditText.setText("");
        ingredient2EditText.setText("");
        ingredient3EditText.setText("");
        ingredient4EditText.setText("");
        ingredient5EditText.setText("");
        instructionsEditText.setText("");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addNewRecipeViewModel = new ViewModelProvider(this).get(AddNewRecipeViewModel.class);
    }

}