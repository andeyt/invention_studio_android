package inventionstudio.inventionstudioandroid.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class QueueFragment extends Fragment {

    public static final String USER_PREFERENCES = "UserPrefs";
    public static final String BASE_URL = "https://sums.gatech.edu/SUMSAPI/rest/API/";
    private static Retrofit retrofit = null;
    private ExpandableListAdapter adapter;
    private ExpandableListView expandableListView;
    private HashSet<String> queues;
    private HashMap<String, List<String>> queueData;
    private Call<List<QueueMember>> call;
    private ProgressBar loadProgress;
    private SwipeRefreshLayout refreshLayout;

    public QueueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Queue");
        View rootView = inflater.inflate(R.layout.fragment_queue, container, false);
        expandableListView = rootView.findViewById(R.id.expandable_list);
        // Gives the queue data from the SUMS API
        loadProgress = (ProgressBar) rootView.findViewById(R.id.progressBar);
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
        // Create the retrofit for building the API data
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        SumsApiService sumsApiService = retrofit.create(SumsApiService.class);
        // Call to preferences to get username and OTP
        // Replace hardcoded args when work in Login is complete.
        SharedPreferences prefs = getContext().getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        // String username = prefs.getString("username");
        // String otp = prefs.getString("otp");

        // TODO: Change to variables
        call = sumsApiService.getQueueLists(8, "rkaup3", "HYXUVGNMLR34MKYZT20T");
        call.enqueue(new Callback<List<QueueMember>>() {
            @Override
            public void onResponse(Call<List<QueueMember>> call, Response<List<QueueMember>> response) {
                List<QueueMember> members = response.body();
                System.out.println(members);
                // Run through list of Queue members
                // Create Queue list as well as HashMap
                queues = new HashSet<>();
                queueData = new HashMap<>();
                for (QueueMember q : members) {
                    // Filter out trash/test daya
                    if (q.getName().equals("reuse")) {
                        continue;
                    }
                    // Add queue name to Hashset, check if it has an accompanying list
                    queues.add(q.getName());
                    if (queueData.get(q.getName()) == null) {
                        // Add list as value of the queue key name
                        queueData.put(q.getName(), new ArrayList<String>());
                    }
                    // Add member to the queue list
                    if (q.getMemberName().trim().equals("")) {
                        // if memberName is blank, do username
                        queueData.get(q.getName()).add(q.getMemberUserName());
                    } else {
                        // use memberName otherwise
                        queueData.get(q.getName()).add(q.getMemberName());
                    }
                }
                // Setup and create ExpandableList
                ArrayList<String> queueList = new ArrayList<>(queues);
                adapter = new ExpandableListAdapter(getActivity(), queueList, queueData);
                expandableListView.setAdapter(adapter);
                loadProgress.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<List<QueueMember>> call, Throwable throwable) {
                loadProgress.setVisibility(View.GONE);
            }
        });
    }
}
