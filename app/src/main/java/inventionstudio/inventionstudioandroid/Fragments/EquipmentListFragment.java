package inventionstudio.inventionstudioandroid.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

        final ListView listView = (ListView) rootView.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                String str = o.toString();

                if (str.equals("RICK")) {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    // Replace the contents of the container with the new fragment
                    ft.replace(R.id.fragment_container, new EquipmentFragment());
                    ft.addToBackStack(null);
                    // or ft.add(R.id.your_placeholder, new FooFragment());
                    // Complete the changes added above
                    ft.commit();
                }
            }
        });
        return rootView;
    }


}
