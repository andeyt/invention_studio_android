package inventionstudio.inventionstudioandroid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import inventionstudio.inventionstudioandroid.API.SumsApiService;
import inventionstudio.inventionstudioandroid.Model.Machine;
import inventionstudio.inventionstudioandroid.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipmentInfoFragment extends MachineGroupFragment {
    private ImageView statusIcon;
    private TextView statusText;
    private TextView description;
    private Retrofit retrofit;
    private String machineName;
    private Call<List<Machine>> call;

    public EquipmentInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_equipment_info, container, false);

        Bundle bundle = getArguments();
        machineName = ((Machine) bundle.getSerializable("Machine")).getToolName();

        statusIcon = rootView.findViewById(R.id.status_icon);

        statusText = rootView.findViewById(R.id.status_text);

        description = rootView.findViewById(R.id.machine_description);

        connectAndGetApiData();

        return rootView;
    }

    @Override
    public void onPause () {
        super.onPause();
        if (call != null) {
            call.cancel();
        }


    }

    public void connectAndGetApiData(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        SumsApiService sumsApiService = retrofit.create(SumsApiService.class);
        call = sumsApiService.getMachineList(8);
        call.enqueue(new Callback<List<Machine>>() {
            @Override
            public void onResponse(Call<List<Machine>> call, Response<List<Machine>> response) {
                List<Machine> e = response.body();
                for (Machine m : e) {
                    if (m.getToolName().equals(machineName)) {
                        statusIcon.setImageResource(m.statusIcon());

                        statusText.setText(m.statusText());

                        description.setText(m.getToolDescription());

                        break;
                    }
                }


            }
            @Override
            public void onFailure(Call<List<Machine>> call, Throwable throwable) {
                Log.e("REST", throwable.toString());
            }
        });
    }

}
