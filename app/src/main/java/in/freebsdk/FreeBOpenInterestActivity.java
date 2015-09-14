package in.freebsdk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import in.freeb.sdk.R;
import in.freeb.sdk.utils.FreeBCommonUtility;
import in.freeb.sdk.utils.FreeBConnectivity;
import in.freeb.sdk.utils.FreeBConstants;
import in.freeb.sdk.utils.FreeBSDKLogger;
import in.freeb.sdk.utils.FreebSDKApplication;
import in.freebsdk.views.ProgressWheel;

/**
 * This class set a webChromeClient to track the progress, When user show interest in a particular offer but not consume that offer.
 */
public class FreeBOpenInterestActivity extends Activity {

    private WebView webView;
    private ProgressWheel progressBar;
    private Timer timer;
    private String url = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // request the progress-bar feature for the activity
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.freeb_open_interest_url_in_webview_activity);
        try {
            webView = (WebView) findViewById(R.id.webView);
            webView.setWebViewClient(new MyBrowser());
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

            progressBar = (ProgressWheel) findViewById(R.id.progressBar);

            String url = getIntent().getStringExtra("url");
            webView.loadUrl(url);

            // set a webChromeClient to track progress
            webView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    // update the progressBar
                    FreeBOpenInterestActivity.this.setProgress(progress * 100);
                }
            });
            processTwoMinutes();
        } catch (Exception e) {
            // Crashlytics.logException(e);
            FreeBSDKLogger.e(FreeBCommonUtility.LOG_TAG,
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
    }

    /**
     * Custom Browser Client to have control what url is opening and set
     * visibility of WebView to Visible once URL has loaded
     */
    private class MyBrowser extends WebViewClient {
        private String appPackageName;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final String url) {
            if (url != null) {
                FreeBOpenInterestActivity.this.url = url;
                if (url.startsWith("market")
                        || url.contains("https://play.google.com")
                        || url.contains("http://play.google.com")) {
                    // google play opened successfully so cancel the timer.
                    timer.cancel();
                    try {
                        appPackageName = url.substring(url.indexOf("?id=") + 4);
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id="
                                            + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            // Crashlytics.logException(anfe);
                            startActivity(new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id="
                                            + appPackageName)));
                        } catch (Exception e) {
                            // Crashlytics.logException(e);
                            Toast.makeText(
                                    FreeBOpenInterestActivity.this,
                                    "No application found to perform this action",
                                    Toast.LENGTH_LONG).show();
                        }
                        finish();
                    } catch (Exception e) {
                        // Crashlytics.logException(e);
                        FreeBSDKLogger.e(FreeBCommonUtility.LOG_TAG, e
                                .getMessage() != null ? e.getMessage()
                                : FreeBConstants.WRONG);
                    }
                } else {
                    try {
                        view.loadUrl(url);
                    } catch (Exception e) {
                        FreeBSDKLogger.e(FreeBCommonUtility.LOG_TAG, e
                                .getMessage() != null ? e.getMessage()
                                : FreeBConstants.WRONG);
                        // Crashlytics.logException(e);
                    }
                }
            }
            return true;

        }

        public void onPageFinished(WebView view, String url) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Wait for two minutes, if something goes wrong then display the error
     * message to user.
     */
    private void processTwoMinutes() {
        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            FreeBCommonUtility
                                    .showAlertDialogAndFinishActivity(
                                            FreeBOpenInterestActivity.this,
                                            getString(R.string.something_went_wrong));
                        } catch (Exception e) {
                            // Crashlytics.logException(e);
                        }
                    }
                });
            }
        };
        timer = new Timer();

        // if Internet is fast then it will process for 1 min otherwise on slow
        // network it will do same for 2 mins
        if (FreeBConnectivity.isConnectedFast(this))
            timer.schedule(timertask, 22 * 1000);
        else
            timer.schedule(timertask, 40 * 1000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (FreebSDKApplication.freeBOffersListener != null)
            FreebSDKApplication.freeBOffersListener.noOfferInstalled(
                    FreeBConstants.offersDismissCode,
                    FreeBConstants.offersDismissMessage);
        if (webView != null) {
            webView.loadUrl("about:blank");
        }

        if (timer != null)
            timer.cancel();

    }

}