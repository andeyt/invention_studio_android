package inventionstudio.inventionstudioandroid.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import inventionstudio.inventionstudioandroid.API.ServerApiService;
import inventionstudio.inventionstudioandroid.Model.ToolBrokenFeedback;
import inventionstudio.inventionstudioandroid.R;
import okhttp3.Credentials;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandingActivity extends AppCompatActivity {
    public static final String USER_PREFERENCES = "UserPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //connectAndCheckTimestamp();

        SharedPreferences prefs = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);

//        if (prefs.contains("lastLoginTime")) {
//            long lastLoginTime = prefs.getLong("lastLoginTime", 0);
//            if (currentTime - lastLoginTime < -1) {
//                SharedPreferences.Editor editor = prefs.edit();
//                editor.remove("username");
//                editor.remove("otp");
//                editor.remove("name");
//                editor.commit();
//            }
//        }

        if (prefs.contains("username") && prefs.contains("otp")) {
            Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_landing);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.landing_activity_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_login:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void connectAndCheckTimestamp() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://is-apps.me.gatech.edu/api/v1-0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServerApiService serverApiService = retrofit.create(ServerApiService.class);
        Call<ResponseBody> generalCall = serverApiService.getTimestamp("771e6dd7-2d2e-4712-8944-7055ce69c9fb");
        generalCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("Code", Integer.toString(response.code()));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
