package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by maxim_000 on 3/29/2018.
 */

public class PIFeedback {
    private int equipmentGroudId = 8;
    private String username;
    private String comments;
    private int rating;
    private String name;

    public PIFeedback(String username, String comments, int rating, String name) {
        this.username = username;
        this.username = comments;
        this.rating = rating;
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getComments() {
        return comments;
    }

    public int getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }
}
