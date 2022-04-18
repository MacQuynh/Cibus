package dk.au.mad22spring.group04.cibusapp.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

// Entity relation: https://developer.android.com/training/data-storage/room/relationships
public class RecipeWithInstructionsDTO {
    @Embedded public RecipeDTO recipe;
    @Relation(
            parentColumn = "idRecipe",
            entityColumn = "recipeCretorId"
    )
    public List<InstructionDTO> instructions;
}
