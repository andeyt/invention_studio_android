package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by Rishab K on 2/8/2018.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import inventionstudio.inventionstudioandroid.R;

/**
 * The type Equipment.
 */
public class Equipment implements Serializable, Comparable<Equipment>{

    transient private int statusComparisonVal;

    private int getStatusComparisonVal() {
        return statusComparisonVal;
    }

    public int compareTo(Equipment other) {
        if (this.statusComparisonVal == other.getStatusComparisonVal()) {
            return this.getToolName().compareTo(other.getToolName());
        }
        return this.statusComparisonVal - other.getStatusComparisonVal();
    }

    @SerializedName("equipmentGroupId")
    @Expose
    private Integer equipmentGroupId;
    @SerializedName("locationId")
    @Expose
    private Integer locationId;
    @SerializedName("toolId")
    @Expose
    private Integer toolId;
    @SerializedName("currentUserUserName")
    @Expose
    private String currentUserUserName;
    @SerializedName("equipmentGroupDescription")
    @Expose
    private String equipmentGroupDescription;
    @SerializedName("equipmentGroupName")
    @Expose
    private String equipmentGroupName;
    @SerializedName("locationAddress")
    @Expose
    private String locationAddress;
    @SerializedName("locationManager")
    @Expose
    private String locationManager;
    @SerializedName("locationName")
    @Expose
    private String locationName;
    @SerializedName("locationDescription")
    @Expose
    private String locationDescription;
    @SerializedName("locationUrl")
    @Expose
    private String locationUrl;
    @SerializedName("toolCurrentUser")
    @Expose
    private String toolCurrentUser;
    @SerializedName("toolDescription")
    @Expose
    private String toolDescription;
    @SerializedName("toolName")
    @Expose
    private String toolName;
    @SerializedName("locationPhone")
    @Expose
    private String locationPhone;
    @SerializedName("toolInUseSince")
    @Expose
    private String toolInUseSince;
    @SerializedName("toolIsOperational")
    @Expose
    private Boolean toolIsOperational;

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
     * Gets location id.
     *
     * @return the location id
     */
    public Integer getLocationId() {
        return locationId;
    }

    /**
     * Sets location id.
     *
     * @param locationId the location id
     */
    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    /**
     * Gets tool id.
     *
     * @return the tool id
     */
    public Integer getToolId() {
        return toolId;
    }

    /**
     * Sets tool id.
     *
     * @param toolId the tool id
     */
    public void setToolId(Integer toolId) {
        this.toolId = toolId;
    }

    /**
     * Gets current user user name.
     *
     * @return the current user user name
     */
    public String getCurrentUserUserName() {
        return currentUserUserName;
    }

    /**
     * Sets current user user name.
     *
     * @param currentUserUserName the current user user name
     */
    public void setCurrentUserUserName(String currentUserUserName) {
        this.currentUserUserName = currentUserUserName;
    }

    /**
     * Gets equipment group description.
     *
     * @return the equipment group description
     */
    public String getEquipmentGroupDescription() {
        return equipmentGroupDescription;
    }

    /**
     * Sets equipment group description.
     *
     * @param equipmentGroupDescription the equipment group description
     */
    public void setEquipmentGroupDescription(String equipmentGroupDescription) {
        this.equipmentGroupDescription = equipmentGroupDescription;
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
     * Gets location address.
     *
     * @return the location address
     */
    public String getLocationAddress() {
        return locationAddress;
    }

    /**
     * Sets location address.
     *
     * @param locationAddress the location address
     */
    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    /**
     * Gets location manager.
     *
     * @return the location manager
     */
    public String getLocationManager() {
        return locationManager;
    }

    /**
     * Sets location manager.
     *
     * @param locationManager the location manager
     */
    public void setLocationManager(String locationManager) {
        this.locationManager = locationManager;
    }

    /**
     * Gets location name.
     *
     * @return the location name
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * Sets location name.
     *
     * @param locationName the location name
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * Gets location description.
     *
     * @return the location description
     */
    public String getLocationDescription() {
        return locationDescription;
    }

    /**
     * Sets location description.
     *
     * @param locationDescription the location description
     */
    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    /**
     * Gets location url.
     *
     * @return the location url
     */
    public String getLocationUrl() {
        return locationUrl;
    }

    /**
     * Sets location url.
     *
     * @param locationUrl the location url
     */
    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
    }

    /**
     * Gets tool current user.
     *
     * @return the tool current user
     */
    public String getToolCurrentUser() {
        return toolCurrentUser;
    }

    /**
     * Sets tool current user.
     *
     * @param toolCurrentUser the tool current user
     */
    public void setToolCurrentUser(String toolCurrentUser) {
        this.toolCurrentUser = toolCurrentUser;
    }

    /**
     * Gets tool description.
     *
     * @return the tool description
     */
    public String getToolDescription() {
        return toolDescription;
    }

    /**
     * Sets tool description.
     *
     * @param toolDescription the tool description
     */
    public void setToolDescription(String toolDescription) {
        this.toolDescription = toolDescription;
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
     * Gets location phone.
     *
     * @return the location phone
     */
    public String getLocationPhone() {
        return locationPhone;
    }

    /**
     * Sets location phone.
     *
     * @param locationPhone the location phone
     */
    public void setLocationPhone(String locationPhone) {
        this.locationPhone = locationPhone;
    }

    /**
     * Gets tool in use since.
     *
     * @return the tool in use since
     */
    public String getToolInUseSince() {
        return toolInUseSince;
    }

    /**
     * Sets tool in use since.
     *
     * @param toolInUseSince the tool in use since
     */
    public void setToolInUseSince(String toolInUseSince) {
        this.toolInUseSince = toolInUseSince;
    }

    /**
     * Gets tool is operational.
     *
     * @return the tool is operational
     */
    public Boolean getToolIsOperational() {
        return toolIsOperational;
    }

    /**
     * Sets tool is operational.
     *
     * @param toolIsOperational the tool is operational
     */
    public void setToolIsOperational(Boolean toolIsOperational) {
        this.toolIsOperational = toolIsOperational;
    }

    /**
     * Status icon int that references status
     *
     * @return the int
     */
    public int statusIcon() {
        if (toolIsOperational) {
            if (!toolCurrentUser.equals("")) {
                statusComparisonVal = 0;
                return R.drawable.in_use;
            }
            statusComparisonVal = -1;
            return R.drawable.available;

        }
        statusComparisonVal = 1;
        return R.drawable.unavailable;

    }

    /**
     * Status text string that determines either in use available or down
     *
     * @return the string
     */
    public String statusText() {
        if (toolIsOperational) {
            if (!toolCurrentUser.equals("")) {
                return "In Use";
            }
            return "Available";
        }
        return "Down";
    }
}
