package inventionstudio.inventionstudioandroid.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import inventionstudio.inventionstudioandroid.API.SumsApiService;
import inventionstudio.inventionstudioandroid.Model.UserGroups;
import inventionstudio.inventionstudioandroid.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadingActivity extends AppCompatActivity {
    public static final String USER_PREFERENCES = "UserPrefs";
    private Retrofit retrofit;
    public static final String BASE_URL = "https://sums.gatech.edu/SUMSAPI/rest/API/";
    private Call<List<UserGroups>> call;
    private boolean studioMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_loading);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                connectAndGetApiData();
            }

        }, 2000L);


    }

    public void connectAndGetApiData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        SumsApiService sumsApiService = retrofit.create(SumsApiService.class);
        // Call to preferences to get username and OTP
        // Replace hardcoded args when work in Login is complete.
        SharedPreferences prefs = this.getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        String username = prefs.getString("username", "");
        String otp = prefs.getString("OTP", "");
        // TODO: Change to variables
        call = sumsApiService.getUserGroups(username, otp);
        call.enqueue(new Callback<List<UserGroups>>() {
            @Override
            public void onResponse(Call<List<UserGroups>> call, Response<List<UserGroups>> response) {
                List<UserGroups> groups = response.body();
                studioMember = false;
                if (groups != null) {
                    for (UserGroups u : groups) {
                        if (u.getEquipmentGroupId() == 8) {
                            studioMember = true;
                        }
                    }
                }

                if (studioMember) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), AgreementActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
            @Override
            public void onFailure(Call<List<UserGroups>> call, Throwable throwable) {
                Toast.makeText(getParent(), "An Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
