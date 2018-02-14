package inventionstudio.inventionstudioandroid.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import inventionstudio.inventionstudioandroid.R;
//import edu.gatech.t_squaremobile.R;


import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends Activity {
    WebView webView;
    Handler handlerForJavascriptInterface = new Handler();
    String page_html;
    String otp;
    String username;
    final String usernameDisplayId = "LiverpoolTheme_wt1_block_wtMainContent_SilkUIFramework_wt8_block_wtColumn2_wt14_SilkUIFramework_wt14_block_wtContent1_wt15_SilkUIFramework_wt382_block_wtPanelContent_SilkUIFramework_wt109_block_wtColumn2_SilkUIFramework_wt289_block_wtPanelContent_SilkUIFramework_wtBrief_block_wtContent_wtUsernameDisplay";
    final String OtpDisplayId = "LiverpoolTheme_wt1_block_wtMainContent_SilkUIFramework_wt8_block_wtColumn2_wt14_SilkUIFramework_wt14_block_wtContent1_wt15_SilkUIFramework_wt382_block_wtPanelContent_SilkUIFramework_wt109_block_wtColumn2_SilkUIFramework_wt289_block_wtPanelContent_SilkUIFramework_wtBrief_block_wtContent_wtCalendarLink";

    private class MyTask extends AsyncTask<Void, String, String[]>  {
        @Override
        protected String[] doInBackground(Void...a) {

            webView.evaluateJavascript("document.getElementById(\"" + usernameDisplayId + "\").innerText", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    username = s;
                    System.out.println("1: username: " + username);

                }
            });
            webView.evaluateJavascript("document.getElementById(\"" + OtpDisplayId + "\").innerText", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    otp = s.split("=")[1];
                    System.out.println("2: otp: " + otp);

                }
            });
            return new String[] {username, otp};
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);



        webView = (WebView)findViewById(R.id.webView);
        webView.clearCache(true);
        webView.clearHistory();
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        WebSettings websetting = webView.getSettings();
        websetting.setSaveFormData(true);


        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView webView, String url) {



                if (url.contains("EditResearcherProfile")) {
                    try {
                        String[] str_result= new MyTask().execute().get();
                        System.out.println("otp " + str_result[0] + " username " + str_result[1]);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
                if(otp != null && username != null) {
                    Intent homeIntent = new Intent(getApplicationContext(), AgreementActivity.class);
                    startActivity(homeIntent);
                    finish();
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
        webView.loadUrl("https://sums-dev.gatech.edu/EditResearcherProfile.aspx?");



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

