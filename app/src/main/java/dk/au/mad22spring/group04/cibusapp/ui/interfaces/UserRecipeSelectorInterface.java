package dk.au.mad22spring.group04.cibusapp.ui.interfaces;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;

//Master Detail inspiration: Demo from lecture "FragmentsArnieMovies"
public interface UserRecipeSelectorInterface {

    public void onUserRecipeSelected(int index);
    public void onBackFromUserRecipeDetails();
    //public List<RecipeWithSectionsAndInstructionsDTO> setMovies();
}
