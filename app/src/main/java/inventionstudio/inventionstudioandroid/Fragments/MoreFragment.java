package inventionstudio.inventionstudioandroid.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import inventionstudio.inventionstudioandroid.Activities.LandingActivity;
import inventionstudio.inventionstudioandroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends PreferenceFragmentCompat {


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
                Intent intent = new Intent(getActivity(), LandingActivity.class);
                startActivity(intent);
                return true;
            }
        });
    }

}
