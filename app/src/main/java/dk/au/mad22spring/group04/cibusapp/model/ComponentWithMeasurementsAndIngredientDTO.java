package dk.au.mad22spring.group04.cibusapp.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;
// Entity relation: https://developer.android.com/training/data-storage/room/relationships
// Multiple relations: https://stackoverflow.com/questions/59417370/room-how-to-implement-several-one-to-one-relations
public class ComponentWithMeasurementsAndIngredientDTO {
    @Embedded
    public ComponentDTO component;
    @Relation(
            parentColumn = "idComponent",
            entityColumn = "componentCreatorId"
    )
    public List<MeasurementDTO> measurements;
    @Relation(
            parentColumn = "idComponent",
            entityColumn = "componentCreatorIdForIngredient"
    )
    public IngredientDTO ingredient;
}
