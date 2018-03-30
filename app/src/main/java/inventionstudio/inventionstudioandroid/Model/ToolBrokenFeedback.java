package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by maxim_000 on 3/29/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ToolBrokenFeedback {

    @SerializedName("equipment_group_id")
    @Expose
    private Integer equipmentGroupId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("problem")
    @Expose
    private String problem;
    @SerializedName("tool_group_name")
    @Expose
    private String toolGroupName;
    @SerializedName("tool_name")
    @Expose
    private String toolName;
    @SerializedName("comments")
    @Expose
    private String comments;

    /**
     * No args constructor for use in serialization
     *
     */
    public ToolBrokenFeedback() {
    }

    /**
     *
     * @param username
     * @param toolGroupName
     * @param equipmentGroupId
     * @param problem
     * @param toolName
     * @param comments
     */
    public ToolBrokenFeedback(Integer equipmentGroupId, String username, String problem, String toolGroupName, String toolName, String comments) {
        super();
        this.equipmentGroupId = equipmentGroupId;
        this.username = username;
        this.problem = problem;
        this.toolGroupName = toolGroupName;
        this.toolName = toolName;
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

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getToolGroupName() {
        return toolGroupName;
    }

    public void setToolGroupName(String toolGroupName) {
        this.toolGroupName = toolGroupName;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
