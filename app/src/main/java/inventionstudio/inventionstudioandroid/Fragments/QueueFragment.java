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
    private HashSet<String> queues;
    private HashMap<String, List<String>> queueData;
    private ProgressBar loadProgress;
    private SwipeRefreshLayout refreshLayout;
    private QueueTask queueTask;

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
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
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
        if (queueTask != null) {
            queueTask.cancel(true);
        }
    }

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

        // TODO: Change to variables
        Call<List<QueueMember>> call = sumsApiService.getQueueMembers(8, username, otp);

        call.enqueue(new Callback<List<QueueMember>>() {
            @Override
            public void onResponse(Call<List<QueueMember>> call, Response<List<QueueMember>> response) {
                List<QueueMember> members = response.body();
                // Run through list of Queue members
                // Create Queue list as well as HashMap
                queueData = new HashMap<>();

                for (QueueMember q : members) {
                    int i = 1;
                    // Filter out trash/test daya
                    if (q.getQueueName().equals("reuse")) {
                        continue;
                    }
                    if (queueData.get(q.getQueueName()) == null) {
                        // Add list as value of the queue key name
                        queueData.put(q.getQueueName(), new ArrayList<String>());
                    }
                    // Add member to the queue list
                    if (q.getMemberName().trim().equals("")) {
                        // if memberName is blank, do username
                        queueData.get(q.getQueueName()).add(Integer.toString(queueData.get(q.getQueueName()).size() + 1) + ". " + q.getMemberUserName());
                    } else {
                        // use memberName otherwise
                        queueData.get(q.getQueueName()).add(Integer.toString(queueData.get(q.getQueueName()).size() + 1) + ". " + q.getMemberName());
                    }

                }
                connectAndGetQueueGroups();
            }
            @Override
            public void onFailure(Call<List<QueueMember>> call, Throwable throwable) {
                loadProgress.setVisibility(View.GONE);
            }
        });



    }

    public void connectAndGetQueueGroups(){
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

        // TODO: Change to variables
        Call<List<QueueGroups>> call = sumsApiService.getQueueGroups(8, username, otp);

        call.enqueue(new Callback<List<QueueGroups>>() {
                @Override
                public void onResponse(Call<List<QueueGroups>> call, Response<List<QueueGroups>> response) {
                    List<QueueGroups> groups = response.body();

                    queues = new HashSet<>();
                    for (QueueGroups q : groups) {
                        if (q.getIsGroup()) {
                            queues.add(q.getName());
                            if (queueData.get(q.getName()) == null) {
                                queueData.put(q.getName(), new ArrayList<String>());
                                queueData.get(q.getName()).add("No users in queue");

                            }
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
