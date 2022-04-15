
package dk.au.mad22spring.group04.cibusapp.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//generated with https://www.jsonschema2pojo.org/

public class UserRatings {

    @SerializedName("count_positive")
    @Expose
    private Integer countPositive;
    @SerializedName("score")
    @Expose
    private Object score;
    @SerializedName("count_negative")
    @Expose
    private Integer countNegative;

    public Integer getCountPositive() {
        return countPositive;
    }

    public void setCountPositive(Integer countPositive) {
        this.countPositive = countPositive;
    }

    public Object getScore() {
        return score;
    }

    public void setScore(Object score) {
        this.score = score;
    }

    public Integer getCountNegative() {
        return countNegative;
    }

    public void setCountNegative(Integer countNegative) {
        this.countNegative = countNegative;
    }

}
