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
import java.util.List;

import dk.au.mad22spring.group04.cibusapp.R;
import dk.au.mad22spring.group04.cibusapp.helpers.Constants;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.IngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.InstructionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.MeasurementDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.UnitDTO;

//Code for using scrollView adapted from https://stackoverflow.com/questions/6674341/how-to-use-scrollview-in-android

public class AddNewRecipeFragment extends Fragment {

    private static final String TAG = "ADD_NEW_RECIPE_FRAGMENT";
    private AddNewRecipeViewModel addNewRecipeViewModel;
    private EditText recipeNameEditText, measure1EditText,measure2EditText,measure3EditText,measure4EditText,measure5EditText;
    private EditText unit1EditText,unit2EditText,unit3EditText,unit4EditText,unit5EditText;
    private EditText ingredient1EditText, ingredient2EditText, ingredient3EditText, ingredient4EditText, ingredient5EditText, instructionsEditText;
    private EditText totalTimeEditText, cookTimeEditText, prepTimeEditText, numberOfServingsEditText;
    private EditText measureDynamic, unitDynamic,ingredientDynamic;
    private Button addBtn, extraFieldBtn;
    int numberOfEditTexts = 1 ;
    List<EditText>allDynamicMeasures = new ArrayList<EditText>();
    List<EditText>allDynamicUnits = new ArrayList<EditText>();
    List<EditText>allDynamicIngredients = new ArrayList<EditText>();


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
        unit1EditText = view.findViewById(R.id.addNew_unit1_editText);
        unit2EditText = view.findViewById(R.id.addNew_unit2_editText);
        unit3EditText = view.findViewById(R.id.addNew_unit3_editText);
        unit4EditText = view.findViewById(R.id.addNew_unit4_editText);
        unit5EditText = view.findViewById(R.id.addNew_unit5_editText);
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
                addNewRecipeToLibrary();
            }
        });
        extraFieldBtn = view.findViewById(R.id.addNew_addField_btn_);
        extraFieldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFieldsDynamically();
                numberOfEditTexts ++;
            }
        });
    }

    // Code adapted from https://stackoverflow.com/questions/37764422/add-edittext-dynamically-with-if-possible-string-id-into-a-fragment
    // Code adapted from https://stackoverflow.com/questions/41865416/android-dynamically-or-programmatically-add-two-edittext-in-one-line-and-make-t
    private void addFieldsDynamically() {
        Context context = getActivity();
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        measureDynamic = new EditText(context);
        allDynamicMeasures.add(measureDynamic);
        measureDynamic.setHint(R.string.measurements_editText);
        measureDynamic.setTextSize(16);
        measureDynamic.setId(numberOfEditTexts);
        measureDynamic.setLayoutParams(new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT, 0.6f));
        linearLayout.addView(measureDynamic);

        unitDynamic = new EditText(context);
        allDynamicUnits.add(unitDynamic);
        unitDynamic.setHint(R.string.unit_editText);
        unitDynamic.setTextSize(16);
        unitDynamic.setId(numberOfEditTexts);
        unitDynamic.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.7f));
        linearLayout.addView(unitDynamic);

        ingredientDynamic = new EditText(context);
        allDynamicIngredients.add(ingredientDynamic);
        ingredientDynamic.setHint(R.string.ingredient_editText);
        ingredientDynamic.setTextSize(16);
        ingredientDynamic.setId(numberOfEditTexts);
        ingredientDynamic.setLayoutParams(new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT, 1.5f));
        linearLayout.addView(ingredientDynamic);

       LinearLayout layout = (LinearLayout)getView().findViewById(R.id.addNew_linearLayout);
        layout.addView(linearLayout);

    }

    private void addNewRecipeToLibrary() {
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
        Double cookTime = 0.0;
        try{
            cookTime = Double.valueOf(cookTimeString);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        String prepTimeString = prepTimeEditText.getText().toString();
        Double prepTime = 0.0;
        try{
            prepTime = Double.valueOf(prepTimeString);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        String totalTimeString = totalTimeEditText.getText().toString();
        Double totalTime = 0.0;
        try{
            totalTime = Double.valueOf(totalTimeString);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        String instructions = instructionsEditText.getText().toString();

        //List of measurements
        ArrayList<MeasurementDTO> listOfMeasures = new ArrayList<MeasurementDTO>();
        listOfMeasures.add(new MeasurementDTO(measure1EditText.getText().toString()));
        listOfMeasures.add(new MeasurementDTO(measure2EditText.getText().toString()));
        listOfMeasures.add(new MeasurementDTO(measure3EditText.getText().toString()));
        listOfMeasures.add(new MeasurementDTO(measure4EditText.getText().toString()));
        listOfMeasures.add(new MeasurementDTO(measure5EditText.getText().toString()));

        for (int i = 0; i < allDynamicMeasures.size(); i++){
            listOfMeasures.add(new MeasurementDTO(allDynamicMeasures.get(i).getText().toString()));
        }

        //List of units
        ArrayList<UnitDTO> listOfUnits = new ArrayList<UnitDTO>();
        listOfUnits.add(new UnitDTO(unit1EditText.getText().toString(),"",""));
        listOfUnits.add(new UnitDTO(unit2EditText.getText().toString(),"",""));
        listOfUnits.add(new UnitDTO(unit3EditText.getText().toString(),"",""));
        listOfUnits.add(new UnitDTO(unit4EditText.getText().toString(),"",""));
        listOfUnits.add(new UnitDTO(unit5EditText.getText().toString(),"",""));

        for (int i = 0; i < allDynamicUnits.size(); i++){
            listOfUnits.add(new UnitDTO(allDynamicUnits.get(i).getText().toString(),"",""));
        }

        //List of ingredients
        ArrayList<IngredientDTO> listOfIngredients = new ArrayList<IngredientDTO>();
        listOfIngredients.add(new IngredientDTO(ingredient1EditText.getText().toString(),"",""));
        listOfIngredients.add(new IngredientDTO(ingredient2EditText.getText().toString(),"",""));
        listOfIngredients.add(new IngredientDTO(ingredient3EditText.getText().toString(),"",""));
        listOfIngredients.add(new IngredientDTO(ingredient4EditText.getText().toString(),"",""));
        listOfIngredients.add(new IngredientDTO(ingredient5EditText.getText().toString(),"",""));

        for (int i = 0; i < allDynamicIngredients.size(); i++){
            listOfIngredients.add(new IngredientDTO(allDynamicIngredients.get(i).getText().toString(),"", ""));
        }

        RecipeDTO recipeDTO = new RecipeDTO(null, recipeName,"", totalTime, cookTime, prepTime, "", numberOfServings, "",1619608605, 1651144605,0, Constants.USER_ID);

        InstructionDTO instructionDTO = new InstructionDTO(instructions, 00,00, 1);

        SectionDTO sectionDTO = new SectionDTO("",1 );

        addNewRecipeViewModel.addNewRecipeToLibrary(recipeDTO,instructionDTO,sectionDTO,listOfMeasures,listOfUnits,listOfIngredients);

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
        unit1EditText.setText("");
        unit2EditText.setText("");
        unit3EditText.setText("");
        unit4EditText.setText("");
        unit5EditText.setText("");
        ingredient1EditText.setText("");
        ingredient2EditText.setText("");
        ingredient3EditText.setText("");
        ingredient4EditText.setText("");
        ingredient5EditText.setText("");
        instructionsEditText.setText("");


        for (int i = 0; i < allDynamicMeasures.size(); i++){
            allDynamicMeasures.get(i).getText().clear();
        }

        for (int i = 0; i < allDynamicUnits.size(); i++){
            allDynamicUnits.get(i).getText().clear();
        }

        for (int i = 0; i < allDynamicIngredients.size(); i++){
            allDynamicIngredients.get(i).getText().clear();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addNewRecipeViewModel = new ViewModelProvider(this).get(AddNewRecipeViewModel.class);
    }

}