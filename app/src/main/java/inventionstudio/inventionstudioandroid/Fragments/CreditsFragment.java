package inventionstudio.inventionstudioandroid.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class CreditsFragment extends Fragment {
    public static final String USER_PREFERENCES = "UserPrefs";
    private Button logoutButton;

    public CreditsFragment() {
        // Required empty public constructor
    }

    /**
     * Method to create the fragment and instantiate its view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Credits");
        View rootView = inflater.inflate(R.layout.fragment_credits, container, false);
        return rootView;
    }

}
