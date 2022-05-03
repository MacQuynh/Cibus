package dk.au.mad22spring.group04.cibusapp.model.DTOs;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Entity relation: https://developer.android.com/training/data-storage/room/relationships
@Entity
public class UnitDTO {

    @PrimaryKey(autoGenerate = true)
    public int idUnit;

    public long measurementCreatorId;

    private String name;

    private String displayPlural;

    private String displaySingular;

    public UnitDTO(String name, String displayPlural, String displaySingular) {
        this.name = name;
        this.displayPlural = displayPlural;
        this.displaySingular = displaySingular;
    }

    public int getIdUnit() {
        return idUnit;
    }

    public void setIdUnit(int idUnit) {
        this.idUnit = idUnit;
    }

    public long getMeasurementCreatorId() {
        return measurementCreatorId;
    }

    public void setMeasurementCreatorId(long measurementCreatorId) {
        this.measurementCreatorId = measurementCreatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayPlural() {
        return displayPlural;
    }

    public void setDisplayPlural(String displayPlural) {
        this.displayPlural = displayPlural;
    }

    public String getDisplaySingular() {
        return displaySingular;
    }

    public void setDisplaySingular(String displaySingular) {
        this.displaySingular = displaySingular;
    }
}
