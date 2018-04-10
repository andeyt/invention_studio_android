package inventionstudio.inventionstudioandroid.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;

import inventionstudio.inventionstudioandroid.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {
    public static final String USER_PREFERENCES = "UserPrefs";
    private Button logoutButton;
    private Button creditsButton;

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_more, container, false);
        BottomNavigationView bottom =  (getActivity().findViewById(R.id.bottomBar));
        getActivity().setTitle(bottom.getMenu().findItem(bottom.getSelectedItemId()).getTitle());
        logoutButton = rootView.findViewById(R.id.logout_button);
        creditsButton = rootView.findViewById(R.id.credits_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure you wish to log out?");
                builder.setTitle("Log Out");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences prefs = getActivity().getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
                        String username = prefs.getString("username", "");
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.clear();
                        editor.apply();
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(username + "_android");
                        dialog.dismiss();
                        getActivity().finish();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        creditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new CreditsFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return rootView;
    }



}
