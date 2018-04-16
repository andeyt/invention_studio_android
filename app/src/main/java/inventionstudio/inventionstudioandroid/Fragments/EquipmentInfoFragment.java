package inventionstudio.inventionstudioandroid.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import inventionstudio.inventionstudioandroid.API.SumsApiService;
import inventionstudio.inventionstudioandroid.Model.Equipment;
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
public class EquipmentInfoFragment extends EquipmentGroupFragment {
    public static final String USER_PREFERENCES = "UserPrefs";
    public static final String BASE_URL = "https://sums.gatech.edu/SUMSAPI/rest/API/";
    private ImageView statusIcon;
    private TextView statusText;
    private TextView description;
    private Retrofit retrofit;
    private String machineName;
    private String machineGroup;
    private ImageView image;
    private Call<List<Equipment>> call;
    private ProgressBar loadProgress;
    private SwipeRefreshLayout refreshLayout;

    public EquipmentInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_equipment_info, container, false);

        Bundle bundle = getArguments();
        machineName = ((Equipment) bundle.getSerializable("Equipment")).getToolName();
        machineGroup = ((Equipment) bundle.getSerializable("Equipment")).getLocationName();
        statusIcon = rootView.findViewById(R.id.status_icon);
        image = rootView.findViewById(R.id.imageView);
        statusText = rootView.findViewById(R.id.status_text);
        description = rootView.findViewById(R.id.machine_description);
        loadProgress = (ProgressBar) rootView.findViewById(R.id.progressBar);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeToRefresh);
        refreshLayout.setColorSchemeResources(R.color.IS_AccentPrimary_Light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                connectAndGetApiData();
            }
        });

        connectAndGetApiData();
        return rootView;
    }

    @Override
    public void onPause () {
        super.onPause();
        if (call != null) {
            call.cancel();
        }
    }

    public void connectAndGetApiData(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        SumsApiService sumsApiService = retrofit.create(SumsApiService.class);
        SharedPreferences prefs = getContext().getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        String username = prefs.getString("username", "");
        String otp = prefs.getString("otp", "");
        call = sumsApiService.getMachineList(8, username, otp);
        call.enqueue(new Callback<List<Equipment>>() {
            @Override
            public void onResponse(Call<List<Equipment>> call, Response<List<Equipment>> response) {
                List<Equipment> e = response.body();
                for (Equipment m : e) {
                    if (m.getToolName().equals(machineName)) {
                        statusIcon.setImageResource(m.statusIcon());

                        statusText.setText(m.statusText());

                        description.setText(Html.fromHtml(m.getToolDescription()));

                        loadProgress.setVisibility(View.GONE);
                        refreshLayout.setRefreshing(false);
                        break;
                    }
                }
                String formattedGroup = machineGroup.replaceAll("[^A-Za-z0-9\\s]", " ")
                        .replaceAll(" +", " ")
                        .trim()
                        .replace(" " , "_")
                        .toLowerCase();

                String formattedEquipment = machineName.replaceAll("[^A-Za-z0-9\\s]", " ")
                        .replaceAll(" +", " ")
                        .trim()
                        .replace(" " , "_")
                        .toLowerCase();

                Log.d("Formatted", formattedGroup);
                Log.d("Formatted", formattedEquipment);
                Picasso.get()
                        .load("https://is-apps.me.gatech.edu/resources/images/tools/"
                                + formattedGroup
                                + "/"
                                + formattedEquipment
                                + ".jpg")
                        .into(image, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                loadProgress.setVisibility(View.GONE);
                                refreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });

            }
            @Override
            public void onFailure(Call<List<Equipment>> call, Throwable throwable) {
                loadProgress.setVisibility(View.GONE);
                refreshLayout.setRefreshing(false);
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
