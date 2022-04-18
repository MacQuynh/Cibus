package dk.au.mad22spring.group04.cibusapp.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class SectionWithComponentsDTO {
    @Embedded
    public SectionDTO section;
    @Relation(
            parentColumn = "idSection",
            entityColumn = "sectionCreatorId"
    )
    public List<ComponentDTO> components;
}
