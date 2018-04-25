package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by maxim_000 on 3/29/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Staff feedback.
 */
public class StaffFeedback {

    @SerializedName("equipment_group_id")
    @Expose
    private Integer equipmentGroupId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("staff_name")
    @Expose
    private String staffName;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("comments")
    @Expose
    private String comments;

    /**
     * No args constructor for use in serialization
     */
    public StaffFeedback() {
    }

    /**
     * Instantiates a new Staff feedback.
     *
     * @param equipmentGroupId the equipment group id
     * @param username         the username
     * @param staffName        the staff name
     * @param rating           the rating
     * @param comments         the comments
     */
    public StaffFeedback(Integer equipmentGroupId, String username, String staffName, Integer rating, String comments) {
        super();
        this.equipmentGroupId = equipmentGroupId;
        this.username = username;
        this.staffName = staffName;
        this.rating = rating;
        this.comments = comments;
    }

    /**
     * Gets equipment group id.
     *
     * @return the equipment group id
     */
    public Integer getEquipmentGroupId() {
        return equipmentGroupId;
    }

    /**
     * Sets equipment group id.
     *
     * @param equipmentGroupId the equipment group id
     */
    public void setEquipmentGroupId(Integer equipmentGroupId) {
        this.equipmentGroupId = equipmentGroupId;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets staff name.
     *
     * @return the staff name
     */
    public String getStaffName() {
        return staffName;
    }

    /**
     * Sets staff name.
     *
     * @param staffName the staff name
     */
    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    /**
     * Gets rating.
     *
     * @return the rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Sets rating.
     *
     * @param rating the integer rating
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * Gets comments.
     *
     * @return the string of comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets comments.
     *
     * @param comments the comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

}
