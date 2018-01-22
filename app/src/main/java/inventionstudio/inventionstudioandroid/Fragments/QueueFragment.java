package inventionstudio.inventionstudioandroid.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import inventionstudio.inventionstudioandroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class QueueFragment extends Fragment {


    public QueueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Queue");

        return inflater.inflate(R.layout.fragment_queue, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inflate the layout for this fragment
        TextView text = view.findViewById(R.id.textView);
        text.setText("To join a queue, head to the kiosk closest to the machine" +
                " you're interested in. Find a PI (with a green armband) for more info.");
    }
}
