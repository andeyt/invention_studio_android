package inventionstudio.inventionstudioandroid.Activities;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;

import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.URL;
import java.util.List;

import inventionstudio.inventionstudioandroid.API.SumsApiService;
import inventionstudio.inventionstudioandroid.Model.UserGroups;
import inventionstudio.inventionstudioandroid.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;




public class LoginActivity extends Activity {
    public static final String USER_PREFERENCES = "UserPrefs";
    private WebView webView;
    private String otp;
    private String username;
    final String usernameDisplayId = "LiverpoolTheme_wt1_block_wtMainContent_SilkUIFramework_wt8_block_wtColumn2_wt14_SilkUIFramework_wt14_block_wtContent1_wt15_SilkUIFramework_wt382_block_wtPanelContent_SilkUIFramework_wt109_block_wtColumn2_SilkUIFramework_wt289_block_wtPanelContent_SilkUIFramework_wtBrief_block_wtContent_wtUsernameDisplay";
    final String OtpDisplayId = "LiverpoolTheme_wt1_block_wtMainContent_SilkUIFramework_wt8_block_wtColumn2_wt14_SilkUIFramework_wt14_block_wtContent1_wt15_SilkUIFramework_wt382_block_wtPanelContent_SilkUIFramework_wt109_block_wtColumn2_SilkUIFramework_wt289_block_wtPanelContent_SilkUIFramework_wtBrief_block_wtContent_wtCalendarLink";
    private View group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);



        webView = (WebView)findViewById(R.id.webView);
        webView.clearCache(true);
        webView.clearHistory();
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        group = findViewById(R.id.progress_group);


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView webView, String url, Bitmap b) {
                try {
                    URL baseURL = new URL(url);
                    String base = baseURL.getProtocol() + "://" + baseURL.getHost();
                    if (base.equals("https://sums-dev.gatech.edu")) {
                        webView.setVisibility(View.GONE);
                        group.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                try {
                    URL baseURL = new URL(url);
                    String base = baseURL.getProtocol() + "://" + baseURL.getHost();
                    if (base.equals("https://sums-dev.gatech.edu")) {
                        webView.setVisibility(View.GONE);
//                        webView.evaluateJavascript("document.getElementById(\"" + usernameDisplayId + "\").innerText", new ValueCallback<String>() {
                        webView.evaluateJavascript("document.querySelector('[id$=\"UsernameDisplay\"]').innerText", new ValueCallback<String>() {

                            @Override
                            public void onReceiveValue(String s) {
                                username = s.substring(1, s.length() - 1);
                                SharedPreferences prefs = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();

                                Log.d("REST", username);
                                editor.putString("username", username);
                                editor.apply();


                            }
                        });
                        webView.evaluateJavascript("document.querySelector('[id$=\"CalendarLink\"]').innerText", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                otp = s.split("=")[1];
                                otp = otp.substring(0, otp.length() - 1);
                                SharedPreferences prefs = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();

                                Log.d("REST", otp);
                                editor.putString("OTP", otp);
                                editor.apply();
                            }
                        });

                        Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        finish();
                    }

                    // TODO: get Server Time and put in shared prefs


                    // long lastLoginTime = getServerTime()


                    // SharedPreferences prefs = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
                    // SharedPreferences.Editor editor =prefs.edit();

                    // editor.putLong("lastLoginTime", lastLoginTime)


                    // editor.apply();

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
        webView.loadUrl("https://login.gatech.edu/cas/login?service=https://sums-dev.gatech.edu/EditResearcherProfile.aspx");



    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();

        }
    }


}

