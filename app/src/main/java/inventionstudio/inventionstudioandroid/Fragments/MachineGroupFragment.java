package inventionstudio.inventionstudioandroid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
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
public class MachineGroupFragment extends Fragment {


    public static final String BASE_URL = "https://sums.gatech.edu/SUMSAPI/rest/API/";
    private static Retrofit retrofit = null;
    private HashSet<String> groups;
    private ListView listView;
    private Call<List<Machine>> call;


    public MachineGroupFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //TODO: Programmatically populate the machine groups
        getActivity().setTitle("Machine Groups");

        View rootView = inflater.inflate(R.layout.fragment_machine_group, container, false);

        listView = (ListView) rootView.findViewById(R.id.listview);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                String str = o.toString();

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment2 = new EquipmentListFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("MachineGroup", str);
                fragment2.setArguments(bundle);
                // Replace the contents of the container with the new fragment
                ft.replace(R.id.fragment_container, fragment2);
                ft.addToBackStack(null);
                // or ft.add(R.id.your_placeholder, new FooFragment());
                // Complete the changes added above
                ft.commit();

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


    public void connectAndGetApiData() {
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
                groups = new HashSet<>();
                for (Machine m : e) {
                    if (!(m.getLocationName().equals(""))) {
                        groups.add(m.getLocationName());
                    }

                }
                ArrayList<String> groupList = new ArrayList<>(groups);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_list_item_1, groupList);

                listView.setAdapter(adapter);


            }
            @Override
            public void onFailure(Call<List<Machine>> call, Throwable throwable) {
                Log.e("REST", throwable.toString());
            }
        });


    }

}


