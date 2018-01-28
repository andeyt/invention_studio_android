package inventionstudio.inventionstudioandroid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import inventionstudio.inventionstudioandroid.Model.Equipment;
import inventionstudio.inventionstudioandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipmentInfoFragment extends Fragment {


    public EquipmentInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_equipment_info, container, false);
        Bundle bundle = getArguments();
        Equipment obj= (Equipment) bundle.getSerializable("Equipment");

        getActivity().setTitle(obj.getName());
        ImageView statusIcon = rootView.findViewById(R.id.status_icon);
        statusIcon.setImageResource(obj.getIcon());

        TextView statusText = rootView.findViewById(R.id.status_text);
        statusText.setText(obj.getStatus());


        return rootView;
    }

}
