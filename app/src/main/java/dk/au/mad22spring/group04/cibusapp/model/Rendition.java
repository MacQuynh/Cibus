
package dk.au.mad22spring.group04.cibusapp.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//generated with https://www.jsonschema2pojo.org/

public class Rendition {

    @SerializedName("poster_url")
    @Expose
    private String posterUrl;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("minimum_bit_rate")
    @Expose
    private Integer minimumBitRate;
    @SerializedName("container")
    @Expose
    private String container;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("bit_rate")
    @Expose
    private Object bitRate;
    @SerializedName("content_type")
    @Expose
    private String contentType;
    @SerializedName("aspect")
    @Expose
    private String aspect;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("maximum_bit_rate")
    @Expose
    private Integer maximumBitRate;
    @SerializedName("file_size")
    @Expose
    private Object fileSize;
    @SerializedName("height")
    @Expose
    private Integer height;

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getMinimumBitRate() {
        return minimumBitRate;
    }

    public void setMinimumBitRate(Integer minimumBitRate) {
        this.minimumBitRate = minimumBitRate;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getBitRate() {
        return bitRate;
    }

    public void setBitRate(Object bitRate) {
        this.bitRate = bitRate;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getAspect() {
        return aspect;
    }

    public void setAspect(String aspect) {
        this.aspect = aspect;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaximumBitRate() {
        return maximumBitRate;
    }

    public void setMaximumBitRate(Integer maximumBitRate) {
        this.maximumBitRate = maximumBitRate;
    }

    public Object getFileSize() {
        return fileSize;
    }

    public void setFileSize(Object fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

}
