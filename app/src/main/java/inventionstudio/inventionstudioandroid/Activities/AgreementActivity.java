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
                Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
