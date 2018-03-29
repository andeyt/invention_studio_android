package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by maxim_000 on 3/29/2018.
 */

public class ToolBrokenFeedback {
    private String username;
    private String comments;
    private String toolGroup;
    private String toolName;
    private String problem;

    public ToolBrokenFeedback(String username, String comments, String toolGroup, String toolName, String problem) {
        this.username = username;
        this.username = comments;
        this.toolGroup = toolGroup;
        this.toolName = toolName;
        this.problem = problem;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setToolGroup(String toolGroup) {
        this.toolGroup = toolGroup;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getUsername() {
        return username;
    }

    public String getComments() {
        return comments;
    }

    public String getToolGroup() {
        return toolGroup;
    }

    public String getToolName() {
        return toolName;
    }

    public String getProblem() {
        return problem;
    }
}
