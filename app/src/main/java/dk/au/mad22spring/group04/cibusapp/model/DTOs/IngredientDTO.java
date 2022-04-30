package dk.au.mad22spring.group04.cibusapp.model.DTOs;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class IngredientDTO {
    @PrimaryKey(autoGenerate = true)
    public int idIngredient;

    public long componentCreatorIdForIngredient;

    private String name;

    private String displayPlural;

    private String displaySingular;

    public IngredientDTO(String name, String displayPlural, String displaySingular) {
        this.name = name;
        this.displayPlural = displayPlural;
        this.displaySingular = displaySingular;
    }

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public long getComponentCreatorIdForIngredient() {
        return componentCreatorIdForIngredient;
    }

    public void setComponentCreatorIdForIngredient(long componentCreatorIdForIngredient) {
        this.componentCreatorIdForIngredient = componentCreatorIdForIngredient;
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
