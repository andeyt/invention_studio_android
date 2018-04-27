package inventionstudio.inventionstudioandroid.Fragments;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;

import inventionstudio.inventionstudioandroid.API.SumsApiService;
import inventionstudio.inventionstudioandroid.Adapters.ExpandableListAdapter;
import inventionstudio.inventionstudioandroid.Model.QueueGroups;
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

    public static final String TAG = "Queue Fragment";
    public static final String USER_PREFERENCES = "UserPrefs";
    public static final String BASE_URL = "https://sums.gatech.edu/SUMSAPI/rest/API/";
    private static Retrofit retrofit = null;
    private ExpandableListAdapter adapter;
    private ExpandableListView expandableListView;
    private List<QueueGroups> groups;
    private ArrayList<String> queues;
    private HashMap<String, List<String>> queueData;
    private ProgressBar loadProgress;
    private SwipeRefreshLayout refreshLayout;
    private QueueTask queueTask;
    private Call<List<QueueMember>> queueMembersCall;
    private Call<List<QueueGroups>> queueGroupsCall;

    public QueueFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_queue, container, false);

        // Use bottom bar to set the title of the page
        BottomNavigationView bottom =  (getActivity().findViewById(R.id.bottomBar));
        getActivity().setTitle(bottom.getMenu().findItem(bottom.getSelectedItemId()).getTitle());
        expandableListView = rootView.findViewById(R.id.expandable_list);
        expandableListView.addHeaderView(new View(getContext()), null, true);

        // Set functionality when a group is expanded - close all other groups
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

        // Gives the queue data from the SUMS API
        loadProgress = (ProgressBar) rootView.findViewById(R.id.progressBar);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeToRefresh);
        refreshLayout.setColorSchemeResources(R.color.IS_AccentPrimary_Light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queueTask = new QueueTask();
                queueTask.execute();
            }
        });

        queueTask = new QueueTask();
        queueTask.execute();
        return rootView;
    }

    @Override
    public void onPause () {
        super.onPause();
        if (queueMembersCall != null) {
            queueMembersCall.cancel();
        }
        if (queueGroupsCall != null) {
            queueGroupsCall.cancel();
        }
    }

    /**
     * Method to get queue members from SUMS
     */
    public void connectAndGetQueueMembers(){

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
        String username = prefs.getString("username", "");
        String otp = prefs.getString("otp", "");
        final String name = prefs.getString("name", "");
        queueMembersCall = sumsApiService.getQueueMembers(8, username, otp);
        queueMembersCall.enqueue(new Callback<List<QueueMember>>() {
            @Override
            public void onResponse(Call<List<QueueMember>> call, Response<List<QueueMember>> response) {
                List<QueueMember> members = response.body();

                // Run through list of Queue members
                // Create Queue list as well as HashMap
                queueData = new HashMap<>();
                ArrayList<String> queueList = new ArrayList<>();
                for (QueueGroups g : groups) {
                    if (queueData.get(g.getName()) == null) {
                        queueData.put(g.getName(), new ArrayList<String>());
                    }
                }
                Collections.sort(members);

                // Iterate over all members in all queues
                for (QueueMember q : members) {
                    // Iterate over each queue
                    for (QueueGroups g : groups) {

                        // If the member is in this queue, add them to the queueData.
                        if (q.getQueueGroupId().equals(g.getId())) {
                            // If name is blank, do username
                            if (q.getMemberName().trim().equals("")) {
                                queueData.get(g.getName()).add(q.getMemberQueueLocation() + ". " + q.getMemberUserName());
                            } else {
                                queueData.get(g.getName()).add(q.getMemberQueueLocation() + ". " + q.getMemberName());
                            }
                            // Stop checking queues if the right one is found
                            break;
                        }
                    }
                }

                for (QueueGroups g : groups) {

                }

                // Set up a list of just the queue names to pass to the expandable list adapter
                queues = new ArrayList<>();
                for (QueueGroups g : groups) {
                    queues.add(g.getName());
                    ArrayList<String> names = (ArrayList<String>) queueData.get(g.getName());
                    for (String queueName: names) {
                        if (queueName.contains(name) && !queueList.contains(name)) {
                            queueList.add(g.getName());
                        }
                    }
                }

                // Sort the queue names
                Collections.sort(queues);
                for (String queue: queues) {
                    if (!queueList.contains(queue)) {
                        queueList.add(queue);
                    }
                }

                // Add "No users in queue" if no members are in the queue
                for (String name : queueList) {
                    if (queueData.get(name).size() == 0) {
                        queueData.get(name).add("No users in queue");
                    }
                }

                // Add data the expandable list and show it
                adapter = new ExpandableListAdapter(getActivity(), queueList, queueData);
                expandableListView.setAdapter(adapter);
                loadProgress.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);

            }

            /**
             * method to alert the user when there is an error in retrieving the queue data
             */
            @Override
            public void onFailure(Call<List<QueueMember>> call, Throwable throwable) {
                loadProgress.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Method to get queue groups from API
     */
    public void connectAndGetQueueGroups(){

        // Create the retrofit for building the API data
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SumsApiService sumsApiService = retrofit.create(SumsApiService.class);
        // Call to preferences to get username and OTP
        // Replace hardcoded args when work in Login is complete.
        SharedPreferences prefs = getContext().getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        String username = prefs.getString("username", "");
        String otp = prefs.getString("otp", "");
        queueGroupsCall = sumsApiService.getQueueGroups(8, username, otp);
        queueGroupsCall.enqueue(new Callback<List<QueueGroups>>() {

                @Override
                public void onResponse(Call<List<QueueGroups>> call, Response<List<QueueGroups>> response) {
                    groups = response.body();
                    // populate queueData with group and member data
                    connectAndGetQueueMembers();
                }

                /**
                 * method to alert the user when there is an error in retrieving queue data
                 */
                @Override
                public void onFailure(Call<List<QueueGroups>> call, Throwable throwable) {
                    loadProgress.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    /**
     * Class to handle async task
     */
    class QueueTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void ... voids) {
            connectAndGetQueueGroups();
            return null;
        }
    }
}
