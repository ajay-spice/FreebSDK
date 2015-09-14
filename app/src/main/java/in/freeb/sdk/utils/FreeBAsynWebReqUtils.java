package in.freeb.sdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.freebsdk.database.FreeBDataBaseAdapter;

/**
 * Provides utility functions for making request to call web services.
 */
public class FreeBAsynWebReqUtils {

    /**
     * Adding required values in NameValuePair for calling web services.
     * @param context     Context to get the resources
     * @param dbadapter   Instance of Database Adapter
     * @param packageName Application package name
     * @param appStatus   Tells us about the Application Mode (Staging, Production)
     * @return Request data to server
     */
    public static List<NameValuePair> getRequestParamsAppReporting(
            Context context, FreeBDataBaseAdapter dbadapter,
            String packageName, String appStatus) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        try {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(context);

            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.AFFILIATE_ID, prefs.getString(
                    FreeBConstants.AFFILIATE_ID, "")));
            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.AFFILIATE_APP_ID, prefs.getString(
                    FreeBConstants.AFFILIATE_APP_ID, "")));

            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.TRACKING_ID, prefs.getString(
                    FreeBConstants.ID, "")));
            nameValuePairs.add(new BasicNameValuePair("mobileOs", "Android"));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.VERSION,
                    Build.VERSION.RELEASE));
            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.MOBILE_SDK_VERSION,
                    FreeBConstants.SDK_VERSION));
            nameValuePairs.add(new BasicNameValuePair("serverReferenceId",
                    dbadapter.getAppServerReferanceId(packageName)));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.APP_ID,
                    dbadapter.getOfferId(packageName)));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.STAGING,
                    FreeBCommonUtility.getAppStatus(context)));
            nameValuePairs.add(new BasicNameValuePair("linkName", dbadapter
                    .getAppURL(packageName)));
            nameValuePairs.add(new BasicNameValuePair("videoPlayTime", "0"));

            nameValuePairs.add(new BasicNameValuePair("bannerId", "0"));
            nameValuePairs.add(new BasicNameValuePair("interstitialId", "0"));

            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.UDF1,
                    dbadapter.getItemId(packageName)));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.UDF2,
                    prefs.getString(FreeBConstants.UNIQUE_ID, "")));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.UDF3, ""));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.UDF4, ""));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.UDF5, ""));

            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.APP_STATUS, appStatus));

        } catch (Exception e) {

        }

        return nameValuePairs;
    }

    /**
     * Get request params for app reporting, Post data to server using volley
     * @param context Context to get the resources
     * @param dbadapter Instance of Database Adapter
     * @param packageName Application package name
     * @param appStatus  Tells us about the Application Mode (Staging, Production)
     * @return Request data to server
     */
    public static HashMap<String, String> getRequestParamsAppReportingforVolley(
            Context context, FreeBDataBaseAdapter dbadapter,
            String packageName, String appStatus) {
        HashMap<String, String> nameValuePairs = new  HashMap<String, String>();
        try {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(context);

            nameValuePairs.put(
                    FreeBConstants.AFFILIATE_ID, prefs.getString(
                            FreeBConstants.AFFILIATE_ID, ""));
            nameValuePairs.put(
                    FreeBConstants.AFFILIATE_APP_ID, prefs.getString(
                            FreeBConstants.AFFILIATE_APP_ID, ""));

            nameValuePairs.put(
                    FreeBConstants.TRACKING_ID, prefs.getString(
                            FreeBConstants.ID, ""));
            nameValuePairs.put("mobileOs", "Android");
            nameValuePairs.put(FreeBConstants.VERSION,
                    Build.VERSION.RELEASE);
            nameValuePairs.put(
                    FreeBConstants.MOBILE_SDK_VERSION,
                    FreeBConstants.SDK_VERSION);
            nameValuePairs.put("serverReferenceId",
                    dbadapter.getAppServerReferanceId(packageName));
            nameValuePairs.put(FreeBConstants.APP_ID,
                    dbadapter.getOfferId(packageName));
            nameValuePairs.put(FreeBConstants.STAGING,
                    FreeBCommonUtility.getAppStatus(context));
            nameValuePairs.put("linkName", dbadapter
                    .getAppURL(packageName));
            nameValuePairs.put("videoPlayTime", "0");

            nameValuePairs.put("bannerId", "0");
            nameValuePairs.put("interstitialId", "0");

            nameValuePairs.put(FreeBConstants.UDF1,
                    dbadapter.getItemId(packageName));
            nameValuePairs.put(FreeBConstants.UDF2,
                    prefs.getString(FreeBConstants.UNIQUE_ID, ""));
            nameValuePairs.put(FreeBConstants.UDF3, "");
            nameValuePairs.put(FreeBConstants.UDF4, "");
            nameValuePairs.put(FreeBConstants.UDF5, "");

            nameValuePairs.put(
                    FreeBConstants.APP_STATUS, appStatus);

        } catch (Exception e) {

        }

        return nameValuePairs;
    }
}
