package dk.au.mad22spring.group04.cibusapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.model.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.RecipeWithSectionsAndInstructionsDTO;

@Dao
public interface RecipeDAO {
    @Query("SELECT * FROM RecipeDTO")
    LiveData<List<RecipeDTO>> getAllRecipes();

    @Transaction
    @Query("SELECT * FROM RECIPEDTO")
    public List<RecipeWithSectionsAndInstructionsDTO> getRecipeWithSectionsAndInstructions();
}
