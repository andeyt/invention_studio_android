package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by Rishab K on 3/30/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
     *
     */
    public LoginFormObject() {
    }

    /**
     *
     * @param user_username
     * @param userName
     * @param apiKey
     */
    public LoginFormObject(String user_username, String userName, String apiKey) {
        super();
        this.user_username = user_username;
        this.userName = userName;
        this.apiKey = apiKey;
    }

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}
