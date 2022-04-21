package dk.au.mad22spring.group04.cibusapp.model.DTOs;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MeasurementDTO {

    @PrimaryKey(autoGenerate = true)
    public int idMeasurement;

    public long componentCreatorId;

    private String quantity;

    public MeasurementDTO(long componentCreatorId, String quantity) {
        this.componentCreatorId = componentCreatorId;
        this.quantity = quantity;
    }

    public int getIdMeasurement() {
        return idMeasurement;
    }

    public void setIdMeasurement(int idMeasurement) {
        this.idMeasurement = idMeasurement;
    }

    public long getComponentCreatorId() {
        return componentCreatorId;
    }

    public void setComponentCreatorId(long componentCreatorId) {
        this.componentCreatorId = componentCreatorId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
