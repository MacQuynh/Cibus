package dk.au.mad22spring.group04.cibusapp.model.DTOs;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class RecipeDTO {
    @PrimaryKey(autoGenerate = true)
    public int idRecipe;

    private Integer apiId = null;

    private String name;

    private String thumbnailUrl;

    private Double totalTimeMinutes;

    private Double cookTimeMinutes;

    private Double prepTimeMinutes;

    private String country;

    private Integer numServings;

    private String description;

    private Integer createdAtUnix;

    private Integer updatedAtUnix;

    private float userRatings;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    private String userID;

    public RecipeDTO(Integer apiId,
                     String name,
                     String thumbnailUrl,
                     Double totalTimeMinutes,
                     Double cookTimeMinutes,
                     Double prepTimeMinutes,
                     String country,
                     Integer numServings,
                     String description,
                     Integer createdAtUnix,
                     Integer updatedAtUnix,
                     float userRatings,
                     String userID) {
        this.apiId = apiId;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.totalTimeMinutes = totalTimeMinutes;
        this.cookTimeMinutes = cookTimeMinutes;
        this.prepTimeMinutes = prepTimeMinutes;
        this.country = country;
        this.numServings = numServings;
        this.description = description;
        this.createdAtUnix = createdAtUnix;
        this.updatedAtUnix = updatedAtUnix;
        this.userRatings = userRatings;
        this.userID = userID;
    }

    public Integer getApiId() {
        return apiId;
    }

    public void setApiId(Integer apiId) {
        this.apiId = apiId;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Double getTotalTimeMinutes() {
        return totalTimeMinutes;
    }

    public void setTotalTimeMinutes(Double totalTimeMinutes) {
        this.totalTimeMinutes = totalTimeMinutes;
    }

    public Double getCookTimeMinutes() {
        return cookTimeMinutes;
    }

    public void setCookTimeMinutes(Double cookTimeMinutes) {
        this.cookTimeMinutes = cookTimeMinutes;
    }

    public Double getPrepTimeMinutes() {
        return prepTimeMinutes;
    }

    public void setPrepTimeMinutes(Double prepTimeMinutes) {
        this.prepTimeMinutes = prepTimeMinutes;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getNumServings() {
        return numServings;
    }

    public void setNumServings(Integer numServings) {
        this.numServings = numServings;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCreatedAtUnix() {
        return createdAtUnix;
    }

    public void setCreatedAtUnix(Integer createdAtUnix) {
        this.createdAtUnix = createdAtUnix;
    }

    public Integer getUpdatedAtUnix() {
        return updatedAtUnix;
    }

    public void setUpdatedAtUnix(Integer updatedAtUnix) {
        this.updatedAtUnix = updatedAtUnix;
    }

    public float getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(float userRatings) {
        this.userRatings = userRatings;
    }
}
