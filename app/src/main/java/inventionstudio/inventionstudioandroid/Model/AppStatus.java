package inventionstudio.inventionstudioandroid.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rishab K on 4/6/2018.
 */
public class AppStatus {


    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;


    /**
     * No args constructor for use in serialization
     */
    public AppStatus() {
    }

    /**
     * App status retrieved from IS Server
     *
     * @param title   title of the status
     * @param message message of the status
     */
    public AppStatus(String title, String message) {
        super();
        this.title = title;
        this.message = message;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
