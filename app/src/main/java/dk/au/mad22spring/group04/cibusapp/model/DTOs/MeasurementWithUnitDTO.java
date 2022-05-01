package dk.au.mad22spring.group04.cibusapp.model.DTOs;

import androidx.room.Embedded;
import androidx.room.Relation;

public class MeasurementWithUnitDTO {
    @Embedded
    public MeasurementDTO measurement;
    @Relation(
            parentColumn = "idMeasurement",
            entityColumn = "measurementCreatorId"
    )
    public UnitDTO unit;
}
