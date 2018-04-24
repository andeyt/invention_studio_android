package inventionstudio.inventionstudioandroid.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import inventionstudio.inventionstudioandroid.API.SumsApiService;
import inventionstudio.inventionstudioandroid.Adapters.EquipmentAdapter;
import inventionstudio.inventionstudioandroid.Model.Equipment;
import inventionstudio.inventionstudioandroid.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
*/
public class EquipmentListFragment extends EquipmentGroupFragment {

    public static final String BASE_URL = "https://sums.gatech.edu/SUMSAPI/rest/API/";
    private static Retrofit retrofit = null;
    private ArrayList<Equipment> machines;
    private ListView listView;
    private String machineGroup;
    private TextView description;
    private Call<List<Equipment>> call;
    private ProgressBar loadProgress;
    private SwipeRefreshLayout refreshLayout;
    private ImageView image;

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
        image = header.findViewById(R.id.imageView);
        loadProgress = (ProgressBar) header.findViewById(R.id.progressBar);
        listView = (ListView) rootView.findViewById(R.id.equipment_list);

        description = (TextView) header.findViewById(R.id.group_description);
        listView.addHeaderView(header, null, true);
        listView.addFooterView(new View(getContext()), null, true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Object o = listView.getItemAtPosition(position);

                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    Fragment fragment2 = new MainEquipmentFragment();
                    Bundle bundle = new Bundle();
                    Equipment obj = ((Equipment) o);
                    bundle.putSerializable("Equipment", obj);
                    fragment2.setArguments(bundle);
                    // Replace the contents of the container with the new fragment
                    ft.replace(R.id.fragment_container, fragment2);
                    ft.addToBackStack(null);
                    // or ft.add(R.id.your_placeholder, new FooFragment());
                    // Complete the changes added above
                    ft.commit();
                }
            }

        });

        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeToRefresh);
        refreshLayout.setColorSchemeResources(R.color.IS_AccentPrimary_Light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                connectAndGetEquipmentList();
            }
        });

        connectAndGetEquipmentList();
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
     * Get equipment list from SUMS, and picture from server
     */
    public void connectAndGetEquipmentList(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        SumsApiService sumsApiService = retrofit.create(SumsApiService.class);
        SharedPreferences prefs = getContext().getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        String username = prefs.getString("username", "");
        String otp = prefs.getString("otp", "");
        call = sumsApiService.getEquipmentList(8, username, otp);
        call.enqueue(new Callback<List<Equipment>>() {
            @Override
            public void onResponse(Call<List<Equipment>> call, Response<List<Equipment>> response) {
                List<Equipment> e = response.body();

                machines = new ArrayList<>();
                for (Equipment m : e) {
                    if (m.getLocationName().equals(machineGroup)) {
                        // set compareVal
                        m.statusIcon();
                        machines.add(m);
                    }
                }

                Collections.sort(machines);
                EquipmentAdapter adapter = new EquipmentAdapter(getActivity(), R.layout.equipment_list_row, machines);

                listView.setAdapter(adapter);
                if (machines.isEmpty()) {
                    description.setText("No machines in this group");
                } else {
                    description.setText(Html.fromHtml(machines.get(0).getLocationDescription()));
                }

                String formattedGroup = machineGroup
                        .replaceAll("[^A-Za-z0-9\\s]", " ")
                        .replaceAll(" +", " ")
                        .trim()
                        .replace(" " , "_")
                        .toLowerCase();

                Log.d("Formatted", formattedGroup);
                // Getting picture of equipment group
                Picasso.get()
                        .load("https://is-apps.me.gatech.edu/resources/images/tools/"
                                + formattedGroup
                                + "/header.jpg")
                        .into(image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        loadProgress.setVisibility(View.GONE);
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<Equipment>> call, Throwable throwable) {
                loadProgress.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
