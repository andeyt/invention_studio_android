package inventionstudio.inventionstudioandroid.Fragments;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
        getActivity().setTitle("Queue");
        View rootView = inflater.inflate(R.layout.fragment_queue, container, false);
        expandableListView = rootView.findViewById(R.id.expandable_list);

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

    public void connectAndGetQueueMembers(){
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

        queueMembersCall = sumsApiService.getQueueMembers(8, username, otp);

        queueMembersCall.enqueue(new Callback<List<QueueMember>>() {
            @Override
            public void onResponse(Call<List<QueueMember>> call, Response<List<QueueMember>> response) {
                List<QueueMember> members = response.body();
                connectAndGetQueueGroups();
                // Run through list of Queue members
                // Create Queue list as well as HashMap
                queueData = new HashMap<>();
                ArrayList<String> queueList = new ArrayList<>();

                for (QueueMember q : members) {
                    for (QueueGroups g : groups) {
                        if (queueData.get(g.getName()) == null) {
                            queueData.put(g.getName(), new ArrayList<String>());
                            queueList.add(g.getName());
                        }
                        if (q.getQueueGroupId() == g.getId() && q.getIsGroup() == g.getIsGroup()) {
                            // If name is blank, do username
                            if (q.getMemberName().trim().equals(""))
                                queueData.get(g.getName()).add(Integer.toString(queueData.get(g.getName()).size() + 1) + ". " + q.getMemberUserName());
                            } else {
                                queueData.get(g.getName()).add(Integer.toString(queueData.get(g.getName()).size() + 1) + ". " + q.getMemberName());
                            }
                            // Stop checking queues if the right one is found
                            break;
                        }
//                    // Filter out trash/test data
//                    if (q.getQueueName().equals("reuse")) {
//                        continue;
//                    }
//                    if (queueData.get(q.getQueueName()) == null) {
//                        // Add list as value of the queue key name
//                        queueData.put(q.getQueueName(), new ArrayList<String>());
//                    }
//                    // Add member to the queue list
//                    if (q.getMemberName().trim().equals("")) {
//                        // if memberName is blank, do username
//                        queueData.get(q.getQueueName()).add(Integer.toString(queueData.get(q.getQueueName()).size() + 1) + ". " + q.getMemberUserName());
//                    } else {
//                        // use memberName otherwise
//                        queueData.get(q.getQueueName()).add(Integer.toString(queueData.get(q.getQueueName()).size() + 1) + ". " + q.getMemberName());
//                    }
                }
                adapter = new ExpandableListAdapter(getActivity(), queueList , queueData);
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


    public void connectAndGetQueueGroups(){
        // Create the retrofit for building the API data
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        SumsApiService sumsApiService = retrofit.create(SumsApiService.class);
        // Call to preferences to get username and OTP
        // Replace hardcoded args when work in Login is complete.
        SharedPreferences prefs = getContext().getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        String username = prefs.getString("username", "");
        final String name = prefs.getString("name", "");
        String otp = prefs.getString("otp", "");
        queueGroupsCall = sumsApiService.getQueueGroups(8, username, otp);

        queueGroupsCall.enqueue(new Callback<List<QueueGroups>>() {
                @Override
                public void onResponse(Call<List<QueueGroups>> call, Response<List<QueueGroups>> response) {
                    groups = response.body();

//                    ArrayList<String> queueList = new ArrayList<>();
//
//                    queues = new ArrayList<>();
//                    for (QueueGroups q : groups) {
//                        queues.add(q.getName());
//                        if (queueData.get(q.getName()) == null) {
//                            queueData.put(q.getName(), new ArrayList<>(Arrays.asList("No users in queue")));
//                        }
//                        ArrayList<String> names = (ArrayList<String>) queueData.get(q.getName());
//                        for (String queueName: names) {
//                            if (queueName.contains(name)) {
//                                queueList.add(q.getName());
//                            }
//                        }
//                    }
//
//                    // Setup and create ExpandableList
//                    Collections.sort(queues);
//                    for (String queue: queues) {
//                        if (queueList.contains(queue)) {
//                            continue;
//                        } else {
//                            queueList.add(queue);
//                        }
//                    }
//
//                    adapter = new ExpandableListAdapter(getActivity(), queueList , queueData);
//                    expandableListView.setAdapter(adapter);
//                    loadProgress.setVisibility(View.GONE);
//                    refreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(Call<List<QueueGroups>> call, Throwable throwable) {
                    loadProgress.setVisibility(View.GONE);
                }
            });
    }

    class QueueTask extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void ... voids) {
            connectAndGetQueueMembers();
            return null;
        }
    }
}
