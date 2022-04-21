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

    private String name;

    private String thumbnailUrl;

    private String videoAdContent;

    private float totalTimeMinutes;

    private float cookTimeMinutes;

    private float prepTimeMinutes;

    private String country;

    private Integer numServings;

    private String description;

    private Integer createdAtUnix;

    private Integer updatedAtUnix;

    private double userRatings;

    public RecipeDTO(String name,
                     String thumbnailUrl,
                     String videoAdContent,
                     float totalTimeMinutes,
                     float cookTimeMinutes,
                     float prepTimeMinutes,
                     String country,
                     Integer numServings,
                     String description,
                     Integer createdAtUnix,
                     Integer updatedAtUnix,
                     double userRatings) {
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        this.videoAdContent = videoAdContent;
        this.totalTimeMinutes = totalTimeMinutes;
        this.cookTimeMinutes = cookTimeMinutes;
        this.prepTimeMinutes = prepTimeMinutes;
        this.country = country;
        this.numServings = numServings;
        this.description = description;
        this.createdAtUnix = createdAtUnix;
        this.updatedAtUnix = updatedAtUnix;
        this.userRatings = userRatings;
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

    public String getVideoAdContent() {
        return videoAdContent;
    }

    public void setVideoAdContent(String videoAdContent) {
        this.videoAdContent = videoAdContent;
    }

    public float getTotalTimeMinutes() {
        return totalTimeMinutes;
    }

    public void setTotalTimeMinutes(float totalTimeMinutes) {
        this.totalTimeMinutes = totalTimeMinutes;
    }

    public float getCookTimeMinutes() {
        return cookTimeMinutes;
    }

    public void setCookTimeMinutes(float cookTimeMinutes) {
        this.cookTimeMinutes = cookTimeMinutes;
    }

    public float getPrepTimeMinutes() {
        return prepTimeMinutes;
    }

    public void setPrepTimeMinutes(float prepTimeMinutes) {
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

    public double getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(double userRatings) {
        this.userRatings = userRatings;
    }
}
