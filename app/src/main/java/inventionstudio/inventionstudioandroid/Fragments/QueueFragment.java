package inventionstudio.inventionstudioandroid.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import inventionstudio.inventionstudioandroid.ExpandableListAdapter;
import inventionstudio.inventionstudioandroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class QueueFragment extends Fragment {

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
        prepareListData();
        adapter = new ExpandableListAdapter(getActivity(), queues, queueData);
        expandableListView.setAdapter(adapter);
    }

    private void prepareListData() {
        // Here is where we will use the API to get all lists. Dummy data used now.
        queues = new ArrayList<>();
        queues.add("Queue 1");
        queues.add("Queue 2");
        queues.add("Queue 3");
        queues.add("Queue 4");

        // Only creating one child list here used in all queues
        // In reality there will be a different one for each queue
        queueData = new HashMap<>();
        List<String> people = new ArrayList<>();
        people.add("Maxwell");
        people.add("Rishab");
        people.add("Nick");
        people.add("Noah");
        people.add("Aman");

        queueData.put(queues.get(0), people);
        queueData.put(queues.get(1), people);
        queueData.put(queues.get(2), people);
        queueData.put(queues.get(3), people);
    }

}
