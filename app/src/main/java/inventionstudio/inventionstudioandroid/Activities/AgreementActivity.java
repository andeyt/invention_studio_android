package inventionstudio.inventionstudioandroid.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class AgreementActivity extends AppCompatActivity {

    public static final String USER_PREFERENCES = "UserPrefs";
    private Retrofit retrofit;
    public static final String BASE_URL = "https://sums.gatech.edu/SUMSAPI/rest/API/";
    private Call<List<UserGroups>> call;
    private boolean studioMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_agreement);

        Button submit = (Button) findViewById(R.id.agreementButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectAndGetApiData();
            }
        });
    }

    /**
     * method created to connect to the SUMS API and pull down the list of studio
     * members to confirm if user is a part of the studio
     */
    public void connectAndGetApiData() {

        // Create a retrofit to contact the API and pass in necessary arguments
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        // Create API class and grab user info from stored app data
        SumsApiService sumsApiService = retrofit.create(SumsApiService.class);
        SharedPreferences prefs = this.getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        String username = prefs.getString("username", "");

        // Call the API via arguments and set method enqueue to parse through
        // all members and find if the logged in user is a part of the studio
        String otp = prefs.getString("otp", "");
        call = sumsApiService.getUserGroups(username, otp);
        call.enqueue(new Callback<List<UserGroups>>() {
            @Override
            public void onResponse(Call<List<UserGroups>> call, Response<List<UserGroups>> response) {

                // Parse through list of usergroups for the user, if one is
                // the studio, log them in
                List<UserGroups> groups = response.body();
                studioMember = false;
                if (groups != null) {
                    for (UserGroups u : groups) {
                        if (u.getEquipmentGroupId() == 8) {
                            studioMember = true;
                        }
                    }
                }

                // If a part of the studio, send to main activity
                // else, show agreement page so they can sign it and log in
                if (studioMember) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AgreementActivity.this, "Please sign the User Agreement", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            /**
             * Give a toast if any issue occurs with connecting to the API
             */
            public void onFailure(Call<List<UserGroups>> call, Throwable throwable) {
                Toast.makeText(AgreementActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
