/*
 * Copyright (c) 2015-present, Spice Digital.
 * All rights reserved.
 *
 */
package in.freebsdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Build;
import android.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.freeb.sdk.R;
import in.freeb.sdk.interfaces.FreeBRegistrationListener;
import in.freeb.sdk.utils.FreeBCommonUtility;
import in.freeb.sdk.utils.FreeBConstants;
import in.freeb.sdk.utils.FreeBSDKLogger;
import retrofit.RestAdapter;
import retrofit.mime.MultipartTypedOutput;

/**
 * The {@code FreeBSDKRegistration} class contains static functions that handle global configuration of the FreeB
 * library.
 * This will initialize the library in your projects and also completes the registration process in background.
 */
public class FreeBSDKRegistration {

    private static Context mContext;
    private static String freeBAffiliateId = "";
    private static String freeBAffiliateAppId = "";
    private static String freeBUniqueId = "";
    private static SharedPreferences prefs;
    private static double latitude;
    private static double longitude;


    private static FreeBRegistrationListener freeBRegistrationList;
    protected JsonObject curators = null;
    protected RestAdapter restAdapter = null;
    protected MultipartTypedOutput multipartTypedOutput;
    static boolean executeInitService, installationSuccess;

    /**
     * Authenticates this client as belonging to your application.
     * This must be called before your
     * application can use the FreeB library. The recommended way is to put a call to
     * {@code FreeBSDKRegistration.initialize} in your {@code Application}'s {@code onCreate} method:
     *
     * <pre>
     * public class MyApplication extends Application {
     *      public void onCreate() {
     *           FreeBSDKRegistration.initialize(this, this, &quot;your client key&quot;, &quot;your application id&quot;);
     *      }
     * }
     * </pre>
     *
     * Here FreeB SDK is considering device ID and IMEI as unique parameter to identify user.
     *
     * @param freeBRegistrationListener Library will have to register the instance of your application as a listener that may be called accordance to the response sent by the server.
     * @param context                   The active {@link Context} for your application.
     * @param affiliateAppId            The application id provided in the FreeB dashboard.
     * @param affiliateId               The client id provided in the FreeB dashboard.
     */
    public static void initialize(FreeBRegistrationListener freeBRegistrationListener,
                                  Context context, String affiliateId, String affiliateAppId) {

        // Thread creation for Uncaught Exception
        try {

            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable e) {
                    //Default thread which will be catch all the Uncaught Exceptions for the whole FreeB SDK library

                }
            });
        } catch (Exception e) {
            FreeBSDKLogger.e("FreeBSDKRegistration",
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }

        // initialize the listener reference in the library project
        freeBRegistrationList = freeBRegistrationListener;

        // save the ids for the future reference
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putString(FreeBConstants.AFFILIATE_ID, affiliateId);
        editor.putString(FreeBConstants.AFFILIATE_APP_ID, affiliateAppId);
        editor.commit();


        // initialize the ids in the library project
        freeBAffiliateId = affiliateId;
        freeBAffiliateAppId = affiliateAppId;
        installationSuccess = true;
        mContext = context;
        FreeBSDKLogger.d("Registration", "Initialization");

        if (freeBAffiliateId.equals("") || freeBAffiliateAppId.equals("")) {
            // In case of empty value for Affiliate ID (User id) or Affiliate App ID (App id)
            try {
                if (context.getResources().getString(R.string.showErrorMessage).equals("true")) {
                    FreeBCommonUtility.showToast(mContext, mContext
                            .getString(R.string.empty_value));
                }
                freeBRegistrationList.onRegistrationFailed(
                        FreeBConstants.initServiceSuccess,
                        mContext.getString(R.string.empty_value));

            } catch (Exception e) {

                FreeBSDKLogger.e("FreeBSDKRegistration",
                        e.getMessage() != null ? e.getMessage()
                                : FreeBConstants.WRONG);


            }
        } else {

            if (FreeBCommonUtility.isNetworkAvailable(mContext)) {   // check network connection is available
                if (prefs.getString(FreeBConstants.ID, null) == null
                        || prefs.getString(FreeBConstants.ID, null).equals("")
                        || !Build.VERSION.RELEASE.equals(prefs.getString(
                        FreeBConstants.VERSION, null))
                        || !FreeBCommonUtility.getDeviceId(mContext).equals(
                        (prefs.getString(FreeBConstants.DEVICE_ID, null)))) {
                    //completes the registration process in background
                    //doRegistration();
                    doRegistrationUsingVolley();
                }

            } else {
                // In case of No Internet Connection, it will send the information to client app about the operation failure
                try {
                    if (context.getResources().getString(R.string.showErrorMessage).equals("true")) {
                        FreeBCommonUtility.showToast(mContext, mContext
                                .getString(R.string.please_check_connection));
                    }
                    freeBRegistrationList.onRegistrationFailed(
                            FreeBConstants.initServiceSuccess,
                            mContext.getString(R.string.please_check_connection));

                } catch (Exception e) {

                    FreeBSDKLogger.e("FreeBSDKRegistration",
                            e.getMessage() != null ? e.getMessage()
                                    : FreeBConstants.WRONG);


                }
            }
        }

    }

    /**
     * Authenticates this client as belonging to your application.
     * This must be called before your
     * application can use the FreeB library. The recommended way is to put a call to
     * {@code FreeBSDKRegistration.initialize} in your {@code Application}'s {@code onCreate} method:
     *
     * <pre>
     * public class MyApplication extends Application {
     *     public void onCreate() {
     *          FreeBSDKRegistration.initialize(this, this, &quot;your client key&quot;, &quot;your application id&quot;,&quot;your unique id&quot;);
     *     }
     * }
     * </pre>
     *
     * Here Unique id will be like phone number or email etc which is unique to identify user.
     * If your application primary key is email then send email of your users in this parameter.
     *
     * @param freeBRegistrationListener Library will have to register the instance of your application as a listener that may be called accordance to the response sent by the server.
     * @param context                   The active {@link Context} for your application.
     * @param affiliateAppId            The application id provided in the FreeB dashboard.
     * @param affiliateId               The client id provided in the FreeB dashboard.
     * @param uniqueId                  Unique id will be Phone Number, Email or any param which is unique in your application to identify user.
     */

    // Overwrite function with different number of parameters
    public static void initialize(
            FreeBRegistrationListener freeBRegistrationListener,
            Context context, String affiliateId, String affiliateAppId,
            String uniqueId) {

        // Setup handler for uncaught exceptions.
        try {

            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable e) {
                    //Default Handler thread which will be catch all the Uncaught Exceptions for the whole FreeB SDK library

                }
            });
        } catch (Exception e) {
            FreeBSDKLogger.e("FreeBSDKRegistration",
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }

        // initialize the listener reference in the library project
        freeBRegistrationList = freeBRegistrationListener;

        // save the ids for the future use
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        Editor editor = prefs.edit();
        editor.putString(FreeBConstants.AFFILIATE_ID, affiliateId);
        editor.putString(FreeBConstants.AFFILIATE_APP_ID, affiliateAppId);
        editor.putString(FreeBConstants.UNIQUE_ID, uniqueId);
        editor.commit();

        // initialize the ids in the library project
        freeBAffiliateId = affiliateId;
        freeBAffiliateAppId = affiliateAppId;
        freeBUniqueId = uniqueId;
        installationSuccess = true;
        mContext = context;

        if (freeBAffiliateId.equals("") || freeBAffiliateAppId.equals("") || freeBUniqueId.equals("")) {
            // In case of empty value for Affiliate ID (User id) or Affiliate App ID (App id) or Unique ID
            try {
                if (context.getResources().getString(R.string.showErrorMessage).equals("true")) {
                    FreeBCommonUtility.showToast(mContext, mContext
                            .getString(R.string.empty_value2));
                }
                freeBRegistrationList.onRegistrationFailed(
                        FreeBConstants.initServiceSuccess,
                        mContext.getString(R.string.empty_value2));

            } catch (Exception e) {

                FreeBSDKLogger.e("FreeBSDKRegistration",
                        e.getMessage() != null ? e.getMessage()
                                : FreeBConstants.WRONG);


            }
        } else {
            if (FreeBCommonUtility.isNetworkAvailable(mContext)) {           // check network connection is available
                if (prefs.getString(FreeBConstants.ID, null) == null
                        || prefs.getString(FreeBConstants.ID, null).equals("")
                        || !Build.VERSION.RELEASE.equals(prefs.getString(
                        FreeBConstants.VERSION, null))
                        || !FreeBCommonUtility.getDeviceId(mContext).equals(
                        (prefs.getString(FreeBConstants.DEVICE_ID, null)))) {
                    //completes the registration process in background
                    //doRegistration();
                    doRegistrationUsingVolley();
                }

            } else {
                // In case of No Internet Connection, it will send the information to client app about the operation failure
                try {
                    if (context.getResources().getString(R.string.showErrorMessage)
                            .equals("true")) {
                        FreeBCommonUtility.showToast(mContext, mContext
                                .getString(R.string.please_check_connection));
                    }
                    freeBRegistrationList.onRegistrationFailed(
                            FreeBConstants.initServiceSuccess,
                            mContext.getString(R.string.please_check_connection));

                } catch (Exception e) {
                    FreeBSDKLogger.e(FreeBCommonUtility.LOG_TAG,
                            e.getMessage() != null ? e.getMessage()
                                    : FreeBConstants.WRONG);

                }
            }
        }
    }


    /**
     * A {@code doRegistrationUsingVolley} is used to run code requesting a registration for a
     * user.
     * <p>
     * The easiest way to use a Volley {@code StringRequest} is through an anonymous inner
     * class which performs operation using AsyncTask.
     * Volley mostly works with just two classes, RequestQueue and Request.
     * </p>
     *
     * <pre>
     * create a RequestQueue object by invoking one of Volley's convenience methods {@code Volley.newRequestQueue}, which manages worker threads and delivers the parsed results back to the main thread.
     * Override the {@code onResponse} function to specify what the callback should do after the
     * request is complete.
     *
     * String url = "http://http.org/html";
     *        // Request a string response
     *  StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
     *                    new Response.Listener() {
     *     public void onResponse(String response) {
     *            // Result handling
     *        System.out.println(response.substring(0,100));
     *    }
     *  }, new Response.ErrorListener() {
     *     public void onErrorResponse(VolleyError error) {
     *            // Error handling
     *         System.out.println("Something went wrong!");
     *         error.printStackTrace();
     *     }
     *  });
     *        // Add the request to the queue
     *  Volley.newRequestQueue(this).add(stringRequest);
     * </pre>
     *
     * For example, this sample code requests to perform the registration process reset for a user and calls a different function
     * depending on whether the request succeeded or not.
     */
    private static void doRegistrationUsingVolley() {
        prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        //String url = "https://www.spay.in/FreeBSDK/registerAction";
        String url = FreeBConstants.CORE_REPORT_URL + "registerAction";
        // Request a string response
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Result handling
                        try {
                            String result = response;
                            if (result != null && !result.equals("")) {
                                JSONObject mainObject;
                                try {
                                    mainObject = new JSONObject(result.toString());
                                    String status = mainObject.optString("status");

                                    if (status.equals("ok")) {

                                        Editor editor = prefs.edit();
                                        editor.putString(FreeBConstants.DEVICE_ID,
                                                FreeBCommonUtility.getDeviceId(mContext));
                                        editor.putString(FreeBConstants.VERSION,
                                                Build.VERSION.RELEASE);
                                        editor.putString(FreeBConstants.ID,
                                                mainObject.optString("trackingDeviceId"));
                                        editor.commit();

                                        freeBRegistrationList.onRegistrationSuccess(
                                                mainObject.optString("errorCode"),
                                                mainObject.optString("message"));
                                        if (FreeBOffers.retryoffers) {
                                            FreeBOffers offers = new FreeBOffers(mContext);
                                        }


                                        FreeBSDKLogger.d("FINAL STATUS FOR REGISTRATION", "SUCCESS");


                                    } else {
                                        if (mContext.getResources()
                                                .getString(R.string.showErrorMessage)
                                                .equals("true")) {
                                            FreeBCommonUtility.showToast(mContext,
                                                    mainObject.optString("message"));
                                        }
                                        freeBRegistrationList.onRegistrationFailed(
                                                mainObject.optString("errorCode"),
                                                mainObject.optString("message"));

                                        FreeBSDKLogger.d("REGISTRATION FAILED", mainObject.optString("message"));
                                        FreeBSDKLogger.d("REGISTRATION FAILED ERROR CODE", mainObject.optString("errorCode"));

                                    }
                                } catch (Exception e) {

                                    if (mContext.getResources()
                                            .getString(R.string.showErrorMessage)
                                            .equals("true")) {
                                        FreeBCommonUtility.showToast(mContext, e
                                                .getMessage() != null ? e.getMessage()
                                                : FreeBConstants.WRONG);
                                    }
                                    freeBRegistrationList.onRegistrationFailed(
                                            FreeBConstants.initServiceFailed, e
                                                    .getMessage() != null ? e.getMessage()
                                                    : FreeBConstants.WRONG);

                                    FreeBSDKLogger.e(FreeBConstants.initServiceFailed, e
                                            .getMessage() != null ? e.getMessage()
                                            : FreeBConstants.WRONG);

                                    // Crashlytics.logException(e);

                                }
                            } else {
                                if (mContext.getResources()
                                        .getString(R.string.showErrorMessage)
                                        .equals("true")) {
                                    FreeBCommonUtility.showToast(mContext,
                                            FreeBConstants.WRONG);
                                }
                                freeBRegistrationList.onRegistrationFailed(
                                        FreeBConstants.initServiceFailed,
                                        FreeBConstants.WRONG);

                                FreeBSDKLogger.d(FreeBConstants.initServiceFailed, FreeBConstants.WRONG);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error handling
                        error.printStackTrace();
                        if (mContext.getResources()
                                .getString(R.string.showErrorMessage)
                                .equals("true")) {
                            FreeBCommonUtility.showToast(mContext,
                                    FreeBConstants.WRONG);
                        }
                        freeBRegistrationList.onRegistrationFailed(
                                FreeBConstants.initServiceFailed,
                                FreeBConstants.WRONG);

                        FreeBSDKLogger.d(FreeBConstants.initServiceFailed, FreeBConstants.WRONG);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {

                FreeBSDKLogger.d("Registration", "Server Communication");

                try {
                    Location location = FreeBCommonUtility
                            .getReturnLocation(mContext);
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                } catch (Exception e) {
                    latitude = 0.0;
                    longitude = 0.0;
                    FreeBSDKLogger.e("FreeBSDKRegistration",
                            e.getMessage() != null ? e.getMessage()
                                    : FreeBConstants.WRONG);

                }


                String density = FreeBCommonUtility
                        .getDensity(mContext);

                String isAppStaging = FreeBCommonUtility
                        .getAppStatus(mContext);


                HashMap<String, String> nameValuePairs = FreeBCommonUtility
                        .getRegistrationParamsforVolley(mContext,
                                freeBAffiliateId, freeBAffiliateAppId,
                                density, String.valueOf(latitude),
                                String.valueOf(longitude), isAppStaging);

                Map<String, String> params = new HashMap<>();
                params = nameValuePairs;
                return params;
            }
        };
        // Add the request to the queue
        Volley.newRequestQueue(mContext).add(postRequest);

    }
}
