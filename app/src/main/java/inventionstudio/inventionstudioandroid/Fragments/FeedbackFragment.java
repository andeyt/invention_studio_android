package inventionstudio.inventionstudioandroid.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import inventionstudio.inventionstudioandroid.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {
    public static final String USER_PREFERENCES = "UserPrefs";

    public FeedbackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
        getActivity().setTitle("Feedback");


        SharedPreferences prefs = getContext().getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        final String name = prefs.getString("name", "");
        final TextView nameText = (TextView) rootView.findViewById(R.id.name);
        nameText.setText(name);


        Switch anonSwitch = (Switch) rootView.findViewById(R.id.anon_switch);
        // EditText Instantiation
        EditText textInput = (EditText) rootView.findViewById(R.id.plain_text_input);

        anonSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    nameText.setText("Anonymous");
                } else {
                    nameText.setText(name);
                }
            }
        });


        final Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.feedback_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = spinner.getSelectedItem().toString();
                View group = rootView.findViewById(R.id.rating_group);
                if (text.equals("PI Feedback")) {
                    group.setVisibility(View.VISIBLE);
                } else {
                    group.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return rootView;
    }

}
