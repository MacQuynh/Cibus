package dk.au.mad22spring.group04.cibusapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.model.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.RecipeWithSectionsAndInstructionsDTO;
import dk.au.mad22spring.group04.cibusapp.model.SectionWithComponentsDTO;

@Dao
public interface RecipeDAO {
    @Query("SELECT * FROM RecipeDTO")
    LiveData<List<RecipeDTO>> getAllRecipes();

    // Entity relation: https://developer.android.com/training/data-storage/room/relationships
    @Transaction
    @Query("SELECT * FROM RECIPEDTO")
    public List<RecipeWithSectionsAndInstructionsDTO> getRecipeWithSectionsAndInstructions();

    @Transaction
    @Query("SELECT * FROM SectionDTO")
    public List<SectionWithComponentsDTO> getSectionWithComponents();
}
