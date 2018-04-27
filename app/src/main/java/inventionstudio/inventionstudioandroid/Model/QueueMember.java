package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by Maxwell Broom on 2/8/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Queue member.
 */
public class QueueMember implements Comparable<QueueMember>{

    @SerializedName("queueGroupId")
    @Expose
    private Integer queueGroupId;
    @SerializedName("isGroup")
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

    /**
     * Gets is group.
     *
     * @return the is group
     */
    public boolean getIsGroup() { return isGroup; }

    /**
     * Sets is group.
     *
     * @param isGroup the is group
     */
    public void setIsGroup(boolean isGroup) { this.isGroup = isGroup; }

    /**
     * Gets queue group id.
     *
     * @return the queue group id
     */
    public Integer getQueueGroupId() {
        return queueGroupId;
    }

    /**
     * Sets queue group id.
     *
     * @param queueGroupId the queue group id
     */
    public void setQueueGroupId(Integer queueGroupId) {
        this.queueGroupId = queueGroupId;
    }

    /**
     * Gets member name.
     *
     * @return the member name
     */
    public String getMemberName() {
        return memberName;
    }

    /**
     * Sets member name.
     *
     * @param memberName the member name
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /**
     * Gets member user name.
     *
     * @return the member user name
     */
    public String getMemberUserName() {
        return memberUserName;
    }

    /**
     * Sets member user name.
     *
     * @param memberUserName the member user name
     */
    public void setMemberUserName(String memberUserName) {
        this.memberUserName = memberUserName;
    }

    /**
     * Gets queue name.
     *
     * @return the queue name
     */
    public String getQueueName() {
        return queueName;
    }

    /**
     * Sets queue name.
     *
     * @param queueName the queue name
     */
    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    /**
     * Gets has been notified.
     *
     * @return the has been notified
     */
    public Boolean getHasBeenNotified() {
        return hasBeenNotified;
    }

    /**
     * Sets has been notified.
     *
     * @param hasBeenNotified the has been notified
     */
    public void setHasBeenNotified(Boolean hasBeenNotified) {
        this.hasBeenNotified = hasBeenNotified;
    }

    /**
     * Gets member minutes remaining.
     *
     * @return the member minutes remaining
     */
    public Integer getMemberMinutesRemaining() {
        return memberMinutesRemaining;
    }

    /**
     * Sets member minutes remaining.
     *
     * @param memberMinutesRemaining the member minutes remaining
     */
    public void setMemberMinutesRemaining(Integer memberMinutesRemaining) {
        this.memberMinutesRemaining = memberMinutesRemaining;
    }

    /**
     * Gets member queue location.
     *
     * @return the member queue location
     */
    public Integer getMemberQueueLocation() {
        return memberQueueLocation;
    }

    /**
     * Sets member queue location.
     *
     * @param memberQueueLocation the member queue location
     */
    public void setMemberQueueLocation(Integer memberQueueLocation) {
        this.memberQueueLocation = memberQueueLocation;
    }

    public int compareTo(QueueMember other) {
        return this.memberQueueLocation - other.getMemberQueueLocation();
    }
}