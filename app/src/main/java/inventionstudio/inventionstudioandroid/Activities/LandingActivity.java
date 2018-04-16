package inventionstudio.inventionstudioandroid.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RestrictTo;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import inventionstudio.inventionstudioandroid.API.ServerApiService;
import inventionstudio.inventionstudioandroid.API.SumsApiService;
import inventionstudio.inventionstudioandroid.Model.LoginFormObject;
import inventionstudio.inventionstudioandroid.Model.StudioDescription;
import inventionstudio.inventionstudioandroid.Model.ToolBrokenFeedback;
import inventionstudio.inventionstudioandroid.R;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandingActivity extends AppCompatActivity {

    public static final String USER_PREFERENCES = "UserPrefs";
    private Retrofit retrofit;
    private Call call;
    private ProgressBar loadProgress;
    private TextView studioDescriptionText;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        SharedPreferences prefs = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);

        // if the app already has username and otp data, auto login
        if (prefs.contains("username") && prefs.contains("otp")) {
            Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);
            startActivity(intent);
        }

        studioDescriptionText = findViewById(R.id.studio_description);
        loadProgress = (ProgressBar) findViewById(R.id.progressBar);
        image = (ImageView) findViewById(R.id.imageView);
        connectAndGetStudioDescription();
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

            // Send to login page if the user clicks the login button
            case R.id.action_login:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause () {
        super.onPause();
        if (call != null) {
            call.cancel();
        }
    }

    @Override
    public void onRestart () {
        super.onRestart();
        connectAndGetStudioDescription();
    }

    /**
     * method which connects to the SUMS API and grabs the studio description to display
     */
    public void connectAndGetStudioDescription() {

        // Create a retrofit to contact the API and pass in necessary arguments
        retrofit = new Retrofit.Builder()
                .baseUrl("https://sums.gatech.edu/SUMSAPI/rest/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Creates API class and requests the studio description
        SumsApiService sumsApiService = retrofit.create(SumsApiService.class);
        call = sumsApiService.getStudioDescription(8);
        call.enqueue(new Callback<StudioDescription>() {
            @Override
            public void onResponse(Call<StudioDescription> call, Response<StudioDescription> response) {

                // Sets correct visibility of the description and sets the body
                // to the data collected from the API
                StudioDescription description = response.body();
                studioDescriptionText.setText(Html.fromHtml(description.getEquipmentGroupDescriptionHtml()));
                Picasso.get()
                        .load(
                                "https://is-apps.me.gatech.edu/resources/images/headers/invention_studio.jpg")
                        .into(image, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                loadProgress.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });

            }
            @Override
            /**
             * If there is an issue connecting to the API show error
             */
            public void onFailure(Call<StudioDescription> call, Throwable throwable) {
                loadProgress.setVisibility(View.GONE);
            }
        });
    }


}
