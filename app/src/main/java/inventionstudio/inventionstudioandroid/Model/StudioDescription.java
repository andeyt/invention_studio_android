package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by Rishab K on 4/2/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Studio description.
 */
public class StudioDescription {

    @SerializedName("equipmentGroupDescriptionHtml")
    @Expose
    private String equipmentGroupDescriptionHtml;
    @SerializedName("equipmentGroupName")
    @Expose
    private String equipmentGroupName;
    @SerializedName("equipmentGroupShortName")
    @Expose
    private String equipmentGroupShortName;
    @SerializedName("equipmentGroupId")
    @Expose
    private Integer equipmentGroupId;

    /**
     * No args constructor for use in serialization
     */
    public StudioDescription() {
    }

    /**
     * Instantiates a new Studio description.
     *
     * @param equipmentGroupDescriptionHtml the equipment group description html
     * @param equipmentGroupName            the equipment group name
     * @param equipmentGroupShortName       the equipment group short name
     * @param equipmentGroupId              the equipment group id
     */
    public StudioDescription(String equipmentGroupDescriptionHtml, String equipmentGroupName, String equipmentGroupShortName, Integer equipmentGroupId) {
        super();
        this.equipmentGroupDescriptionHtml = equipmentGroupDescriptionHtml;
        this.equipmentGroupName = equipmentGroupName;
        this.equipmentGroupShortName = equipmentGroupShortName;
        this.equipmentGroupId = equipmentGroupId;
    }

    /**
     * Gets equipment group description html.
     *
     * @return the equipment group description html
     */
    public String getEquipmentGroupDescriptionHtml() {
        return equipmentGroupDescriptionHtml;
    }

    /**
     * Sets equipment group description html.
     *
     * @param equipmentGroupDescriptionHtml the equipment group description html
     */
    public void setEquipmentGroupDescriptionHtml(String equipmentGroupDescriptionHtml) {
        this.equipmentGroupDescriptionHtml = equipmentGroupDescriptionHtml;
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
    public void setEquipmentGroupName(String equipmentGroupName) {
        this.equipmentGroupName = equipmentGroupName;
    }

    /**
     * Gets equipment group short name.
     *
     * @return the equipment group short name
     */
    public String getEquipmentGroupShortName() {
        return equipmentGroupShortName;
    }

    /**
     * Sets equipment group short name.
     *
     * @param equipmentGroupShortName the equipment group short name
     */
    public void setEquipmentGroupShortName(String equipmentGroupShortName) {
        this.equipmentGroupShortName = equipmentGroupShortName;
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

}
