package inventionstudio.inventionstudioandroid.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import inventionstudio.inventionstudioandroid.Adapters.EquipmentAdapter;
import inventionstudio.inventionstudioandroid.Model.Equipment;
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
        Bundle bundle = getArguments();
        String str= (String) bundle.getSerializable("MachineGroup");

        getActivity().setTitle(str);


        View rootView = inflater.inflate(R.layout.fragment_equipment_list, container, false);
        ArrayList<Equipment> equipment = new ArrayList<>();
        equipment.add(new Equipment(2, "Machine A"));
        equipment.add(new Equipment(1, "Machine B"));
        equipment.add(new Equipment(1, "Machine C"));
        equipment.add(new Equipment(0, "Machine D"));
        equipment.add(new Equipment(2, "Machine E"));
        equipment.add(new Equipment(1, "Machine F"));
        equipment.add(new Equipment(2, "Machine G"));
        equipment.add(new Equipment(0, "Machine H"));
        equipment.add(new Equipment(2, "Machine J"));
        equipment.add(new Equipment(1, "Machine K"));
        equipment.add(new Equipment(0, "Machine L"));
        equipment.add(new Equipment(0, "Machine M"));
        equipment.add(new Equipment(2, "Machine N"));
        equipment.add(new Equipment(1, "Machine O"));
        equipment.add(new Equipment(0, "Machine P"));
        equipment.add(new Equipment(2, "Machine Q"));


        Collections.sort(equipment);

        EquipmentAdapter adapter = new EquipmentAdapter(getActivity(), R.layout.equipment_list_row, equipment);
        View header = (View)getActivity().getLayoutInflater().inflate(R.layout.equipment_list_header, null);

        final ListView listView = (ListView) rootView.findViewById(R.id.equipment_list);
        listView.setAdapter(adapter);
        listView.addHeaderView(header, null, false);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment2 = new MainEquipmentFragment();
                Bundle bundle = new Bundle();
                Equipment obj = ((Equipment) o);
                bundle.putSerializable("Equipment", obj);
                fragment2.setArguments(bundle);
                // Replace the contents of the container with the new fragment
                ft.replace(R.id.fragment_container, fragment2);
                ft.addToBackStack(null);
                // or ft.add(R.id.your_placeholder, new FooFragment());
                // Complete the changes added above
                ft.commit();
            }

        });
        return rootView;
    }


}
