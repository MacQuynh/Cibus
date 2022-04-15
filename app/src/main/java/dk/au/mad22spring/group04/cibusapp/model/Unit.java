
package dk.au.mad22spring.group04.cibusapp.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//generated with https://www.jsonschema2pojo.org/

public class Unit {

    @SerializedName("abbreviation")
    @Expose
    private String abbreviation;
    @SerializedName("system")
    @Expose
    private String system;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("display_plural")
    @Expose
    private String displayPlural;
    @SerializedName("display_singular")
    @Expose
    private String displaySingular;

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
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
