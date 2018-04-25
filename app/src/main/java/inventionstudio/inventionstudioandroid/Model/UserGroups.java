package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by Rishab K on 2/9/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type User groups.
 */
public class UserGroups {

    @SerializedName("equipmentGroupId")
    @Expose
    private Integer equipmentGroupId;
    @SerializedName("templateId")
    @Expose
    private Integer templateId;
    @SerializedName("equipmentGroupName")
    @Expose
    private String equipmentGroupName;
    @SerializedName("templateName")
    @Expose
    private String templateName;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userUserName")
    @Expose
    private String userUserName;

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
     * Gets template id.
     *
     * @return the template id
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * Sets template id.
     *
     * @param templateId the template id
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * Gets equipment group name.
     *
     * @return the equipment group name
     */
    public String getEquipmentGroupName() {
        return equipmentGroupName;
    }

    /**
     * Sets equipment group name.
     *
     * @param equipmentGroupName the equipment group name
     */
    public void sejavatEquipmentGroupName(String equipmentGroupName) {
        this.equipmentGroupName = equipmentGroupName;
    }

    /**
     * Gets template name.
     *
     * @return the template name
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * Sets template name.
     *
     * @param templateName the template name
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets user user name.
     *
     * @return the user user name
     */
    public String getUserUserName() {
        return userUserName;
    }

    /**
     * Sets user user name.
     *
     * @param userUserName the user user name
     */
    public void setUserUserName(String userUserName) {
        this.userUserName = userUserName;
    }
}

