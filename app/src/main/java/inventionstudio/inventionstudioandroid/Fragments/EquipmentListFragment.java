package inventionstudio.inventionstudioandroid.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import inventionstudio.inventionstudioandroid.API.SumsApiService;
import inventionstudio.inventionstudioandroid.Adapters.MachineAdapter;
import inventionstudio.inventionstudioandroid.Model.Machine;
import inventionstudio.inventionstudioandroid.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
*/
public class EquipmentListFragment extends MachineGroupFragment {

    public static final String BASE_URL = "https://sums.gatech.edu/SUMSAPI/rest/API/";
    private static Retrofit retrofit = null;
    private ArrayList<Machine> machines;
    private ListView listView;
    private String machineGroup;
    private TextView description;
    private Call<List<Machine>> call;
    private ProgressBar loadProgress;
    private SwipeRefreshLayout refreshLayout;

    public EquipmentListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        machineGroup = (String) bundle.getSerializable("MachineGroup");

        getActivity().setTitle(machineGroup);


        View rootView = inflater.inflate(R.layout.fragment_equipment_list, container, false);
        View header = (View) getActivity().getLayoutInflater().inflate(R.layout.equipment_list_header, null);
        loadProgress = (ProgressBar) rootView.findViewById(R.id.progressBar);



        listView = (ListView) rootView.findViewById(R.id.equipment_list);
        description = (TextView) header.findViewById(R.id.group_description);
        listView.addHeaderView(header, null, false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment2 = new MainEquipmentFragment();
                Bundle bundle = new Bundle();
                Machine obj = ((Machine) o);
                bundle.putSerializable("Machine", obj);
                fragment2.setArguments(bundle);
                // Replace the contents of the container with the new fragment
                ft.replace(R.id.fragment_container, fragment2);
                ft.addToBackStack(null);
                // or ft.add(R.id.your_placeholder, new FooFragment());
                // Complete the changes added above
                ft.commit();
            }

        });

        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeToRefresh);
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                connectAndGetApiData();
            }
        });

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
        SharedPreferences prefs = getContext().getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        String username = prefs.getString("username", "");
        String otp = prefs.getString("OTP", "");
        call = sumsApiService.getMachineList(8, "rkaup3", "HYXUVGNMLR34MKYZT20T");
        call.enqueue(new Callback<List<Machine>>() {
            @Override
            public void onResponse(Call<List<Machine>> call, Response<List<Machine>> response) {
                List<Machine> e = response.body();

                machines = new ArrayList<>();
                for (Machine m : e) {
                    if (m.getLocationName().equals(machineGroup)) {
                        // set compareVal
                        m.statusIcon();
                        machines.add(m);
                    }

                }

                Collections.sort(machines);

                MachineAdapter adapter = new MachineAdapter(getActivity(), R.layout.equipment_list_row, machines);

                listView.setAdapter(adapter);
                if (machines.isEmpty()) {
                    description.setText("No machines in this group");
                } else {
                    description.setText(machines.get(0).getEquipmentGroupDescription());
                }
                loadProgress.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);





            }
            @Override
            public void onFailure(Call<List<Machine>> call, Throwable throwable) {
                loadProgress.setVisibility(View.GONE);
            }
        });
    }


}
