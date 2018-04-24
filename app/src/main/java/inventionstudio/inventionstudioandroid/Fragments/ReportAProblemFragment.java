package inventionstudio.inventionstudioandroid.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import inventionstudio.inventionstudioandroid.API.ServerApiService;
import inventionstudio.inventionstudioandroid.Model.Equipment;
import inventionstudio.inventionstudioandroid.Model.ToolBrokenFeedback;
import inventionstudio.inventionstudioandroid.R;
import okhttp3.Credentials;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


public class ReportAProblemFragment extends EquipmentGroupFragment {
    private static Retrofit retrofit = null;
    private Call call;
    private EditText textInput;
    private Spinner spinner;
    public ReportAProblemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_report_a_problem, container, false);
        final Bundle bundle = getArguments();
        final Equipment obj = (Equipment) bundle.getSerializable("Equipment");

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

        textInput = (EditText) rootView.findViewById(R.id.plain_text_input);
        //textInput.setBackgroundResource(R.drawable.edittext_border);

        spinner = (Spinner) rootView.findViewById(R.id.spinner1);
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
                boolean fieldsFilled = true;

                // Show message if there are no comments on the problem
                if (textInput.getText().toString().trim().equals("")) {
                    showToast("Please give a description of your specific issue.");
                    fieldsFilled = false;
                }

                if (fieldsFilled) {
                    // choose which username to use based on anonymity or not
                    if (anonSwitch.isChecked()) {
                        username = "anonymous";
                    } else {
                        username = prefs.getString("username", "");
                    }

                    // Create feedback for tool broken
                    ToolBrokenFeedback feedback = new ToolBrokenFeedback(
                            8,
                            username,
                            spinner.getSelectedItem().toString(),
                            obj.getLocationName(),
                            obj.getToolName(),
                            textInput.getText().toString()
                    );
                    connectAndSendToolFeedback(feedback);

                }

            }
        });

        return rootView;
    }

    @Override
    public void onPause () {
        super.onPause();
        if (call != null) {
            call.cancel();
        }
    }

    /**
     * Send feedback to IS Server
     * @param feedback feedback to send to server
     */
    public void connectAndSendToolFeedback(ToolBrokenFeedback feedback) {

        retrofit = new Retrofit.Builder()
                .baseUrl("https://is-apps.me.gatech.edu/api/v1-0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SharedPreferences prefs = getContext().getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        ServerApiService serverApiService = retrofit.create(ServerApiService.class);
        String username = prefs.getString("username", "");
        String otp = prefs.getString("otp", "");
        call = serverApiService.sendToolFeedback(feedback, "771e6dd7-2d2e-4712-8944-7055ce69c9fb", Credentials.basic(username, otp));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(response.body().string());
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            textInput.setText("");
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "An Error Occurred Recording Feedback, Try Again Later", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Show toast
     * @param dialogText text of the toast
     */
    public void showToast(String dialogText) {
        Toast.makeText(getActivity(), dialogText, Toast.LENGTH_SHORT).show();
    }
}
