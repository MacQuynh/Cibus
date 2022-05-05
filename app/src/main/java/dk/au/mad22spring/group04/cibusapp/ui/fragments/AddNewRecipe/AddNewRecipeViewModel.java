package dk.au.mad22spring.group04.cibusapp.ui.fragments.AddNewRecipe;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;

import dk.au.mad22spring.group04.cibusapp.model.DTOs.IngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.InstructionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.MeasurementDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.UnitDTO;
import dk.au.mad22spring.group04.cibusapp.model.repository.Repository;

public class AddNewRecipeViewModel extends AndroidViewModel {
    private final Repository repository;

    public AddNewRecipeViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepositoryInstance(application);
    }

    public void addNewRecipeToLibrary(RecipeDTO recipeDTO, InstructionDTO instructionDTO, SectionDTO sectionDTO, ArrayList<MeasurementDTO> listOfMeasurementDTO, ArrayList<UnitDTO> listOfUnitsDTO , ArrayList<IngredientDTO> listOfIngredientDTO){
        repository.addNewRecipeToLibrary(recipeDTO, instructionDTO, sectionDTO, listOfMeasurementDTO, listOfUnitsDTO, listOfIngredientDTO);
    }
}