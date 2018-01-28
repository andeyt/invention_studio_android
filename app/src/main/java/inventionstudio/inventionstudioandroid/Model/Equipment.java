package inventionstudio.inventionstudioandroid.Model;

import java.io.Serializable;
import java.util.Objects;

import inventionstudio.inventionstudioandroid.R;

/**
 * Created by Rishab K on 1/27/2018.
 */

public class Equipment implements Comparable, Serializable {
    private int iconID;
    private int icon;
    private String status;
    private String name;
    public Equipment(){
        super();
    }

    public Equipment(int iconID, String name) {
        super();
        this.iconID = iconID;
        switch (iconID) {
            case 0:
                this.icon = R.drawable.unavailable;
                this.status = "Unavailable";
                break;
            case 1:
                this.icon = R.drawable.in_use;
                this.status = "In Use";
                break;
            case 2:
                this.icon = R.drawable.available;
                this.status = "Available";
                break;
            default:
                this.icon = R.drawable.no_info;
                this.status = "No Information";
                break;
        }

        this.name = name;

    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int icon) {
        this.iconID = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Object equip) {
        int iconID =((Equipment) equip).getIconID();
        String name = ((Equipment) equip).getName();
        if (this.iconID == iconID) {
            return this.name.compareTo(name);
        }
        return iconID - this.iconID;
    }
}
