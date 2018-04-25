package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by maxim_000 on 3/29/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Tool broken feedback.
 */
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
     */
    public ToolBrokenFeedback() {
    }

    /**
     * Instantiates a new Tool broken feedback.
     *
     * @param equipmentGroupId the equipment group id
     * @param username         the username
     * @param problem          the problem
     * @param toolGroupName    the tool group name
     * @param toolName         the tool name
     * @param comments         the comments
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
     * Gets problem.
     *
     * @return the problem
     */
    public String getProblem() {
        return problem;
    }

    /**
     * Sets problem.
     *
     * @param problem the problem
     */
    public void setProblem(String problem) {
        this.problem = problem;
    }

    /**
     * Gets tool group name.
     *
     * @return the tool group name
     */
    public String getToolGroupName() {
        return toolGroupName;
    }

    /**
     * Sets tool group name.
     *
     * @param toolGroupName the tool group name
     */
    public void setToolGroupName(String toolGroupName) {
        this.toolGroupName = toolGroupName;
    }

    /**
     * Gets tool name.
     *
     * @return the tool name
     */
    public String getToolName() {
        return toolName;
    }

    /**
     * Sets tool name.
     *
     * @param toolName the tool name
     */
    public void setToolName(String toolName) {
        this.toolName = toolName;
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
