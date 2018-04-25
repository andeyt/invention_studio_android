package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by Rishab K on 3/30/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The type Login form object.
 */
public class LoginFormObject {

    @SerializedName("user_username")
    @Expose
    private String user_username;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("api_key")
    @Expose
    private String apiKey;

    /**
     * No args constructor for use in serialization
     */
    public LoginFormObject() {
    }

    /**
     * Instantiates a new Login form object.
     *
     * @param user_username the user username
     * @param userName      the user name
     * @param apiKey        the api key
     */
    public LoginFormObject(String user_username, String userName, String apiKey) {
        super();
        this.user_username = user_username;
        this.userName = userName;
        this.apiKey = apiKey;
    }

    /**
     * Gets user username.
     *
     * @return the user username
     */
    public String getUser_username() {
        return user_username;
    }

    /**
     * Sets user username.
     *
     * @param user_username the user username
     */
    public void setUser_username(String user_username) {
        this.user_username = user_username;
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
     * Gets api key.
     *
     * @return the api key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Sets api key.
     *
     * @param apiKey the api key
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}
