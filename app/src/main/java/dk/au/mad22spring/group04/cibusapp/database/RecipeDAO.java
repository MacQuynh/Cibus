package dk.au.mad22spring.group04.cibusapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.google.common.util.concurrent.ListenableFuture;

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
import dk.au.mad22spring.group04.cibusapp.model.Section;

@Dao
public interface RecipeDAO {
    @Query("SELECT * FROM RecipeDTO")
    LiveData<List<RecipeDTO>> getAllRecipes();

    // Entity relation: https://developer.android.com/training/data-storage/room/relationships
    @Transaction
    @Query("SELECT * FROM RECIPEDTO")
    public LiveData<List<RecipeWithSectionsAndInstructionsDTO>> getRecipeWithSectionsAndInstructions();

    @Transaction
    @Query("SELECT * FROM RECIPEDTO WHERE name like :searchText AND userId like :userId")
    public ListenableFuture<List<RecipeWithSectionsAndInstructionsDTO>> getRecipesWithSectionsAndInstructionsFromSearch(String searchText, String userId);

    @Transaction
    @Query("SELECT * FROM RECIPEDTO WHERE idRecipe like :id")
    public ListenableFuture<RecipeWithSectionsAndInstructionsDTO> getFullRecipeById(long id);

    @Transaction
    @Query("SELECT * FROM SectionDTO")
    public List<SectionWithComponentsDTO> getSectionWithComponents();

    @Transaction
    @Query("SELECT * FROM SectionDTO WHERE idSection like :id")
    public ListenableFuture<SectionWithComponentsDTO> getSectionWithComponentsById(int id);

    @Transaction
    @Query("SELECT * FROM ComponentDTO WHERE sectionCreatorId like :id")
    public List<ComponentDTO> getComponentsFromSectionId(int id);

    @Transaction
    @Query("SELECT * FROM IngredientDTO WHERE componentCreatorIdForIngredient like :id")
    public List<IngredientDTO> getIngredientFromComponentId(int id);

    @Transaction
    @Query("SELECT * FROM MeasurementDTO WHERE componentCreatorId like :id")
    public List<MeasurementDTO> getMeasurementsFromComponentId(int id);

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

    @Update
    void updateRecipe(RecipeDTO recipeDTO);

    @Delete
    void deleteRecipe(RecipeDTO recipeDTO);

    @Delete
    void deleteSection(SectionDTO sectionDTO);

    @Delete
    void deleteInstruction(InstructionDTO instructionDTO);

    @Delete
    void deleteComponent(ComponentDTO componentDTO);

    @Delete
    void deleteMeasurement(MeasurementDTO measurementDTO);

    @Delete
    void deleteIngredient(IngredientDTO ingredientDTO);


}
