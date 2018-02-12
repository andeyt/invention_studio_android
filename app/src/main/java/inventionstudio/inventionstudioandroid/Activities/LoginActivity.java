package inventionstudio.inventionstudioandroid.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;

import inventionstudio.inventionstudioandroid.R;

//import edu.gatech.t_squaremobile.R;

public class LoginActivity extends Activity {
    public static final String USER_PREFERENCES = "UserPrefs";
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        webView = (WebView)findViewById(R.id.webView);
        webView.clearCache(true);
        webView.clearHistory();
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        webView.loadUrl("https://login.gatech.edu/cas/login?service=https://sums.gatech.edu/EditResearcherProfile.aspx");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView webView, String url) {
                try {
                    URL urlObj = new URL(url);
                    String baseURL = urlObj.getProtocol() + "://" + urlObj.getHost();
                    if (baseURL.equals("https://sums.gatech.edu")) {
                        // TODO: get the username and the OTP, waiting for Aman
                        // String username = getUserName()
                        // String otp = getOTP()
                        SharedPreferences.Editor editor = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE).edit();
                        // editor.putString("username", username);
                        // editor.putString("OTP", otp);
                        editor.apply();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
        webView.getSettings().setDomStorageEnabled(true);


        
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

