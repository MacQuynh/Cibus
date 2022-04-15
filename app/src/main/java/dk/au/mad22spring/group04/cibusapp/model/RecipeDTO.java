package dk.au.mad22spring.group04.cibusapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeDTO {
    private String name;

    private Integer id;

    private String thumbnailUrl;

    private String videoAdContent;

    private float totalTimeMinutes;

    private float cookTimeMinutes;

    private float prepTimeMinutes;

    private Nutrition nutrition;

    private String country;

    private TotalTimeTier totalTimeDescription;

    private Integer numServings;

    private String description;

    private Integer createdAtUnix;

    private Integer updatedAtUnix;

    private List<Instruction> instructions = null;

    private float userRatings;

    private List<Section> ingredientsWithMeasurement = null;

    public RecipeDTO(String name,
                     Integer id,
                     String thumbnailUrl,
                     String videoAdContent,
                     float totalTimeMinutes,
                     float cookTimeMinutes,
                     float prepTimeMinutes,
                     Nutrition nutrition,
                     String country,
                     TotalTimeTier totalTimeDescription,
                     Integer numServings,
                     String description,
                     Integer createdAtUnix,
                     Integer updatedAtUnix,
                     List<Instruction> instructions,
                     float userRatings,
                     List<Section> ingredientsWithMeasurement) {
        this.name = name;
        this.id = id;
        this.thumbnailUrl = thumbnailUrl;
        this.videoAdContent = videoAdContent;
        this.totalTimeMinutes = totalTimeMinutes;
        this.cookTimeMinutes = cookTimeMinutes;
        this.prepTimeMinutes = prepTimeMinutes;
        this.nutrition = nutrition;
        this.country = country;
        this.totalTimeDescription = totalTimeDescription;
        this.numServings = numServings;
        this.description = description;
        this.createdAtUnix = createdAtUnix;
        this.updatedAtUnix = updatedAtUnix;
        this.instructions = instructions;
        this.userRatings = userRatings;
        this.ingredientsWithMeasurement = ingredientsWithMeasurement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public TotalTimeTier getTotalTimeDescription() {
        return totalTimeDescription;
    }

    public void setTotalTimeDescription(TotalTimeTier totalTimeDescription) {
        this.totalTimeDescription = totalTimeDescription;
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

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public float getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(float userRatings) {
        this.userRatings = userRatings;
    }

    public List<Section> getIngredientsWithMeasurement() {
        return ingredientsWithMeasurement;
    }

    public void setIngredientsWithMeasurement(List<Section> ingredientsWithMeasurement) {
        this.ingredientsWithMeasurement = ingredientsWithMeasurement;
    }
}
