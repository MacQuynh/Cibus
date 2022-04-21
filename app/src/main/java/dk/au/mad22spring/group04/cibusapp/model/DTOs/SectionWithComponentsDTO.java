package dk.au.mad22spring.group04.cibusapp.model.DTOs;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

// Entity relation: https://developer.android.com/training/data-storage/room/relationships
// Multiple relations: https://stackoverflow.com/questions/59417370/room-how-to-implement-several-one-to-one-relations
public class SectionWithComponentsDTO {
    @Embedded
    public SectionDTO section;
    @Relation(
            parentColumn = "idSection",
            entityColumn = "sectionCreatorId"
    )
    public List<ComponentDTO> components;
}
