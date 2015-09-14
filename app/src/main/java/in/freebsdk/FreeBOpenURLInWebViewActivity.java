package in.freebsdk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import in.freeb.sdk.R;
import in.freeb.sdk.utils.FreeBCommonUtility;
import in.freeb.sdk.utils.FreeBConstants;
import in.freeb.sdk.utils.FreebSDKApplication;
import in.freebsdk.views.ProgressWheel;

/**
 * Custom Browser Client to have control what url is opening and set
 * visibility of WebView to Visible once URL has loaded
 */
public class FreeBOpenURLInWebViewActivity extends Activity {

    private WebView webView;
    private ProgressWheel progressBar;
    private boolean isFromProductDetail;
    protected Dialog rateUsDialog;
    private String intentTag;
    private LinearLayout linearLayoutWebView;
    private SharedPreferences sharedPreferences;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // request the progress-bar feature for the activity
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.freeb_open_url_in_webview_activity);
        FreeBCommonUtility
                .setStatusBarColor(FreeBOpenURLInWebViewActivity.this);
        context = getApplicationContext();
        intentTag = getIntent().getStringExtra(
                FreeBConstants.HISTORY_INTENT_TAG);
        if (intentTag == null) {
            intentTag = "";
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this
                .getApplicationContext());

        // webView = (WebView) findViewById(R.id.webView);
        linearLayoutWebView = (LinearLayout) findViewById(R.id.linearLayoutWebView);
        webView = new WebView(this);
        webView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        linearLayoutWebView.addView(webView);
        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        progressBar = (ProgressWheel) findViewById(R.id.progressBar);

        String url = getIntent().getStringExtra("url");
        isFromProductDetail = getIntent().getBooleanExtra(
                "isFromProductDetail", false);
        webView.loadUrl(url);

        // set a webChromeClient to track progress
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // update the progressBar
                FreeBOpenURLInWebViewActivity.this.setProgress(progress * 100);
            }
        });

        webView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    WebView webView = (WebView) v;

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (webView.canGoBack()) {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });

    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // url =
            // "https://play.google.com/store/apps/details?id=in.freeb.app";

            try {
                String[] urls = sharedPreferences.getString("web_urls", "")
                        .split("\\|");
                for (int i = 0; i < urls.length; i++) {
                    if (url.equals(urls[i])) {
                        view.getContext().startActivity(
                                new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        return true;
                    }
                }
            } catch (Exception e) {
                // Crashlytics.logException(e);
            }
            if (url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                startActivity(intent);
                return true;
            }

            // else if (url != null &&
            // (url.startsWith("http://")||url.startsWith("https://"))) {
            // view.getContext().startActivity(
            // new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            // return true;
            // }
            // else if(url.startsWith("https://play.goole.com")){
            // try {
            // startActivity(new Intent(Intent.ACTION_VIEW,
            // Uri.parse("market://details?id=" + "in.freeb.app")));
            // } catch (android.content.ActivityNotFoundException anfe) {
            // startActivity(new Intent(Intent.ACTION_VIEW,
            // Uri.parse("https://play.google.com/store/apps/details?id=" +
            // "in.freeb.app")));
            // }
            // }
            else {
                try {
                    if (url.startsWith("whatsapp")) {
                        String shareText = url
                                .substring(url.indexOf("://") + 3);
                        PackageManager pm = getPackageManager();
                        try {
                            Intent waIntent = new Intent(Intent.ACTION_SEND);
                            waIntent.setType("text/plain");
                            @SuppressWarnings("unused")
                            PackageInfo info = pm.getPackageInfo(
                                    "com.whatsapp",
                                    PackageManager.GET_META_DATA);
                            // Check if package exists or not. If not then code
                            // in catch
                            // block will be called
                            waIntent.setPackage("com.whatsapp");
                            waIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                            startActivity(waIntent);

                        } catch (NameNotFoundException e) {
                            // Crashlytics.logException(e);
                            Toast.makeText(FreeBOpenURLInWebViewActivity.this,
                                    "WhatsApp not Installed",
                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(FreeBOpenURLInWebViewActivity.this,
                                    "WhatsApp not Installed",
                                    Toast.LENGTH_SHORT).show();
                            // Crashlytics.logException(e);
                        }
                    }
                } catch (Exception e) {
                    // Crashlytics.logException(e);
                }

                try {
                    if (!url.startsWith("whatsapp"))
                        view.loadUrl(url);
                } catch (Exception e) {
                    // Crashlytics.logException(e);
                    Log.e("FreeB", (e.getMessage() != null ? (e.getMessage())
                            : ""));
                }

            }

            return true;
        }

        public void onPageFinished(WebView view, String url) {
            try {
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                Log.e("FreeB", (e.getMessage() != null ? e.getMessage() : ""));
                // Crashlytics.logException(e);
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            if (errorCode == 404
                    || !FreeBCommonUtility.isNetworkAvailable(context))
                webView.loadUrl("file:///android_asset/error.html");
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

    @Override
    public void onDestroy() {

        if (webView != null) {
            webView.removeAllViews();
            webView.destroy();

            if (FreebSDKApplication.freeBOffersListener != null)
                FreebSDKApplication.freeBOffersListener.noOfferInstalled(
                        FreeBConstants.offersDismissCode,
                        FreeBConstants.offersDismissMessage);
        }
        webView = null;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (FreebSDKApplication.freeBOffersListener != null)
            FreebSDKApplication.freeBOffersListener.noOfferInstalled(
                    FreeBConstants.offersDismissCode,
                    FreeBConstants.offersDismissMessage);
    }
}