package dk.au.mad22spring.group04.cibusapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class MeasurementDTO {

    @PrimaryKey(autoGenerate = true)
    public int idMeasurement;

    public long componentCreatorId;

    private String quantity;

    public MeasurementDTO(String quantity) {
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
