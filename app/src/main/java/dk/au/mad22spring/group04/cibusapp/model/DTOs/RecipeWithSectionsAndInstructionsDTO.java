package dk.au.mad22spring.group04.cibusapp.model.DTOs;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

// Entity relation: https://developer.android.com/training/data-storage/room/relationships
// Multiple relations: https://stackoverflow.com/questions/59417370/room-how-to-implement-several-one-to-one-relations
public class RecipeWithSectionsAndInstructionsDTO {
    @Embedded
    public RecipeDTO recipe;
    @Relation(
            parentColumn = "idRecipe",
            entityColumn = "recipeCreatorIdForSection"
    )
    public List<SectionDTO> sections;
    @Relation(
            parentColumn = "idRecipe",
            entityColumn = "recipeCreatorId"
    )
    public List<InstructionDTO> instructions;
}
