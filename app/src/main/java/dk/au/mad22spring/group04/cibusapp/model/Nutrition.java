
package dk.au.mad22spring.group04.cibusapp.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//generated with https://www.jsonschema2pojo.org/

public class Nutrition {

    @SerializedName("sugar")
    @Expose
    private Integer sugar;
    @SerializedName("carbohydrates")
    @Expose
    private Integer carbohydrates;
    @SerializedName("fiber")
    @Expose
    private Integer fiber;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("protein")
    @Expose
    private Integer protein;
    @SerializedName("fat")
    @Expose
    private Integer fat;
    @SerializedName("calories")
    @Expose
    private Integer calories;

    public Integer getSugar() {
        return sugar;
    }

    public void setSugar(Integer sugar) {
        this.sugar = sugar;
    }

    public Integer getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Integer carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public Integer getFiber() {
        return fiber;
    }

    public void setFiber(Integer fiber) {
        this.fiber = fiber;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getProtein() {
        return protein;
    }

    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    public Integer getFat() {
        return fat;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

}
