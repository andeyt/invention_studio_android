package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by Maxwell on 3/29/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
     *
     */
    public GeneralFeedback() {
    }

    /**
     *
     * @param username
     * @param equipmentGroupId
     * @param comments
     */
    public GeneralFeedback(Integer equipmentGroupId, String username, String comments) {
        super();
        this.equipmentGroupId = equipmentGroupId;
        this.username = username;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}

