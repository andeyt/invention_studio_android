package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by maxim_000 on 3/29/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PIFeedback {

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
     *
     */
    public PIFeedback() {
    }

    /**
     *
     * @param username
     * @param equipmentGroupId
     * @param staffName
     * @param rating
     * @param comments
     */
    public PIFeedback(Integer equipmentGroupId, String username, String staffName, Integer rating, String comments) {
        super();
        this.equipmentGroupId = equipmentGroupId;
        this.username = username;
        this.staffName = staffName;
        this.rating = rating;
        this.comments = comments;
    }

    public Integer getEquipmentGroupId() {
        return equipmentGroupId;
    }

    public void setEquipmentGroupId(Integer equipmentGroupId) {
        this.equipmentGroupId = equipmentGroupId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
