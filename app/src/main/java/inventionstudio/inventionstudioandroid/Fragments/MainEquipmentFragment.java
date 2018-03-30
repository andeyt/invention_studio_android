package inventionstudio.inventionstudioandroid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import info.hoang8f.android.segmented.SegmentedGroup;
import inventionstudio.inventionstudioandroid.Model.Machine;
import inventionstudio.inventionstudioandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainEquipmentFragment extends MachineGroupFragment {


    public MainEquipmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_equipment, container, false);
        final Bundle bundle = getArguments();
        Machine obj= (Machine) bundle.getSerializable("Machine");

        getActivity().setTitle(obj.getToolName());

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        Fragment fragment2 = new EquipmentInfoFragment();
        fragment2.setArguments(bundle);
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.equipment_fragment_container, fragment2);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();

        SegmentedGroup segmentedGroup = (SegmentedGroup) rootView.findViewById(R.id.segmented);

        segmentedGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.info) {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    // Replace the contents of the container with the new fragment
                    Fragment fragment2 = new EquipmentInfoFragment();
                    fragment2.setArguments(bundle);
                    // Replace the contents of the container with the new fragment
                    ft.replace(R.id.equipment_fragment_container, fragment2);
                    // or ft.add(R.id.your_placeholder, new FooFragment());
                    // Complete the changes added above
                    ft.commit();
                } else {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    // Replace the contents of the container with the new fragment
                    Fragment fragment2 = new ReportAProblemFragment();
                    fragment2.setArguments(bundle);
                    // Replace the contents of the container with the new fragment
                    ft.replace(R.id.equipment_fragment_container, fragment2);
                    // or ft.add(R.id.your_placeholder, new FooFragment());
                    // Complete the changes added above
                    ft.commit();
                }
            }
        });

        return rootView;
    }
}
