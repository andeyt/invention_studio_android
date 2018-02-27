package inventionstudio.inventionstudioandroid.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import inventionstudio.inventionstudioandroid.Adapters.MyRecyclerViewAdapter;
import inventionstudio.inventionstudioandroid.Model.SimpleDividerItemDecoration;
import inventionstudio.inventionstudioandroid.R;

import static android.content.ContentValues.TAG;

public class themeFragment extends Fragment {

    MyRecyclerViewAdapter adapter;

    public themeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_theme, container, false);
        ArrayList<String> themes = new ArrayList<>();
        // Will need to add more available themes here
        themes.add("Light");
        themes.add("Dark");
        // Create view, set layout manager, adapter, and dividers
        RecyclerView list = rootView.findViewById(R.id.theme_list);
        list.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new MyRecyclerViewAdapter(this.getContext(), themes);
        list.setAdapter(adapter);
        list.addItemDecoration(new SimpleDividerItemDecoration(this.getContext()));

        return rootView;
    }

    // Deals with a click event
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: " + position);
    }
}