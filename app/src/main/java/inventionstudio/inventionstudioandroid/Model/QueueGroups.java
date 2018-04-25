package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by Rishab K on 2/18/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Queue groups.
 */
public class QueueGroups {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("isGroup")
    @Expose
    private Boolean isGroup;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets is group.
     *
     * @return the is group
     */
    public Boolean getIsGroup() {
        return isGroup;
    }

    /**
     * Sets is group.
     *
     * @param isGroup the is group
     */
    public void setIsGroup(Boolean isGroup) {
        this.isGroup = isGroup;
    }

}
