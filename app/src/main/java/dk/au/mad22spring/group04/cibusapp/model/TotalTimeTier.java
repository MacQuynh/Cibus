
package dk.au.mad22spring.group04.cibusapp.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//generated with https://www.jsonschema2pojo.org/

public class TotalTimeTier {

    @SerializedName("tier")
    @Expose
    private String tier;
    @SerializedName("display_tier")
    @Expose
    private String displayTier;

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getDisplayTier() {
        return displayTier;
    }

    public void setDisplayTier(String displayTier) {
        this.displayTier = displayTier;
    }

}
