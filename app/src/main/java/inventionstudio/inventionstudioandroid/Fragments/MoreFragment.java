package inventionstudio.inventionstudioandroid.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import inventionstudio.inventionstudioandroid.Activities.LandingActivity;
import inventionstudio.inventionstudioandroid.R;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends PreferenceFragmentCompat {
    public static final String USER_PREFERENCES = "UserPrefs";

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        getActivity().setTitle("More");

        Preference button = findPreference("logout_button");
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //TODO: Clear sharedPreferences if user presses logout

//                SharedPreferences prefs = getActivity().getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
//                SharedPreferences.Editor editor = prefs.edit();
//                editor.clear();
//                editor.apply();

                getActivity().finish();
                return true;
            }
        });
    }

}
