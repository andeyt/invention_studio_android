package inventionstudio.inventionstudioandroid.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.URL;

import inventionstudio.inventionstudioandroid.API.ServerApiService;
import inventionstudio.inventionstudioandroid.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity {

    public static final String USER_PREFERENCES = "UserPrefs";
    private WebView webView;
    private String otp;
    private String username;
    private View group;
    private Call call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup layout to accept the webview of GT login
        setContentView(R.layout.activity_login);
        webView = (WebView)findViewById(R.id.webView);
        webView.clearCache(true);
        webView.clearHistory();
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        group = findViewById(R.id.progress_group);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            /**
             * Show the SUMS login page in the activity
             */
            public void onPageStarted(WebView webView, String url, Bitmap b) {
                try {
                    URL baseURL = new URL(url);
                    String base = baseURL.getProtocol() + "://" + baseURL.getHost();
                    if (base.equals("https://sums.gatech.edu")) {
                        webView.setVisibility(View.GONE);
                        group.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            /**
             * method which takes the login to SUMS and navigates to the necessary pages
             * in order to grab the username and One-Time-Password of the user for
             * using in SUMS API authentication later
             */
            public void onPageFinished(WebView webView, String url) {
                try {
                    URL baseURL = new URL(url);
                    String base = baseURL.getProtocol() + "://" + baseURL.getHost();

                    // If we get to the correct base URL
                    if (base.equals("https://sums.gatech.edu")) {

                        // Grab the usermane by using javascript to parse the html
                        webView.setVisibility(View.GONE);
                        webView.evaluateJavascript("document.querySelector('[id$=\"UsernameDisplay\"]').innerText", new ValueCallback<String>() {

                            @Override
                            public void onReceiveValue(String s) {
                                username = s.substring(1, s.length() - 1);
                                SharedPreferences prefs = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();

                                // Log the username and store in shared preferences
                                Log.d("REST", username);
                                editor.putString("username", username);
                                editor.apply();
                            }
                        });

                        // Grab the One-Time-Password by using javascript to pare the html
                        webView.evaluateJavascript("document.querySelector('[id$=\"CalendarLink\"]').innerText", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                otp = s.split("=")[1];
                                otp = otp.substring(0, otp.length() - 1);
                                SharedPreferences prefs = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();

                                // Log the OTP and store in shared preferences
                                Log.d("REST", otp);
                                editor.putString("otp", otp);
                                editor.apply();
                            }
                        });

                        connectAndCheckTimestamp();

                    }
                } catch (Exception e) {

                }
            }

            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        LoginActivity.this);

                alertDialogBuilder.setTitle("Refresh page");
                alertDialogBuilder
                        .setMessage("Login page has failed to load. Would you like to try again?")
                        .setCancelable(false)
                        .setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                webView.reload();
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://login.gatech.edu/cas/login?service=https://sums.gatech.edu/EditResearcherProfile.aspx");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPause () {
        super.onPause();
        if (call != null) {
            call.cancel();
        }
    }

    /**
     * method to connect to our API and grab the current timestamp to add
     * to the app storage for autologin checks
     */
    public void connectAndCheckTimestamp() {

        // Create a retrofit to connect to our API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://is-apps.me.gatech.edu/api/v1-0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create API class and call timestamp to store it later
        ServerApiService serverApiService = retrofit.create(ServerApiService.class);
        call = serverApiService.getTimestamp();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                // If a successful response from the server, store login time
                if (response.isSuccessful()) {
                    try {
                        // Parse current time and store it in shared preferences
                        String curTimeString = response.body().string();
                        SharedPreferences prefs = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
                        long lastLoginTime = Long.parseLong(curTimeString);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putLong("lastLoginTime", lastLoginTime);
                        editor.commit();

                        // Send user to loading activity then to main activity
                        Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            /**
             * Any error connecting to the API will notify the user an error has occurred
             */
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(LoginActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });

    }


}

