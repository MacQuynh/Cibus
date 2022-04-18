package dk.au.mad22spring.group04.cibusapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class ComponentDTO {
    @PrimaryKey(autoGenerate = true)
    public int idComponent;

    public long sectionCreatorId;

    private Integer id;

    private Integer position;

    private String rawText;

    public ComponentDTO(Integer id, Integer position, String rawText) {
        this.id = id;
        this.position = position;
        this.rawText = rawText;
    }

    public int getIdComponent() {
        return idComponent;
    }

    public void setIdComponent(int idComponent) {
        this.idComponent = idComponent;
    }

    public long getSectionCreatorId() {
        return sectionCreatorId;
    }

    public void setSectionCreatorId(long sectionCreatorId) {
        this.sectionCreatorId = sectionCreatorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }
}
