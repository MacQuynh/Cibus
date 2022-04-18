package dk.au.mad22spring.group04.cibusapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity
public class SectionDTO implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int idSection;

    public long recipeCreatorIdForSection;

    //private List<Component> components = null;

    private String name;

    private Integer order;

    public SectionDTO(String name, Integer order) {
        //this.components = components;
        this.name = name;
        this.order = order;
    }

/*    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
