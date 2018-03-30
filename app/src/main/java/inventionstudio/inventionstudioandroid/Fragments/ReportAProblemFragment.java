package inventionstudio.inventionstudioandroid.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import inventionstudio.inventionstudioandroid.Model.Machine;
import inventionstudio.inventionstudioandroid.Model.ToolBrokenFeedback;
import inventionstudio.inventionstudioandroid.R;

import static android.content.Context.MODE_PRIVATE;


public class ReportAProblemFragment extends MachineGroupFragment {


    public ReportAProblemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_report_a_problem, container, false);
        final Bundle bundle = getArguments();
        final Machine obj = (Machine) bundle.getSerializable("Machine");

        SharedPreferences prefs = getContext().getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        final String name = prefs.getString("name", "");
        final TextView nameText = (TextView) rootView.findViewById(R.id.name);
        nameText.setText(name);
        final Switch anonSwitch = (Switch) rootView.findViewById(R.id.anon_switch);
        anonSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    nameText.setText("Anonymous");
                } else {
                    nameText.setText(name);
                }
            }
        });

        final EditText textInput = (EditText) rootView.findViewById(R.id.plain_text_input);
        //textInput.setBackgroundResource(R.drawable.edittext_border);

        final Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.machine_feedback_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Button functionality
        final Button submit = (Button) rootView.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getContext().getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
                String username;

                // choose which username to use based on anonymity or not
                if (anonSwitch.isChecked()) {
                    username = "anonymous";
                } else {
                    username = prefs.getString("username", "");
                }

                // Create feedback for tool broken
                ToolBrokenFeedback feedback = new ToolBrokenFeedback(username, textInput.getText().toString(),
                        obj.getLocationName(),
                        obj.getToolName(),
                        spinner.getSelectedItem().toString());

            }
        });

        return rootView;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
