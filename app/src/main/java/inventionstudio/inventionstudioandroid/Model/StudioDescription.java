package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by Rishab K on 4/2/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
     *
     */
    public StudioDescription() {
    }

    /**
     *
     * @param equipmentGroupName
     * @param equipmentGroupId
     * @param equipmentGroupShortName
     * @param equipmentGroupDescriptionHtml
     */
    public StudioDescription(String equipmentGroupDescriptionHtml, String equipmentGroupName, String equipmentGroupShortName, Integer equipmentGroupId) {
        super();
        this.equipmentGroupDescriptionHtml = equipmentGroupDescriptionHtml;
        this.equipmentGroupName = equipmentGroupName;
        this.equipmentGroupShortName = equipmentGroupShortName;
        this.equipmentGroupId = equipmentGroupId;
    }

    public String getEquipmentGroupDescriptionHtml() {
        return equipmentGroupDescriptionHtml;
    }

    public void setEquipmentGroupDescriptionHtml(String equipmentGroupDescriptionHtml) {
        this.equipmentGroupDescriptionHtml = equipmentGroupDescriptionHtml;
    }

    public String getEquipmentGroupName() {
        return equipmentGroupName;
    }

    public void setEquipmentGroupName(String equipmentGroupName) {
        this.equipmentGroupName = equipmentGroupName;
    }

    public String getEquipmentGroupShortName() {
        return equipmentGroupShortName;
    }

    public void setEquipmentGroupShortName(String equipmentGroupShortName) {
        this.equipmentGroupShortName = equipmentGroupShortName;
    }

    public Integer getEquipmentGroupId() {
        return equipmentGroupId;
    }

    public void setEquipmentGroupId(Integer equipmentGroupId) {
        this.equipmentGroupId = equipmentGroupId;
    }

}
