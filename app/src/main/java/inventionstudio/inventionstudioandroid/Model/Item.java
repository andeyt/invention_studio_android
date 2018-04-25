package inventionstudio.inventionstudioandroid.Model;

/**
 * Created by Maxwell on 3/12/2018.
 */
public class Item {

    /**
     * The Group name.
     */
    String groupName;

    /**
     * Instantiates a new Item.
     *
     * @param name the name
     */
    public Item(String name) {
        groupName = name;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
// Return the group name
    public String getName() {
        return groupName;
    }
}
