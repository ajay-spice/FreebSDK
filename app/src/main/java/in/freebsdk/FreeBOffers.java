package in.freebsdk;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.freeb.sdk.R;
import in.freeb.sdk.interfaces.FreeBOffersListener;
import in.freeb.sdk.interfaces.FreeBRegistrationListener;

import in.freeb.sdk.model_gson.FetchOffer;
import in.freeb.sdk.model_gson.FreeBOfferData;
import in.freeb.sdk.utils.FreeBCommonUtility;
import in.freeb.sdk.utils.FreeBConstants;
import in.freeb.sdk.utils.FreeBSDKLogger;
import in.freeb.sdk.utils.FreebSDKApplication;
import retrofit.mime.MultipartTypedOutput;

/**
 * The {@code FreeBOffers} class contains static methods to get offers from Freeb SDK server
 * and display in your project.
 *
 */
public class FreeBOffers implements
        FreeBRegistrationListener, FreeBOffersListener {

    private Context context;
    private Intent intent;
    private static double latitude, longitude;
    private static String affiliateId = "", affiliateAppId = "", uniqueId = "";
    private String tracking_id = "";
    private String offerTitle = "", offerTextColor = "", offerLayoutColor = "", itemId = "";

    protected MultipartTypedOutput multipartTypedOutput;
    protected static int isShown = 0;
    protected static boolean retryoffers;
    protected ProgressDialog pBar;
    protected List<FetchOffer> offersList;
    protected List<NameValuePair> nameValuePairs;
    SharedPreferences prefs;
    /**
     * Called to initialize class and set required parameters.This will perform
     * the functionality regarding offers to show in the application that are
     * available through library.
     *<p>
     *  The recommended way is to put a call to
     *  {@code FreeBOffers.setOnFreeBOffersListener(ClientActivity.this)} in your {@code Activity}'s {@code onCreate} method like below
     *</p>
     *
     * <pre>
     *  public class ClientActivity extends Activity implements FreeBOffersListener {
     *
     *       public void onCreate() {
     *              btn.setOnClickListener(new View.OnClickListener() {
     *                  public void onClick(View v) {
     *                      try {
     *                            // For fetching the offers and display on top of your activity.
     *                            FreeBOffers offers=new FreeBOffers(this);
     *                            offers.setOnFreeBOffersListener(this);
     *                            freeBOffers.setTitle("Pick Any Offer to unlock your premium features","#FFFFFF", "#FF6D00");
     *
     *                       } catch (Exception e) {}
     *                    }
     *              });
     *
     *      }
     *  }
     * </pre>
     *
     * @param context Context to get the resources
     */
    public FreeBOffers(Context context) {

        this.context = context;

        pBar = new ProgressDialog(context);
        pBar.setMessage("Please wait...");
        pBar.setCanceledOnTouchOutside(false);

        pBar.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                if (FreebSDKApplication.freeBOffersListener != null)
                    FreebSDKApplication.freeBOffersListener
                            .onDialogDismiss(FreeBConstants.diaglogDismiss);

            }
        });

        if (retryoffers) {
            setOnFreeBOffersListener(FreeBOffers.this);
        }
    }

    /**
     * This method is required if you intend to use your own title{@code offerTitle} ,
     * title color{@code offerTextColor} and title layout background color{@code offerLayoutColor}
     * for offers list display.
     *
     * You have to do like below to set your own title :-
     * <pre>
     *      FreeBOffers offers=new FreeBOffers(this);
     *      offers.setOnFreeBOffersListener(this);
     *      freeBOffers.setTitle("Pick Any Offer to unlock your premium features","#FFFFFF", "#FF6D00");
     * </pre>
     *
     * @param offerTitle-set       title of the screen according the requirement.
     * @param offerTextColor-set   text color of the title given.
     * @param offerLayoutColor-set background color of title layout.
     */
    public void setTitle(String offerTitle, String offerTextColor,
                         String offerLayoutColor) {

        this.offerTitle = offerTitle;
        this.offerTextColor = offerTextColor;
        this.offerLayoutColor = offerLayoutColor;
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putString(FreeBConstants.TITLE, offerTitle);
        editor.putString(FreeBConstants.TEXTCOLOR, offerTextColor);
        editor.putString(FreeBConstants.LAYOUTCOLOR, offerLayoutColor);
        editor.commit();

    }

    /**
     * This must be called to fetch the offers and display offers in your application.
     *
     * You must invoke this wherever you want to load offers either click on any button or in `onCreate()` of your activity {@code FreeBOffers.setOnFreeBOffersListener}
     *
     * <pre>
     *     button.setOnClickListener(new View.OnClickListener() {
     *         Override
     *         public void onClick(View v) {
     *             try {
     *                FreeBOffers offers=new FreeBOffers(this);
     *                offers.setOnFreeBOffersListener(this);
     *                freeBOffers.setTitle("Pick Any Offer to unlock your premium features","#FFFFFF", "#FF6D00");
     *             } catch (Exception e) {}
     *          }
     *      });
     * </pre>
     *
     * @param freeBOffersListener Library will have to register the instance of your application as a listener that may be called accordance to the response sent by the server.
     */


    @SuppressLint("NewApi")
    public void setOnFreeBOffersListener(FreeBOffersListener freeBOffersListener) {

        FreebSDKApplication.freeBOffersListener = freeBOffersListener;
        pBar.show();

        if (FreeBCommonUtility.isNetworkAvailable(context)) {  // check network connection is available
            serverCommunicationUsingVolley();
        } else {
            // In case of No Internet Connection, it will send the information to client app about the operation failure
            if (pBar.isShowing())
                pBar.dismiss();
            try {
                if (context.getResources().getString(R.string.showErrorMessage)
                        .equals("true")) {
                    FreeBCommonUtility
                            .showToast(
                                    context,
                                    context.getString(R.string.please_check_connection));
                }
                if (FreebSDKApplication.freeBOffersListener != null) {
                    FreebSDKApplication.freeBOffersListener
                            .onOffersFailed(
                                    FreeBConstants.offersFailedCode,
                                    context.getString(R.string.please_check_connection));
                }
            } catch (Exception e) {

                FreeBSDKLogger.e(FreeBCommonUtility.LOG_TAG,
                        e.getMessage() != null ? e.getMessage()
                                : FreeBConstants.WRONG);

            }
        }
    }







    /**
     * Called when offers are shown.
     *
     * @throws Exception some times exception occurs due to bad response
     */
    public void showOffers() throws Exception {
        if (pBar.isShowing())
            pBar.dismiss();
        if (FreeBCommonUtility.isAppOnForeground(context)) {

            intent = new Intent(context, FreeBOffersListActivity.class);
            intent.putParcelableArrayListExtra(FreeBConstants.OFFERS,
                    (ArrayList<? extends Parcelable>) offersList);
            context.startActivity(intent);
            if (FreebSDKApplication.freeBOffersListener != null) {
                FreebSDKApplication.freeBOffersListener.onShowOffers();
            }
            isShown++;
        } else {
            isShown = 0;

        }

    }


    @Override
    public void onRegistrationFailed(String code, String errorMessage) {
    }

    @Override
    public void onRegistrationSuccess(String code, String errorMessage) {

    }

    @Override
    public void onOffersFailed(String code, String errorMessage) {

    }

    @Override
    public void onOffersLoaded(String freeBOffers, String code) {

    }

    @Override
    public void onOffersInstallSuccess(String code, String response) {

    }

    @Override
    public void onOffersInstallFailure(String code, String response) {

    }

    @Override
    public void onShowOffers() {

    }

    @Override
    public void noOfferInstalled(String code, String errorMessage) {

    }

    @Override
    public void onLeaveApplication(String code, String errorMessage) {

    }

    @Override
    public void onDialogDismiss(String errorMessage) {

    }

    /**
     * This function will execute web service to fetch server data and load into
     * the application.
     */
    private void serverCommunicationUsingVolley(){
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        //String url = "http://www.spay.in/FreeBSDK/zip/fetchOffersAction";
        String url = FreeBConstants.CORE_REPORT_URL + "zip/fetchOffersAction";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (pBar.isShowing())
                                pBar.dismiss();
                            String result = response;
                            Gson gson = new Gson();
                            List<FreeBOfferData> freeBOfferDatas = Arrays.asList(gson.fromJson(result, FreeBOfferData.class));
                            Log.d("Response for Offers", freeBOfferDatas.toString());
                            retryoffers = false;
                            if (freeBOfferDatas.get(0).getStatus().equals("ok")) {

                                offersList = freeBOfferDatas.get(0).getPayload().getFetchOffers();
                                if (offersList == null) {

                                    if (context
                                            .getResources()
                                            .getString(
                                                    R.string.showErrorMessage)
                                            .equals("true")) {
                                        FreeBCommonUtility
                                                .showToast(context,
                                                        "Offers are not currently available");
                                    }
                                    FreebSDKApplication.freeBOffersListener
                                            .onOffersFailed(
                                                    freeBOfferDatas.get(0).getErrorCode(),
                                                    freeBOfferDatas.get(0).getMessage());
                                } else if (offersList.size() == 0) {


                                    if (context
                                            .getResources()
                                            .getString(
                                                    R.string.showErrorMessage)
                                            .equals("true")) {
                                        FreeBCommonUtility
                                                .showToast(context,
                                                        "Offers are not currently available");
                                    }
                                    if (FreebSDKApplication.freeBOffersListener != null) {
                                        FreebSDKApplication.freeBOffersListener
                                                .onOffersFailed(
                                                        freeBOfferDatas.get(0).getErrorCode(),
                                                        freeBOfferDatas.get(0).getMessage());
                                    }
                                } else if (offersList.size() > 0) {

                                    if (FreebSDKApplication.freeBOffersListener != null) {
                                        FreebSDKApplication.freeBOffersListener
                                                .onOffersLoaded(
                                                        freeBOfferDatas.get(0).getErrorCode(),
                                                        freeBOfferDatas.get(0).getMessage());
                                    }
                                    try {


                                            showOffers();

                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }

                                }
                            } else if (freeBOfferDatas.get(0).getStatus().equals("fail")) {



                                if (FreebSDKApplication.freeBOffersListener != null) {
                                    FreebSDKApplication.freeBOffersListener
                                            .onOffersFailed(
                                                    freeBOfferDatas.get(0).getErrorCode(),
                                                    freeBOfferDatas.get(0).getMessage());
                                }

                                SharedPreferences prefs = PreferenceManager
                                        .getDefaultSharedPreferences(context);

                                tracking_id = prefs.getString(
                                        FreeBConstants.ID, "");
                                if (tracking_id == null
                                        || tracking_id.equals("")) {
                                    if (context
                                            .getResources()
                                            .getString(
                                                    R.string.showErrorMessage)
                                            .equals("true")) {
                                        FreeBCommonUtility.showToast(context,
                                                "Connecting...");
                                    }
                                    retryoffers = true;
                                    affiliateId = prefs.getString(
                                            FreeBConstants.AFFILIATE_ID, "");
                                    affiliateAppId = prefs
                                            .getString(
                                                    FreeBConstants.AFFILIATE_APP_ID,
                                                    "");
                                    FreeBSDKRegistration.initialize(
                                            FreeBOffers.this, context, affiliateId,
                                            affiliateAppId);
                                } else {
                                    if (context
                                            .getResources()
                                            .getString(
                                                    R.string.showErrorMessage)
                                            .equals("true")) {
                                        FreeBCommonUtility.showToast(context,
                                                "Please try again");
                                    }
                                }

                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        if (pBar.isShowing())
                            pBar.dismiss();
                        if (context.getResources().getString(R.string.showErrorMessage)
                                .equals("true")) {
                            FreeBCommonUtility.showToast(context,
                                    error.getMessage() != null ? error.getMessage()
                                            : FreeBConstants.WRONG);
                        }
                        if (FreebSDKApplication.freeBOffersListener != null) {
                            FreebSDKApplication.freeBOffersListener.onOffersFailed(
                                    FreeBConstants.offersFailedCode,
                                    error.getMessage() != null ? error.getMessage()
                                            : FreeBConstants.WRONG);
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                String locationUser = "";

                try {
                    Location location = FreeBCommonUtility
                            .getReturnLocation(context);
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    try {
                        Geocoder geocoder = new Geocoder(context,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                latitude, longitude, 1);
                        if (addresses.size() > 0) {
                            locationUser = addresses.toString();
                        }
                    } catch (Exception e) {
                        // Crashlytics.logException(e);
                        FreeBSDKLogger.e(FreeBCommonUtility.LOG_TAG, e.getMessage());
                    }

                } catch (Exception e) {
                    latitude = 0.0;
                    longitude = 0.0;
                    FreeBSDKLogger.e(FreeBCommonUtility.LOG_TAG, e.getMessage());
                    // Crashlytics.logException(e);
                }

                affiliateId = prefs.getString(FreeBConstants.AFFILIATE_ID, "");
                affiliateAppId = prefs.getString(
                        FreeBConstants.AFFILIATE_APP_ID, "");
                uniqueId = prefs.getString(FreeBConstants.UNIQUE_ID, "");
                String density = FreeBCommonUtility.getDensity(context);
                String isAppStaging = FreeBCommonUtility.getAppStatus(context);

                HashMap<String, String> multipartTypedOutput = FreeBCommonUtility.getOffersParamsforVolley(
                        context, affiliateId, affiliateAppId, locationUser,
                        density, String.valueOf(latitude),
                        String.valueOf(longitude), isAppStaging, uniqueId);



                Map<String, String> params = new HashMap<>();
                params=multipartTypedOutput;
                return params;
            }
        };
        Volley.newRequestQueue(context).add(postRequest);

    }
}
