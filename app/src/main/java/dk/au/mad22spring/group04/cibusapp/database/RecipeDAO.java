package dk.au.mad22spring.group04.cibusapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.ComponentWithMeasurementsAndIngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.IngredientDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.InstructionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.MeasurementDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.RecipeWithSectionsAndInstructionsDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionDTO;
import dk.au.mad22spring.group04.cibusapp.model.DTOs.SectionWithComponentsDTO;

@Dao
public interface RecipeDAO {
    @Query("SELECT * FROM RecipeDTO")
    LiveData<List<RecipeDTO>> getAllRecipes();

    // Entity relation: https://developer.android.com/training/data-storage/room/relationships
    @Transaction
    @Query("SELECT * FROM RECIPEDTO")
    public LiveData<List<RecipeWithSectionsAndInstructionsDTO>> getRecipeWithSectionsAndInstructions();

    @Transaction
    @Query("SELECT * FROM SectionDTO")
    public List<SectionWithComponentsDTO> getSectionWithComponents();

    @Transaction
    @Query("SELECT * FROM ComponentDTO")
    public List<ComponentWithMeasurementsAndIngredientDTO> getComponentWithMeasurementsAndIngredient();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addRecipe(RecipeDTO recipeDTO);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addInstruction(InstructionDTO instructionDTO);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addSection(SectionDTO sectionDTO);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addComponent(ComponentDTO componentDTO);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addIngredient(IngredientDTO ingredientDTO);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMeasurement(MeasurementDTO measurementDTO);
}
