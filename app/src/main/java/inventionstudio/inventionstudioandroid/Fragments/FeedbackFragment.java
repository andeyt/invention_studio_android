package inventionstudio.inventionstudioandroid.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import inventionstudio.inventionstudioandroid.API.SumsApiService;
import inventionstudio.inventionstudioandroid.Model.Machine;
import inventionstudio.inventionstudioandroid.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {
    public static final String USER_PREFERENCES = "UserPrefs";
    public static final String BASE_URL = "https://sums.gatech.edu/SUMSAPI/rest/API/";
    private static Retrofit retrofit = null;
    private ArrayList<String> machineNames;
    private Call<List<Machine>> call;
    private Spinner machineSpinner;

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
        anonSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    nameText.setText("Anonymous");
                } else {
                    nameText.setText(name);
                }
            }
        });

        // EditText Instantiation
        EditText commentTextInput = (EditText) rootView.findViewById(R.id.plain_text_input);
        EditText piTextInput = (EditText) rootView.findViewById(R.id.piname_text_input);

        // Data for type of feedback
        final Spinner feedbackSpinner = (Spinner) rootView.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.feedback_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feedbackSpinner.setAdapter(adapter);

        // API machine data
        machineSpinner = (Spinner) rootView.findViewById(R.id.machine_spinner);
        connectAndGetAPIData();

        // Machine broken data
        final Spinner issueSpinner = (Spinner) rootView.findViewById(R.id.issue_spinner);
        ArrayAdapter<CharSequence> issueAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.machine_feedback_array,
                android.R.layout.simple_spinner_item);
        issueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        issueSpinner.setAdapter(issueAdapter);

        // Button functionality
        final Button submit = (Button) rootView.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Submit functionality not implemented yet!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Nothing will happen, returned to feedback fragment
                        // Reset the page perhaps
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        feedbackSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = feedbackSpinner.getSelectedItem().toString();
                View ratingGroup = rootView.findViewById(R.id.rating_group);
                View issueGroup = rootView.findViewById(R.id.machine_issue_group);
                if (text.equals("PI Feedback")) {
                    ratingGroup.setVisibility(View.VISIBLE);
                    issueGroup.setVisibility(View.GONE);
                } else if (text.equals("Machine Broken")) {
                    ratingGroup.setVisibility(View.GONE);
                    issueGroup.setVisibility(View.VISIBLE);
                } else {
                    ratingGroup.setVisibility(View.GONE);
                    issueGroup.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootView;
    }

    public void connectAndGetAPIData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        SharedPreferences prefs = getContext().getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        SumsApiService sumsApiService = retrofit.create(SumsApiService.class);
        String username = prefs.getString("username", "");
        String otp = prefs.getString("otp", "");
        call = sumsApiService.getMachineList(8, username, otp);
        call.enqueue(new Callback<List<Machine>>() {
            @Override
            public void onResponse(Call<List<Machine>> call, Response<List<Machine>> response) {
                List<Machine> e = response.body();

                // Make list of all machine names for adding to the spinner
                machineNames = new ArrayList<>();
                for (Machine m : e) {
                    machineNames.add(m.getToolName());
                    Log.d(TAG, m.getToolName());
                }
                Log.d(TAG, machineNames.toString());
                Collections.sort(machineNames);
                Log.d(TAG, machineNames.toString());

                ArrayAdapter<String> machineAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, machineNames);
                machineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                machineSpinner.setAdapter(machineAdapter);
            }

            @Override
            public void onFailure(Call<List<Machine>> call, Throwable throwable) {
                // What on failure with no progress bar?
            }
        });
    }
}
