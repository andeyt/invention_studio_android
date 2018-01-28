package inventionstudio.inventionstudioandroid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import inventionstudio.inventionstudioandroid.Model.Equipment;
import inventionstudio.inventionstudioandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainEquipmentFragment extends Fragment {


    public MainEquipmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_equipment, container, false);


        final RadioButton rb1 = (RadioButton) rootView.findViewById(R.id.info);
        final RadioButton rb2 = (RadioButton) rootView.findViewById(R.id.problem);
        rb1.setChecked(true);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        Fragment fragment2 = new EquipmentInfoFragment();
        Bundle bundle = getArguments();
        fragment2.setArguments(bundle);
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.equipment_fragment_container, fragment2);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();

        rb1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(rb1.isChecked()){
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    // Replace the contents of the container with the new fragment
                    Fragment fragment2 = new EquipmentInfoFragment();
                    Bundle bundle = getArguments();
                    fragment2.setArguments(bundle);
                    // Replace the contents of the container with the new fragment
                    ft.replace(R.id.equipment_fragment_container, fragment2);
                    // or ft.add(R.id.your_placeholder, new FooFragment());
                    // Complete the changes added above
                    ft.commit();
                }
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(rb2.isChecked()){
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    // Replace the contents of the container with the new fragment
                    ft.replace(R.id.equipment_fragment_container, new ReportAProblemFragment());
                    // or ft.add(R.id.your_placeholder, new FooFragment());
                    // Complete the changes added above
                    ft.commit();
                }
            }
        });





        return rootView;
    }

}
