package inventionstudio.inventionstudioandroid.Fragments;


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
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import inventionstudio.inventionstudioandroid.API.SumsApiService;
import inventionstudio.inventionstudioandroid.Model.GeneralFeedback;
import inventionstudio.inventionstudioandroid.Model.Machine;
import inventionstudio.inventionstudioandroid.Model.PIFeedback;
import inventionstudio.inventionstudioandroid.Model.ToolBrokenFeedback;
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
    private HashSet<String> machineTypes;
    private Call<List<Machine>> call;
    private Spinner machineSpinner;
    private Spinner machineTypeSpinner;

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

        // Rating instantiation
        final SeekBar rating = (SeekBar) rootView.findViewById(R.id.seekBar);

        // EditText Instantiation
        final EditText commentTextInput = (EditText) rootView.findViewById(R.id.plain_text_input);
        final EditText piTextInput = (EditText) rootView.findViewById(R.id.piname_text_input);

        // Data for type of feedback
        final Spinner feedbackSpinner = (Spinner) rootView.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.feedback_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feedbackSpinner.setAdapter(adapter);

        // API machine data
        machineSpinner = (Spinner) rootView.findViewById(R.id.machine_spinner);
        machineTypeSpinner = (Spinner) rootView.findViewById(R.id.type_spinner);
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
                SharedPreferences prefs = getContext().getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
                String username;

                // choose which username to use based on anonymity or not
                if (anonSwitch.isChecked()) {
                    username = "anonymous";
                } else {
                    username = prefs.getString("username", "");
                }

                GeneralFeedback feedback;
                // Create the correct object based on the type of data being sent
                if (feedbackSpinner.getSelectedItem().toString().equals("PI Feedback")) {
                    feedback = new PIFeedback(username, commentTextInput.getText().toString(),
                            rating.getProgress(), piTextInput.getText().toString());
                } else if (feedbackSpinner.getSelectedItem().toString().equals("Machine Broken")) {
                    feedback = new ToolBrokenFeedback(username, commentTextInput.getText().toString(),
                            machineTypeSpinner.getSelectedItem().toString(),
                            machineSpinner.getSelectedItem().toString(),
                            issueSpinner.getSelectedItem().toString());
                } else if (feedbackSpinner.getSelectedItem().toString().equals("General Feedback")) {
                    feedback = new GeneralFeedback(username, commentTextInput.getText().toString());
                } else {
                    feedback = new GeneralFeedback("NO USERNAME", "NO COMMENTS");
                }
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
                machineTypes = new HashSet<>();
                for (Machine m : e) {
                    machineTypes.add(m.getLocationName());
                    machineNames.add(m.getToolName());
                }
                Collections.sort(machineNames);

                ArrayAdapter<String> machineAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, machineNames);
                machineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                machineSpinner.setAdapter(machineAdapter);

                ArrayList<String> types = new ArrayList<>(machineTypes);
                ArrayAdapter<String> machineTypeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, types);
                machineTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                machineTypeSpinner.setAdapter(machineTypeAdapter);
            }

            @Override
            public void onFailure(Call<List<Machine>> call, Throwable throwable) {
                // What on failure with no progress bar?
            }
        });
    }
}
