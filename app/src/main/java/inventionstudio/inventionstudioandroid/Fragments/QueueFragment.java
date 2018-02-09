package inventionstudio.inventionstudioandroid.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import inventionstudio.inventionstudioandroid.API.SumsApiService;
import inventionstudio.inventionstudioandroid.Adapters.ExpandableListAdapter;
import inventionstudio.inventionstudioandroid.Model.QueueMember;
import inventionstudio.inventionstudioandroid.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class QueueFragment extends Fragment {

    public static final String BASE_URL = "https://sums.gatech.edu/SUMSAPI/rest/API/";
    private static Retrofit retrofit = null;
    ExpandableListAdapter adapter;
    ExpandableListView expandableListView;
    List<String> queues;
    HashMap<String, List<String>> queueData;

    public QueueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Queue");
        View rootView = inflater.inflate(R.layout.fragment_queue, container, false);

        return rootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inflate the layout for this fragment
        TextView text = view.findViewById(R.id.text);
        text.setText("To join a queue, head to the kiosk closest to the machine" +
                " you're interested in. Find a PI (with a green armband) for more info.");
        expandableListView = view.findViewById(R.id.expandable_list);
        // Gives the queue data from the SUMS API
        connectAndGetApiData();
    }

    public void connectAndGetApiData(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        SumsApiService sumsApiService = retrofit.create(SumsApiService.class);
        Call<List<QueueMember>> call = sumsApiService.getQueueLists(8, "rkaup3");
        call.enqueue(new Callback<List<QueueMember>>() {
            @Override
            public void onResponse(Call<List<QueueMember>> call, Response<List<QueueMember>> response) {
                List<QueueMember> members = response.body();
                System.out.println(members);
                // Run through list of Queue members
                // Create Queue list as well as HashMap
                String currentQueue = "";
                List<String> queueList = new ArrayList<>();
                for (QueueMember q : members) {
                    // set current queue name and add to queues if need be
                    if (currentQueue.equals("")) {
                        currentQueue = q.getName();
                        queues.add(currentQueue);
                    } else if (!currentQueue.equals(q.getName())) {
                        queueData.put(currentQueue, queueList);
                        queueList.clear();
                        currentQueue = q.getName();
                    }

                    // add memberName to list
                    queueList.add(q.getMemberName());
                }

                adapter = new ExpandableListAdapter(getActivity(), queues, queueData);
                expandableListView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<QueueMember>> call, Throwable throwable) {
                Log.e("REST", throwable.toString());
            }
        });
    }
}
