package inventionstudio.inventionstudioandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

//import edu.gatech.t_squaremobile.R;

public class LoginActivity extends Activity {
    String sessionName;
    String sessionId;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        webView = (WebView)findViewById(R.id.webView);
        webView.clearCache(true);
        webView.clearHistory();
        webView.loadUrl("https://login.gatech.edu/cas/login");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String url) {
                String cookies = CookieManager.getInstance().getCookie(url);
                Log.d("cookies", cookies);
                if (url.contains("logout")) {
                    webView.clearCache(true);
                    webView.loadUrl("https://login.gatech.edu/cas/login");
                }
                if (cookies.contains("CASTGT")) {
                    //webView.loadUrl(url);
                    // run "mockup"-specific code
                    cookies = CookieManager.getInstance().getCookie(url);
//                    String[] splitParams = url.split("\\?")[1].split("&");
                    String[] splitParams = cookies.split("=");
//                    String sessionName = splitParams[0].split("=")[1];
//                    String sessionId = splitParams[1].split("=")[1];
                    sessionName = splitParams[0];
                    sessionId = splitParams[1];

                    Log.d("cookies", sessionName + "\n" + sessionId);

                    Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(homeIntent);

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


        final Activity activity = this;
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 100);
            }
        });

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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri data = intent.getData();
        if(data != null) {
            if(data.getQueryParameter("sessionName") != null && data.getQueryParameter("sessionId") != null) {
                sessionName = data.getQueryParameter("sessionName");
                sessionId = data.getQueryParameter("sessionId");
            }
        }
        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity(homeIntent);
    }

}

