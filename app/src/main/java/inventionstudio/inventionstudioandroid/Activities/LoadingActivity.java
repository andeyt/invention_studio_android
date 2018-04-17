package inventionstudio.inventionstudioandroid.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import inventionstudio.inventionstudioandroid.API.ServerApiService;
import inventionstudio.inventionstudioandroid.API.SumsApiService;
import inventionstudio.inventionstudioandroid.Model.UserGroups;
import inventionstudio.inventionstudioandroid.R;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadingActivity extends AppCompatActivity {

    public static final String USER_PREFERENCES = "UserPrefs";
    private Retrofit retrofit;
    public static final String BASE_URL = "https://sums.gatech.edu/SUMSAPI/rest/API/";
    private Call call;
    private boolean studioMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        connectAndCheckTimestamp();
    }

    @Override
    public void onPause () {
        super.onPause();
        if (call != null) {
            call.cancel();
        }
    }

    /**
     * method which connects to the SUMS API and collects user info
     * and status that will be used later in the application
     */
    public void connectAndGetStudioMemberStatus() {

        // Create a retrofit to contact the API and pass in necessary arguments
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create API class, grab arguments from app storage, request API
        SumsApiService sumsApiService = retrofit.create(SumsApiService.class);
        SharedPreferences prefs = this.getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        String username = prefs.getString("username", "");
        String otp = prefs.getString("otp", "");
        call = sumsApiService.getUserGroups(username, otp);

        // Call to the API which parses through usergroups to find the user
        // and collect their name for later use
        call.enqueue(new Callback<List<UserGroups>>() {
            @Override
            public void onResponse(Call<List<UserGroups>> call, Response<List<UserGroups>> response) {
                List<UserGroups> groups = response.body();
                studioMember = false;
                if (groups != null) {
                    for (UserGroups u : groups) {
                        if (u.getEquipmentGroupId() == 8) {
                            studioMember = true;
                            SharedPreferences prefs = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();

                            editor.putString("name", u.getUserName());
                            editor.apply();
                        }
                    }
                }

                // if they are in fact a studio member, send to the main activity
                // else send to the agreement page to sign user agreement
                if (studioMember) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    String notification = getIntent().getStringExtra("notification");
                    intent.putExtra("notification", notification);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), AgreementActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
            @Override
            /**
             * if any connection error occurs, tell the user an error occured
             */
            public void onFailure(Call<List<UserGroups>> call, Throwable throwable) {
                Toast.makeText(LoadingActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * method created to check the timestamp of the last login and autologin
     * if it has been less than 7 days
     */
    public void connectAndCheckTimestamp() {

        // Create a retrofit to contact the API and pass in necessary arguments
        retrofit = new Retrofit.Builder()
                .baseUrl("https://is-apps.me.gatech.edu/api/v1-0/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getCertClient())
                .build();

        // Create API class and request
        ServerApiService serverApiService = retrofit.create(ServerApiService.class);
        call = serverApiService.getTimestamp();

        /**
         * method which gets the current login time and compares it to the last
         * login time to see if the app can autologin the user
         */
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        // Grab current and previous login times
                        String curTimeString = response.body().string();
                        SharedPreferences prefs = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
                        long lastLoginTime = prefs.getLong("lastLoginTime", 0);

                        Log.d("LOGIN_TIME", Long.toString(lastLoginTime));
                        Log.d("LOGIN_TIME", curTimeString);

                        // If there is a last login time, check if autologin is possible
                        if (prefs.contains("lastLoginTime")) {

                            // If last login is more than a week ago, remove saved info
                            // and request the user verify with GT login again.
                            // Else login and get info
                            if (Long.parseLong(curTimeString) - lastLoginTime > 604800) {
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.remove("username");
                                editor.remove("otp");
                                editor.remove("name");
                                editor.commit();
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoadingActivity.this);
                                builder.setMessage("It has been over a week since you've logged in.\n\n" +
                                        "Please re-authenticate with Georgia Tech!");
                                builder.setTitle("Login Timeout");
                                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                Handler mHandler = new Handler();
                                mHandler.postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        connectAndGetStudioMemberStatus();
                                    }

                                }, 1000L);
                            }


                        }
                    } else {
                        Toast.makeText(LoadingActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                        e.printStackTrace();
                }
            }

            @Override
            /**
             * If an API connection issue occurs, tell the user an error occured
             */
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(LoadingActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private OkHttpClient getCertClient() {
        OkHttpClient okHttpClient_client = new OkHttpClient();
        try {
            // Load CAs from an InputStream
            // (could be from a resource or ByteArrayInputStream or ...)
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            InputStream caInput = new BufferedInputStream(this
                    .getResources().openRawResource(R.raw.is_apps_me_gatech_edu_cert));
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
            TrustManager[] trustManagers = tmf.getTrustManagers();
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

            // Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[] { trustManager }, null);
            // Tell the okhttp to use a SocketFactory from our SSLContext
            okHttpClient_client = new OkHttpClient.Builder().sslSocketFactory(context.getSocketFactory(),trustManager).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return okHttpClient_client;
    }
}
