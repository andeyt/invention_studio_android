package inventionstudio.inventionstudioandroid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import inventionstudio.inventionstudioandroid.R;

/**
 * Created by maxim_000 on 3/12/2018.
 */

/**
 * Adapter for the equipment groups
 */
public class GroupAdapter extends ArrayAdapter<String> {

    Context context;
    int resource;
    ArrayList<String> groups;

    /**
     * Adapter for displaying the status of the equipment in the studio
     * @param context Context of the adapter
     * @param resource id of the resource that will be filled
     * @param groups data for the equipment groups
     */
    public GroupAdapter(Context context, int resource, ArrayList groups) {
        super(context, resource, groups);
        this.context = context;
        this.resource = resource;
        this.groups = groups;
    }

    /**
     * method to create a row for a group
     */
    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        View row = convertView;
        TextHolder holder = null;

        // Set the textview in the holder
        if (row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(resource, container, false);

            holder = new TextHolder();
            holder.name = (TextView) row.findViewById(R.id.lblListItem);

            row.setTag(holder);
        } else {
            holder = (TextHolder)row.getTag();
        }

        // Put the name of the group into the given textview
        String group = groups.get(position);
        if (group != null) {
            holder.name.setText(group);
        }

        return row;
    }

    // Class to hold the elements in the row of a group
    static class TextHolder {
        TextView name;
    }
}
