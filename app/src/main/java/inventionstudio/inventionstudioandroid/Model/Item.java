package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by Maxwell on 3/12/2018.
 */

public class Item {

    String groupName;

    public Item(String name) {
        groupName = name;
    }

    // Return the group name
    public String getName() {
        return groupName;
    }
}
