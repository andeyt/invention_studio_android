package inventionstudio.inventionstudioandroid.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import inventionstudio.inventionstudioandroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MachineGroupFragment extends Fragment {


    public MachineGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //TODO: Programmatically populate the machine groups
        getActivity().setTitle("Machine Groups");

        View rootView = inflater.inflate(R.layout.fragment_machine_group, container, false);


        //Build adapter
        String[] testArray = {"3D Printers", "Lasers", "Waterjet"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, testArray);

        final ListView listView = (ListView) rootView.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                String str = o.toString();

                if (str.equals("3D Printers")) {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    // Replace the contents of the container with the new fragment
                    ft.replace(R.id.fragment_container, new EquipmentListFragment());
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
