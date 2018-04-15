package inventionstudio.inventionstudioandroid.Adapters;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import inventionstudio.inventionstudioandroid.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Maxwell Broom on 1/23/2018.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    // header titles
    private List<String> _listDataHeader;
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    public static final String USER_PREFERENCES = "UserPrefs";

    // Pass in headers as a list and a map of the header to the list of data
    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    /**
     * method to get the child at a position in its list
     */
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    /**
     * method which returns the position of a child in a list
     */
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    /**
     * method to set the name of the user to bold font if in the list
     */
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        // Get the name of the particular child, if it is the name of the current
        // user of the application, make it bold for easier viewing
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        SharedPreferences prefs = _context.getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        String name = prefs.getString("name", "");

        if (childText.contains(name)) {
            txtListChild.setTypeface(null, Typeface.BOLD);
        } else {
            txtListChild.setTypeface(null, Typeface.NORMAL);
        }
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    /**
     * method to return the size of a particular queue
     */
    public int getChildrenCount(int groupPosition) {
        List<String> s = this._listDataChild.get(this._listDataHeader.get(groupPosition));
        if (s == null) {
            return 0;
        } else {
            return s.size();
        }
    }

    @Override
    /**
     * method to return a header at a particular position
     */
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    /**
     * method to return how many headers or groups there are
     */
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    /**
     * method to return the group position
     */
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    /**
     * method to get the title of a particular group and set it as
     * the title in a textview
     */
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}