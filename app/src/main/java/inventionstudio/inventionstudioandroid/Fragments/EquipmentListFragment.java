package inventionstudio.inventionstudioandroid.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import inventionstudio.inventionstudioandroid.R;

/**
*/
public class EquipmentListFragment extends Fragment {



    public EquipmentListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_equipment_list, container, false);
        String[] testArray = {"RICK", "MORTY", "GOGO"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, testArray);

        ListView listView = (ListView) rootView.findViewById(R.id.listview);
        listView.setAdapter(adapter);
        return rootView;
    }


}
