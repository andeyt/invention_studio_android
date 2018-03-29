package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by Maxwell on 3/29/2018.
 */

public class GeneralFeedback {
    private String username;
    private String comments;

    public GeneralFeedback(String username, String comments) {
        this.username = username;
        this.username = comments;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUsername() {
        return username;
    }

    public String getComments() {
        return comments;
    }
}
