package in.freebsdk.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;

import java.util.Random;

import in.freeb.sdk.R;
import in.freeb.sdk.utils.FreeBCommonUtility;
import in.freeb.sdk.utils.FreeBConstants;
import in.freeb.sdk.utils.FreeBSDKLogger;
import in.freebsdk.FreeBOpenInterestActivity;
import in.freebsdk.FreeBOpenURLInWebViewActivity;
import in.freebsdk.database.FreeBDataBaseAdapter;
import in.freebsdk.views.ProgressWheel;

/**
* Inflating offer detail layout corresponds to offer selected by the user
*/
public class FreeBOffersDetail extends Fragment {

    private Context mContext;
    private TextView installImageView, tv_amount;
    private ImageView bannerImageView;
    private TextView descriptionTextView;
    private ImageView dealImageView;

    private TextView firstOfferName, firstOfferType, earncash;
    private ProgressWheel descriptionProgress, descriptionImageProgress;
    private AQuery aQuery;
    int bundle;
    private ImageButton cross;
    private String type; // action
    private double latitude = 0.0;
    private double longitude = 0.0;
    private Double accuracy;
    private RelativeLayout topViewDealDisplay;
    private String refernceId;
    private String bannerAction;
    private String itemId;


    public FreeBOffersDetail() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.freeb_offfer_detail, container,
                false);

        installImageView = (TextView) view.findViewById(R.id.installTextView);
        bannerImageView = (ImageView) view.findViewById(R.id.bannerImageView);
        descriptionTextView = (TextView) view
                .findViewById(R.id.descriptionTextView);

        earncash = (TextView) view.findViewById(R.id.earncash);
        dealImageView = (ImageView) view.findViewById(R.id.dealImageView);
        firstOfferName = (TextView) view.findViewById(R.id.dealNameTextView);
        firstOfferType = (TextView) view.findViewById(R.id.offerTypeTextView);
        tv_amount = (TextView) view.findViewById(R.id.tv_amount);
        topViewDealDisplay = (RelativeLayout) view
                .findViewById(R.id.topViewDealDisplay);
        descriptionProgress = (ProgressWheel) view
                .findViewById(R.id.descriptionProgress);
        descriptionImageProgress = (ProgressWheel) view
                .findViewById(R.id.descriptionImageProgress);

        mContext = getActivity();
        aQuery = new AQuery(getActivity());

        bundle = getArguments().getInt("POSITION");
        itemId = getArguments().getString(FreeBConstants.ITEMID);
        setOfferValues();

        if (FreeBCommonUtility.isNetworkAvailable(mContext)) {
            try {
                // getOfferDetail();

            } catch (Exception e) {
                // Crashlytics.logException(e);
            }
        } else
            FreeBCommonUtility.showAlertDialog(mContext,
                    getString(R.string.please_check_connection));

        // Called on fragment back pressed
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().getSupportFragmentManager().popBackStack();

                    return true;
                } else {
                    return false;
                }
            }
        });
        // initiate the installation process for the offer selected by the user on Upper Button Pressed
        topViewDealDisplay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (FreeBCommonUtility.isNetworkAvailable(mContext)) {
                    onInstallAction();
                } else {
                    if (mContext.getResources()
                            .getString(R.string.showErrorMessage)
                            .equals("true")) {
                        FreeBCommonUtility.showToast(mContext, mContext
                                .getString(R.string.please_check_connection));
                    }
                }
            }
        });
        // initiate the installation process for the offer selected by the user on Lower Button Pressed
        installImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (FreeBCommonUtility.isNetworkAvailable(mContext)) {
                    onInstallAction();
                } else {
                    if (mContext.getResources()
                            .getString(R.string.showErrorMessage)
                            .equals("true")) {
                        FreeBCommonUtility.showToast(mContext, mContext
                                .getString(R.string.please_check_connection));
                    }
                }
            }
        });

        bannerImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    String action = bannerAction.split("\\|")[0];
                    // String action = "SHARE_VIA_APPS";
                    String URL = bannerAction.split("\\|")[1];
                    // String URL = "";

                    if (action.equals("WEBBROWSER")) {
                        try {
                            Intent webBrowserIntent = new Intent(
                                    Intent.ACTION_VIEW, Uri.parse(URL));
                            startActivity(webBrowserIntent);
                        } catch (Exception e) {
                            // Crashlytics.logException(e);
                        }
                    } else if (action.equals("WEBVIEW")) {
                        try {
                            Intent webViewIntent = new Intent(mContext,
                                    FreeBOpenURLInWebViewActivity.class);
                            webViewIntent.putExtra("url", URL);
                            webViewIntent.putExtra("isFromProductDetail", true);
                            startActivity(webViewIntent);
                        } catch (Exception e1) {
                            // Crashlytics.logException(e1);
                        }
                    } else if (action.equals("SHARE_VIA_WATSAPP")) {

                        PackageManager pm = mContext.getPackageManager();
                        try {
                            Intent waIntent = new Intent(Intent.ACTION_SEND);
                            waIntent.setType("text/plain");
                            @SuppressWarnings("unused")
                            PackageInfo info = pm.getPackageInfo(
                                    "com.whatsapp",
                                    PackageManager.GET_META_DATA);
                            // Check if package exists or not. If not then code
                            // in
                            // catch
                            // block will be called
                            waIntent.setPackage("com.whatsapp");
                            waIntent.putExtra(Intent.EXTRA_TEXT, URL);
                            startActivity(waIntent);

                        } catch (NameNotFoundException e) {
                            Toast.makeText(mContext, "WhatsApp not Installed",
                                    Toast.LENGTH_SHORT).show();
                            // Crashlytics.logException(e);
                        } catch (Exception e) {
                            Toast.makeText(mContext, "WhatsApp not Installed",
                                    Toast.LENGTH_SHORT).show();
                            // Crashlytics.logException(e);
                        }

                    } else if (action.equals("SHARE_VIA_APPS")) {
                        // String referrelURL =
                        // PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(
                        // "REFERRAL_URL", "");
                        try {
                            Intent sendIntent = new Intent();
                            sendIntent.setType("text/plain");
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, URL);
                            startActivity(sendIntent);
                        } catch (Exception e) {
                            // Crashlytics.logException(e);
                            Toast.makeText(
                                    mContext,
                                    "No application found to perform this action",
                                    Toast.LENGTH_LONG).show();
                        }

                    } else if (action.equals("TWITTER")) {
                        try {
                            Intent webBrowserIntent = new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("twitter://user?screen_name="
                                            + URL));
                            startActivity(webBrowserIntent);
                        } catch (Exception e) {
                            // Crashlytics.logException(e);
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri
                                    .parse("https://twitter.com/" + URL)));
                        }
                    } else if (action.equals("FACEBOOK")) {
                        // fb://page/762757640440760
                        try {
                            Intent webBrowserIntent = new Intent(
                                    Intent.ACTION_VIEW, Uri.parse("fb://page/"
                                    + URL));
                            startActivity(webBrowserIntent);
                        } catch (Exception e) {
                            // Crashlytics.logException(e);
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri
                                    .parse("https://www.facebook.com/" + URL)));
                        }
                    }

                } catch (Exception e) {
                    // Crashlytics.logException(e);
                }

            }
        });

        return view;
    }

    /**
     * Get static values of offer from offer list and set on the detail screen
     */
    private void setOfferValues() {

        try {
            String installEarnText[] = FreeBOffersFragment.freeBOffersList
                    .get(bundle).getActionText().split("\\|");

            bannerAction = FreeBOffersFragment.freeBOffersList.get(bundle)
                    .getPromoImageAction();
            installImageView.setText(installEarnText[1]);

            earncash.setText(installEarnText[0]);
            descriptionTextView.setText(Html
                    .fromHtml(FreeBOffersFragment.freeBOffersList.get(bundle)
                            .getLongDescription()));
            descriptionTextView.setMovementMethod(LinkMovementMethod
                    .getInstance());
            descriptionProgress.setVisibility(View.GONE);
            descriptionTextView.setVisibility(View.VISIBLE);

            // PROMO IMAGE
            bannerImageView.setVisibility(View.VISIBLE);
            descriptionImageProgress.setVisibility(View.GONE);
            aQuery.id(bannerImageView).image(
                    FreeBOffersFragment.freeBOffersList.get(bundle)
                            .getPromoImage(), true, true, 0, 0, null,
                    AQuery.FADE_IN);
            aQuery.id(dealImageView).image(
                    FreeBOffersFragment.freeBOffersList.get(bundle)
                            .getIconUrl());

            firstOfferName.setText(FreeBOffersFragment.freeBOffersList.get(
                    bundle).getOfferName());
            firstOfferType.setText(FreeBOffersFragment.freeBOffersList.get(
                    bundle).getOfferCategory());
            tv_amount.setText(mContext.getResources().getString(R.string.rs)
                    + FreeBOffersFragment.freeBOffersList.get(bundle)
                    .getOfferPrice());

            try {

                String colorArray[] = FreeBOffersFragment.freeBOffersList
                        .get(bundle).getRgbCode().split("\\|");
                firstOfferName.setTextColor(Color.parseColor(colorArray[0]));
                firstOfferType.setTextColor(Color.parseColor(colorArray[1]));
                descriptionTextView.setTextColor(Color
                        .parseColor(colorArray[2]));
                earncash.setTextColor(Color.parseColor(colorArray[3]));
            } catch (Exception e) {
                // Crashlytics.logException(e);
            }
        } catch (Exception e) {
            // Crashlytics.logException(e);
        }

    }


    /**
     * Called when some offer will be consumed by the user, it will send the information regarding the offer installed by the user to the server.
     */
    private void onInstallAction() {

        Random rand = new Random();

        try {
            Location location = FreeBCommonUtility.getReturnLocation(mContext);
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            accuracy = 0.0;
        } catch (Exception e) {
            latitude = 0.0;
            longitude = 0.0;
            accuracy = 0.0;
            // Crashlytics.logException(e);
        }
        refernceId = rand.nextInt(Integer.MAX_VALUE) + 1 + ""
                + System.currentTimeMillis();
        type = FreeBOffersFragment.freeBOffersList.get(bundle)
                .getOfferAppAction();
        try {

            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(mContext);
            if (type.equalsIgnoreCase("GooglePlay")) {

                String URL = FreeBConstants.CORE_REPORT_URL
                        + ""
                        + FreeBConstants.APP_REPORTING
                        + "affiliateId="
                        + prefs.getString(FreeBConstants.AFFILIATE_ID, null)
                        + "&trackingDeviceId="
                        + prefs.getString(FreeBConstants.ID, null)
                        + "&mobileOs="
                        + "Android"
                        + "&osVersion="
                        + Build.VERSION.RELEASE
                        + "&sdkVersion="
                        + FreeBConstants.SDK_VERSION
                        + "&videoPlayTime="
                        + "0"
                        + "&bannerId="
                        + "0"
                        + "&interstitialId="
                        + "0"

                        + "&udf1="
                        + itemId
                        + "&udf2="
                        + prefs.getString(FreeBConstants.UNIQUE_ID, null)
                        + "&udf3="
                        + ""
                        + "&appStatusUserActivity ="
                        + FreeBCommonUtility.getAppStatus(mContext)
                        + "&udf4=&udf5="

                        + "&affiliateAppId="
                        + prefs.getString(FreeBConstants.AFFILIATE_APP_ID, null)

                        + "&serverReferenceId="
                        + FreeBOffersFragment.freeBOffersList.get(bundle)
                        .getServerReferenceId()
                        + "&appId="
                        + FreeBOffersFragment.freeBOffersList.get(bundle)
                        .getOfferId()
                        + "&linkName="
                        + FreeBOffersFragment.freeBOffersList.get(bundle)
                        .getLinkName() + "&appStatus=" + "INTEREST";

                FreeBDataBaseAdapter dataBaseAdapter = new FreeBDataBaseAdapter(
                        mContext);

                if (dataBaseAdapter.getOfferId(
                        FreeBOffersFragment.freeBOffersList.get(bundle)
                                .getPackageName()).equals(
                        FreeBOffersFragment.freeBOffersList.get(bundle)
                                .getOfferId())) {
                    dataBaseAdapter.updateData(
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getOfferName(),
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getPackageName(),
                            // "com.myntra.android",
                            FreeBConstants.APP_STATUS_INTEREST,
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getTimesInSeconds(),
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getOfferId(),
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getOfferPrice(), "APP",
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getOfferType(),
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getAlreadyConsumed(),
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getOfferCategory(),
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getLinkName(), refernceId, System
                                    .currentTimeMillis(), null,
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getServerReferenceId(), prefs.getString(
                                    "TRACKING_ID", null), itemId);
                } else {
                    dataBaseAdapter.insertData(
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getOfferName(),
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getPackageName(),
                            // "com.myntra.android",
                            FreeBConstants.APP_STATUS_INTEREST,
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getTimesInSeconds(),
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getOfferId(),
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getOfferPrice(), "APP",
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getOfferType(),
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getAlreadyConsumed(),
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getOfferCategory(),
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getLinkName(), refernceId, System
                                    .currentTimeMillis(), "false",
                            FreeBOffersFragment.freeBOffersList.get(bundle)
                                    .getServerReferenceId(), prefs.getString(
                                    "TRACKING_ID", null), itemId);
                }

                dataBaseAdapter.close();

                Intent mIntent = new Intent(mContext,
                        FreeBOpenInterestActivity.class);
                mIntent.putExtra("url", URL);
                startActivity(mIntent);

            } else if (type.equalsIgnoreCase("WEBBROWSER")) {
                try {
                    Intent mIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(FreeBOffersFragment.freeBOffersList.get(
                                    bundle).getLinkName()));
                    startActivity(mIntent);
                } catch (Exception e) {
                    // Crashlytics.logException(e);
                }
            } else if (type.equalsIgnoreCase("WebView")) {
                Intent mIntent = new Intent(mContext,
                        FreeBOpenURLInWebViewActivity.class);
                mIntent.putExtra("url", FreeBOffersFragment.freeBOffersList
                        .get(bundle).getLinkName());

                mIntent.putExtra("isFromProductDetail", true);
                startActivity(mIntent);
            } else if (type.equalsIgnoreCase("SPAY_WebView")) {
                String URL = FreeBConstants.CORE_REPORT_URL
                        + ""
                        + FreeBConstants.APP_REPORTING
                        + "affiliateId="
                        + prefs.getString("AFFILIATE_ID", null)
                        + "&trackingDeviceId="
                        + prefs.getString("TRACKING_ID", null)
                        + "&mobileOs="
                        + "Android"
                        + "&osVersion="
                        + Build.VERSION.RELEASE
                        + "&sdkVersion="
                        + FreeBConstants.SDK_VERSION
                        + "&videoPlayTime="
                        + "0"
                        + "&bannerId="
                        + "0"
                        + "&interstitialId="
                        + "0"

                        + "&udf1="
                        + ""
                        + "&udf2="
                        + ""
                        + "&udf3="
                        + ""
                        + "&udf4=&udf5="

                        + "&affiliateAppId="
                        + prefs.getString("AFFILIATEAPP_ID", null)

                        + "&serverReferenceId="
                        + FreeBOffersFragment.freeBOffersList.get(bundle)
                        .getServerReferenceId()
                        + "&appId="
                        + FreeBOffersFragment.freeBOffersList.get(bundle)
                        .getOfferId()
                        + "&linkName="
                        + FreeBOffersFragment.freeBOffersList.get(bundle)
                        .getLinkName() + "&appStatus=" + "INTEREST";
                try {
                    Intent mIntent = new Intent(mContext,
                            FreeBOpenURLInWebViewActivity.class);
                    mIntent.putExtra("url", URL);
                    startActivity(mIntent);
                } catch (Exception e) {
                    // Crashlytics.logException(e);
                }
            }
        } catch (Exception e) {
            // Crashlytics.logException(e);

            String get = e.toString();
            FreeBSDKLogger.e("Exceprtion", e.toString());
        }

    }

}