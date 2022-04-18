package dk.au.mad22spring.group04.cibusapp.model.DTOs;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class InstructionDTO implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int idInstruction;

    public long recipeCreatorId;

    private String displayText;

    private Integer startTime;

    private Integer endTime;

    private Integer order;

    public InstructionDTO(String displayText, Integer startTime, Integer endTime, Integer order) {
        this.displayText = displayText;
        this.startTime = startTime;
        this.endTime = endTime;
        this.order = order;
    }

    public int getIdInstruction() {
        return idInstruction;
    }

    public void setIdInstruction(int idInstruction) {
        this.idInstruction = idInstruction;
    }

    public long getRecipeId() {
        return recipeCreatorId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeCreatorId = recipeId;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
