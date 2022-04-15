
package dk.au.mad22spring.group04.cibusapp.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//generated with https://www.jsonschema2pojo.org/

public class Instruction {

    @SerializedName("display_text")
    @Expose
    private String displayText;
    @SerializedName("start_time")
    @Expose
    private Integer startTime;
    @SerializedName("appliance")
    @Expose
    private Object appliance;
    @SerializedName("end_time")
    @Expose
    private Integer endTime;
    @SerializedName("temperature")
    @Expose
    private Object temperature;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("position")
    @Expose
    private Integer position;

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

    public Object getAppliance() {
        return appliance;
    }

    public void setAppliance(Object appliance) {
        this.appliance = appliance;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Object getTemperature() {
        return temperature;
    }

    public void setTemperature(Object temperature) {
        this.temperature = temperature;
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

}
