
package dk.au.mad22spring.group04.cibusapp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//generated with https://www.jsonschema2pojo.org/

public class Result {

    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("video_ad_content")
    @Expose
    private String videoAdContent;
    @SerializedName("keywords")
    @Expose
    private String keywords;
    @SerializedName("servings_noun_singular")
    @Expose
    private String servingsNounSingular;
    @SerializedName("tips_and_ratings_enabled")
    @Expose
    private Boolean tipsAndRatingsEnabled;
    @SerializedName("draft_status")
    @Expose
    private String draftStatus;
    @SerializedName("video_url")
    @Expose
    private String videoUrl;
    @SerializedName("topics")
    @Expose
    private List<Topic> topics = null;
    @SerializedName("yields")
    @Expose
    private String yields;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = null;
    @SerializedName("inspired_by_url")
    @Expose
    private Object inspiredByUrl;
    @SerializedName("thumbnail_alt_text")
    @Expose
    private String thumbnailAltText;
    @SerializedName("total_time_minutes")
    @Expose
    private Object totalTimeMinutes;
    @SerializedName("promotion")
    @Expose
    private String promotion;
    @SerializedName("brand_id")
    @Expose
    private Integer brandId;
    @SerializedName("nutrition")
    @Expose
    private Nutrition nutrition;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("compilations")
    @Expose
    private List<Object> compilations = null;
    @SerializedName("aspect_ratio")
    @Expose
    private String aspectRatio;
    @SerializedName("credits")
    @Expose
    private List<Credit> credits = null;
    @SerializedName("cook_time_minutes")
    @Expose
    private Object cookTimeMinutes;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("brand")
    @Expose
    private Brand brand;
    @SerializedName("prep_time_minutes")
    @Expose
    private Object prepTimeMinutes;
    @SerializedName("video_id")
    @Expose
    private Integer videoId;
    @SerializedName("nutrition_visibility")
    @Expose
    private String nutritionVisibility;
    @SerializedName("approved_at")
    @Expose
    private Integer approvedAt;
    @SerializedName("renditions")
    @Expose
    private List<Rendition> renditions = null;
    @SerializedName("beauty_url")
    @Expose
    private Object beautyUrl;
    @SerializedName("total_time_tier")
    @Expose
    private TotalTimeTier totalTimeTier;
    @SerializedName("seo_title")
    @Expose
    private String seoTitle;
    @SerializedName("canonical_id")
    @Expose
    private String canonicalId;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("num_servings")
    @Expose
    private Integer numServings;
    @SerializedName("show")
    @Expose
    private Show show;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("created_at")
    @Expose
    private Integer createdAt;
    @SerializedName("updated_at")
    @Expose
    private Integer updatedAt;
    @SerializedName("is_shoppable")
    @Expose
    private Boolean isShoppable;
    @SerializedName("original_video_url")
    @Expose
    private String originalVideoUrl;
    @SerializedName("instructions")
    @Expose
    private List<Instruction> instructions = null;
    @SerializedName("user_ratings")
    @Expose
    private UserRatings userRatings;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("show_id")
    @Expose
    private Integer showId;
    @SerializedName("servings_noun_plural")
    @Expose
    private String servingsNounPlural;
    @SerializedName("facebook_posts")
    @Expose
    private List<Object> facebookPosts = null;
    @SerializedName("sections")
    @Expose
    private List<Section> sections = null;
    @SerializedName("buzz_id")
    @Expose
    private Object buzzId;
    @SerializedName("is_one_top")
    @Expose
    private Boolean isOneTop;

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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getServingsNounSingular() {
        return servingsNounSingular;
    }

    public void setServingsNounSingular(String servingsNounSingular) {
        this.servingsNounSingular = servingsNounSingular;
    }

    public Boolean getTipsAndRatingsEnabled() {
        return tipsAndRatingsEnabled;
    }

    public void setTipsAndRatingsEnabled(Boolean tipsAndRatingsEnabled) {
        this.tipsAndRatingsEnabled = tipsAndRatingsEnabled;
    }

    public String getDraftStatus() {
        return draftStatus;
    }

    public void setDraftStatus(String draftStatus) {
        this.draftStatus = draftStatus;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public String getYields() {
        return yields;
    }

    public void setYields(String yields) {
        this.yields = yields;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Object getInspiredByUrl() {
        return inspiredByUrl;
    }

    public void setInspiredByUrl(Object inspiredByUrl) {
        this.inspiredByUrl = inspiredByUrl;
    }

    public String getThumbnailAltText() {
        return thumbnailAltText;
    }

    public void setThumbnailAltText(String thumbnailAltText) {
        this.thumbnailAltText = thumbnailAltText;
    }

    public Object getTotalTimeMinutes() {
        return totalTimeMinutes;
    }

    public void setTotalTimeMinutes(Object totalTimeMinutes) {
        this.totalTimeMinutes = totalTimeMinutes;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getCompilations() {
        return compilations;
    }

    public void setCompilations(List<Object> compilations) {
        this.compilations = compilations;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public Object getCookTimeMinutes() {
        return cookTimeMinutes;
    }

    public void setCookTimeMinutes(Object cookTimeMinutes) {
        this.cookTimeMinutes = cookTimeMinutes;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Object getPrepTimeMinutes() {
        return prepTimeMinutes;
    }

    public void setPrepTimeMinutes(Object prepTimeMinutes) {
        this.prepTimeMinutes = prepTimeMinutes;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getNutritionVisibility() {
        return nutritionVisibility;
    }

    public void setNutritionVisibility(String nutritionVisibility) {
        this.nutritionVisibility = nutritionVisibility;
    }

    public Integer getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(Integer approvedAt) {
        this.approvedAt = approvedAt;
    }

    public List<Rendition> getRenditions() {
        return renditions;
    }

    public void setRenditions(List<Rendition> renditions) {
        this.renditions = renditions;
    }

    public Object getBeautyUrl() {
        return beautyUrl;
    }

    public void setBeautyUrl(Object beautyUrl) {
        this.beautyUrl = beautyUrl;
    }

    public TotalTimeTier getTotalTimeTier() {
        return totalTimeTier;
    }

    public void setTotalTimeTier(TotalTimeTier totalTimeTier) {
        this.totalTimeTier = totalTimeTier;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getCanonicalId() {
        return canonicalId;
    }

    public void setCanonicalId(String canonicalId) {
        this.canonicalId = canonicalId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getNumServings() {
        return numServings;
    }

    public void setNumServings(Integer numServings) {
        this.numServings = numServings;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Integer updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsShoppable() {
        return isShoppable;
    }

    public void setIsShoppable(Boolean isShoppable) {
        this.isShoppable = isShoppable;
    }

    public String getOriginalVideoUrl() {
        return originalVideoUrl;
    }

    public void setOriginalVideoUrl(String originalVideoUrl) {
        this.originalVideoUrl = originalVideoUrl;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public UserRatings getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(UserRatings userRatings) {
        this.userRatings = userRatings;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
    }

    public String getServingsNounPlural() {
        return servingsNounPlural;
    }

    public void setServingsNounPlural(String servingsNounPlural) {
        this.servingsNounPlural = servingsNounPlural;
    }

    public List<Object> getFacebookPosts() {
        return facebookPosts;
    }

    public void setFacebookPosts(List<Object> facebookPosts) {
        this.facebookPosts = facebookPosts;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public Object getBuzzId() {
        return buzzId;
    }

    public void setBuzzId(Object buzzId) {
        this.buzzId = buzzId;
    }

    public Boolean getIsOneTop() {
        return isOneTop;
    }

    public void setIsOneTop(Boolean isOneTop) {
        this.isOneTop = isOneTop;
    }

}
