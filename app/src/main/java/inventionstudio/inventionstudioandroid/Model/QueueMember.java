package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by Maxwell Broom on 2/8/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueueMember {

    @SerializedName("queueGroupId")
    @Expose
    private Integer queueGroupId;
    @SerializedName("queueGroupId")
    @Expose
    private boolean isGroup;
    @SerializedName("memberName")
    @Expose
    private String memberName;
    @SerializedName("memberUserName")
    @Expose
    private String memberUserName;
    @SerializedName("queueName")
    @Expose
    private String queueName;
    @SerializedName("hasBeenNotified")
    @Expose
    private Boolean hasBeenNotified;
    @SerializedName("memberMinutesRemaining")
    @Expose
    private Integer memberMinutesRemaining;
    @SerializedName("memberQueueLocation")
    @Expose
    private Integer memberQueueLocation;

    public boolean getIsGroup() { return isGroup; }

    public void setIsGroup(boolean isGroup) { this.isGroup = isGroup; }

    public Integer getQueueGroupId() {
        return queueGroupId;
    }

    public void setQueueGroupId(Integer queueGroupId) {
        this.queueGroupId = queueGroupId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberUserName() {
        return memberUserName;
    }

    public void setMemberUserName(String memberUserName) {
        this.memberUserName = memberUserName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Boolean getHasBeenNotified() {
        return hasBeenNotified;
    }

    public void setHasBeenNotified(Boolean hasBeenNotified) {
        this.hasBeenNotified = hasBeenNotified;
    }

    public Integer getMemberMinutesRemaining() {
        return memberMinutesRemaining;
    }

    public void setMemberMinutesRemaining(Integer memberMinutesRemaining) {
        this.memberMinutesRemaining = memberMinutesRemaining;
    }

    public Integer getMemberQueueLocation() {
        return memberQueueLocation;
    }

    public void setMemberQueueLocation(Integer memberQueueLocation) {
        this.memberQueueLocation = memberQueueLocation;
    }
}