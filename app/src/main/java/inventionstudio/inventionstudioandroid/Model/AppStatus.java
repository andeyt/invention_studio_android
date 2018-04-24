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
     *
     */
    public AppStatus() {
    }

    /**
     * App status retrieved from IS Server
     * @param message message of the status
     * @param title title of the status
     */
    public AppStatus(String title, String message) {
        super();
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
