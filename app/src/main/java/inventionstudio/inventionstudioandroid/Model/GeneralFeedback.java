package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by Maxwell on 3/29/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type General feedback.
 */
public class GeneralFeedback {

    @SerializedName("equipment_group_id")
    @Expose
    private Integer equipmentGroupId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("comments")
    @Expose
    private String comments;

    /**
     * No args constructor for use in serialization
     */
    public GeneralFeedback() {
    }

    /**
     * Instantiates a new General feedback.
     *
     * @param equipmentGroupId the equipment group id
     * @param username         the username
     * @param comments         the comments
     */
    public GeneralFeedback(Integer equipmentGroupId, String username, String comments) {
        super();
        this.equipmentGroupId = equipmentGroupId;
        this.username = username;
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
     * Gets comments.
     *
     * @return the comments
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

