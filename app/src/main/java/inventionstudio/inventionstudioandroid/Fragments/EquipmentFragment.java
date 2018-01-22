package inventionstudio.inventionstudioandroid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import inventionstudio.inventionstudioandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipmentFragment extends Fragment {


    public EquipmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_equipment, container, false);
        // Inflate the layout for this fragment
        View.OnClickListener info_radio_listener = new View.OnClickListener(){
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                // Replace the contents of the container with the new fragment
                ft.replace(R.id.equipment_fragment_container, new EquipmentInfoFragment());
                // or ft.add(R.id.your_placeholder, new FooFragment());
                // Complete the changes added above
                ft.commit();
            }
        };

        RadioButton rb1 = (RadioButton) rootView.findViewById(R.id.info);
        RadioButton rb2 = (RadioButton) rootView.findViewById(R.id.problem);
        rb1.setOnClickListener(info_radio_listener);



        return rootView;
    }

}
