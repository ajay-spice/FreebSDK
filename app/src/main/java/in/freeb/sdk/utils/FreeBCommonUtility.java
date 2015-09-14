package in.freeb.sdk.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.util.Patterns;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import in.freeb.sdk.R;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedString;

/**
 * Provides utility functions for working with Anonymously logged-in users.
 */
public class FreeBCommonUtility {
    /**
     * ***** @author of class CH-E00953/Aalok Sharma ********
     */

    public static final String LOG_TAG = "FREEB_SDK";
    public static final String NETWORK_UNAVAILABLE = "NETWORK_UNAVAILABLE";
    private static double latitude;
    private static double longitude;

    public static String FACEBOOK_APP_LINK = "fb://page/762757640440760";
    public static String FACEBOOK_BROWSER_LINK = "https://www.facebook.com/pages/FreeB/762757640440760";
    public static String TWITTER_APP_LINK = "twitter://user?screen_name=freeBofficial";
    public static String TWITTER_BROWSER_LINK = "https://twitter.com/freeBofficial";
    public static String GOOGLE_PLUS_LINK = "https://plus.google.com/101411241056406439810/posts";
    public static String GOOGLE_PLUS_APP_LINK = "google://user?screen_name=freeBofficial";
    public static String GOOGLE_PLUS_BROWSER_LINK = "https://plus.google.com/freeBofficial";

    private static boolean isDebug = true;

    /**
     * To check whether string supplied is null or empty
     *
     * @param string any input string
     * @return true if string is null or empty or else false
     */
    @SuppressLint("NewApi")
    public static boolean isStringEmtyOrNull(String string) {
        return (string == null || string.isEmpty());
    }

    /**
     * Display alert dialog
     *
     * @param context context to get resources
     * @param message input display message
     */
    public static void showAlertDialogAndFinishActivity(final Context context,
                                                        String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ((Activity) context).finish();
                        ((Activity) context).overridePendingTransition(
                                R.anim.righttoleftanimreturn,
                                R.anim.lefttorightanimreturn);
                    }
                }).setIcon(R.drawable.app_icon);

        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetch network connection type whether network is 3G/GPRS/CDMA etc.
     *
     * @param context Context to get resources and Network information
     * @return address or empty string
     */

    public static String getNetworkClass(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
            return "-"; // not connected
        if (info.getType() == ConnectivityManager.TYPE_WIFI)
            return "WIFI";
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return "4G";
                default:
                    return "UNKNOWN";
            }
        }
        return "UNKNOWN";
    }

    /**
     * To get device id of your phone/tablet
     *
     * @param context Context to get the resources
     * @return device id
     */

    public static String getDeviceId(Context context) {
        String deviceId = "";
        try {
            deviceId = Secure.getString(context.getContentResolver(),
                    Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }

    /**
     * To get mobile country code
     *
     * @param mContext Context to get the instance of TelephonyManager
     * @return MCC 3-digit Mobile Country Code
     */
    public static String getMCC(Context mContext) {
        String mcc = "";
        try {
            // retrieve a reference to an instance of TelephonyManager
            TelephonyManager telephonyManager = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String networkOperator = telephonyManager.getNetworkOperator();
            mcc = networkOperator.substring(0, 3);
        } catch (Exception e) {
            FreeBSDKLogger.e(LOG_TAG, e.getMessage());
        }
        return mcc;
    }

    /**
     * To get mobile network code
     *
     * @param mContext Context to get the instance of TelephonyManager
     * @return MNC  2 or 3-digit Mobile Network Code
     */
    public static String getMNC(Context mContext) {
        String mnc = "";
        try {
            // retrieve a reference to an instance of TelephonyManager
            TelephonyManager telephonyManager = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String networkOperator = telephonyManager.getNetworkOperator();
            mnc = networkOperator.substring(3);
        } catch (Exception e) {
            FreeBSDKLogger.e(LOG_TAG, e.getMessage());
        }
        return mnc;
    }

    /**
     * To get CELL ID
     *
     * @param mContext Context to get the instance of TelephonyManager
     * @return CELL ID
     */
    public static String getCellID(Context mContext) {
        String cellID = "";
        try {
            // retrieve a reference to an instance of TelephonyManager
            TelephonyManager telephonyManager = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager
                    .getCellLocation();
            cellID = String.valueOf(cellLocation.getCid());
        } catch (Exception e) {
            FreeBSDKLogger.e(LOG_TAG, e.getMessage());
        }
        return cellID;
    }

    /**
     * To get location area code.
     *
     * @param mContext Context to get the instance of TelephonyManager
     * @return LAC gsm location area code
     */
    public static String getLAC(Context mContext) {
        String lac = "";
        try {
            // retrieve a reference to an instance of TelephonyManager
            TelephonyManager telephonyManager = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager
                    .getCellLocation();
            lac = String.valueOf(cellLocation.getLac());
        } catch (Exception e) {
            FreeBSDKLogger.e(LOG_TAG, e.getMessage());
        }
        return lac;
    }


    /**
     * To get mobile number
     *
     * @param context Context to get the instance of TelephonyManager
     * @return mobile no
     */
    public static String getMobileNo(Context context) {
        String mobileNo = "";
        try {
            SharedPreferences sharedPreference = PreferenceManager
                    .getDefaultSharedPreferences(context);
            mobileNo = sharedPreference.getString("authenticated_phone_number",
                    "");
            if (!mobileNo.equals("")) {
                return mobileNo;
            } else {
                TelephonyManager telephonyManager = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                mobileNo = telephonyManager.getLine1Number();
                if (mobileNo == null)
                    mobileNo = "";
                if (mobileNo.length() > 10)
                    mobileNo = mobileNo.substring(mobileNo.length() - 10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mobileNo;

    }

    /**
     * To get IMEI of the device
     *
     * @param context Context to get the instance of TelephonyManager
     * @return Imei Number
     */

    public static String getImeiNumber(Context context) {

        String deviceIMEI = "";
        try {
            TelephonyManager tManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            deviceIMEI = tManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceIMEI;
    }

    /**
     * To get the version code of the SDK.
     *
     * @param context Context to get the instance of PackageInfo
     * @return Version Code
     */

    public static String getVersionCode(Context context) {
        PackageInfo pInfo = null;
        int version = 0;
        try {
            pInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
        if (pInfo != null)
            version = pInfo.versionCode;
        return String.valueOf(version);
    }

    /**
     * To get configured email id of the device
     *
     * @param context Context to get the instance of AccountManager
     * @return Email Id
     */

    public static String getEmailId(Context context) {
        String possibleEmail = "";
        try {
            Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
            Account[] accounts = AccountManager.get(context).getAccountsByType(
                    "com.google");
            for (Account account : accounts) {
                if (emailPattern.matcher(account.name).matches()) {
                    possibleEmail = account.name;
                    break;
                }
            }
        } catch (Exception e) {
            // Log.i("Exception", "Exception:" + e);
        }
        return possibleEmail;
    }

    /**
     * Get Installed Apps Package Name
     *
     * @param context Context to get the List of Installed App Package Name
     * @return Installed Apps Package Name
     */
    public static String getInstalledAppsPackageName(Context context) {
        String packageNames = "";
        try {
            List<ApplicationInfo> list = context.getPackageManager()
                    .getInstalledApplications(PackageManager.GET_META_DATA);
            String packageName;
            packageNames = "";
            for (int i = 0; i < list.size(); i++) {
                ApplicationInfo applicationInfo = list.get(i);
                packageName = applicationInfo.packageName;

                packageNames = packageNames + packageName + ",";

            }
            packageNames = packageNames.substring(0, packageNames.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packageNames;
    }

    /**
     * Get Location from device
     *
     * @param context Context to get the User Location via get the Instance of LocationManager
     * @return user location object
     */

    public static Location getReturnLocation(Context context) {
        Location location = null;

        try {

            FreeBUserLocationUtility userLocationUtility = new FreeBUserLocationUtility(
                    context); // Location location =
            // userLocationUtility.getLocation();
            if (userLocationUtility.canGetLocation()) {
                // latitude = userLocationUtility.getLatitude();
                // longitude = userLocationUtility.getLongitude();
                location = userLocationUtility.getLocation();
            } else {
                location = null;
            }
            return location;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Set the Status Bar Color
     *
     * @param context Context to get the resources
     */
    @SuppressLint("NewApi")
    public static void setStatusBarColor(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = ((Activity) context).getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(context.getResources().getColor(
                        R.color.statusbar_color));
            }
        } catch (NotFoundException e) {
        } catch (Exception e) {
        }
    }

    /**
     * Get App Status
     *
     * @param context Context to get the resources
     * @return App Status tells us about the application mode
     */
    public static String getAppStatus(Context context) {
        boolean isDebuggable = (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
        if (isDebuggable)
            return "STAGING";
        else
            return "PRODUCTION";
    }


    /**
     * Display Alert Dialog
     *
     * @param context Context to get the resources
     * @param message Message which will be displayed
     */
    public static void showAlertDialog(Context context, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setIcon(R.drawable.app_icon);

        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Check network connection status
     *
     * @param context Context to get the resources
     * @return if available returns true else false
     */
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        Log.e("", "Network Not Available...");
        return false;
    }

    /**
     * Check whether app is in foreground or background
     *
     * @param context Context to get the resources
     * @return if success returns true else false
     */
    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Make a standard toast that just contains a text view.
     *
     * @param context Context to get the resources
     * @param message Message which will be displayed
     */
    public static void showToast(Context context, String message) {

        if (isDebug)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();

    }

    public static MultipartTypedOutput getOffresParams(Context context,
                                                       String affiliateId, String affiliateAppId, String locationUser,
                                                       String density, String deviceLattitude, String deviceLongitude,
                                                       String appStatus, String uniqueId) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
        multipartTypedOutput.addPart(FreeBConstants.VERSION, new TypedString(
                Build.VERSION.RELEASE));
        multipartTypedOutput.addPart(FreeBConstants.AFFILIATE_ID,
                new TypedString(affiliateId));

        multipartTypedOutput.addPart(FreeBConstants.AFFILIATE_APP_ID,
                new TypedString(affiliateAppId));

        multipartTypedOutput.addPart(FreeBConstants.TRACKING_ID,
                new TypedString(prefs.getString(FreeBConstants.ID, "")));

        multipartTypedOutput.addPart(FreeBConstants.CONN_TYPE, new TypedString(
                FreeBCommonUtility.getNetworkClass(context)));
        multipartTypedOutput.addPart(FreeBConstants.DENSITY, new TypedString(
                density));
        multipartTypedOutput.addPart(FreeBConstants.STAGING, new TypedString(
                appStatus));

        multipartTypedOutput.addPart(FreeBConstants.MOBILE_SDK_VERSION,
                new TypedString(FreeBConstants.SDK_VERSION));

        multipartTypedOutput.addPart(FreeBConstants.DEVICE_ID, new TypedString(
                FreeBCommonUtility.getDeviceId(context)));
        multipartTypedOutput.addPart("mobileApp", new TypedString("1"));
        multipartTypedOutput.addPart("mobileOs", new TypedString("Android"));
        multipartTypedOutput.addPart("mobileAppVersion", new TypedString(
                FreeBCommonUtility.getVersionCode(context)));

        multipartTypedOutput.addPart("location", new TypedString(locationUser));

        multipartTypedOutput.addPart(FreeBConstants.UDF1, new TypedString(""));
        multipartTypedOutput.addPart(FreeBConstants.UDF2, new TypedString(uniqueId));
        multipartTypedOutput.addPart(FreeBConstants.UDF3, new TypedString(""));

        multipartTypedOutput.addPart(FreeBConstants.UDF4, new TypedString(""));
        multipartTypedOutput.addPart(FreeBConstants.UDF5, new TypedString(""));

        multipartTypedOutput.addPart(FreeBConstants.MCC, new TypedString(
                FreeBCommonUtility.getMCC(context)));

        multipartTypedOutput.addPart(FreeBConstants.MNC, new TypedString(
                FreeBCommonUtility.getMNC(context)));
        multipartTypedOutput.addPart(FreeBConstants.CELLID, new TypedString(
                FreeBCommonUtility.getCellID(context)));

        multipartTypedOutput.addPart(FreeBConstants.IMEI, new TypedString(
                FreeBCommonUtility.getImeiNumber(context)));
        multipartTypedOutput.addPart(FreeBConstants.LATITUDE, new TypedString(
                String.valueOf(deviceLattitude)));
        multipartTypedOutput.addPart(FreeBConstants.LONGITUDE, new TypedString(
                String.valueOf(deviceLongitude)));
        multipartTypedOutput.addPart(FreeBConstants.HANDSET_MAKE,
                new TypedString(Build.MANUFACTURER));
        multipartTypedOutput.addPart(FreeBConstants.HANDSET_MODEL,
                new TypedString(Build.MODEL));
        multipartTypedOutput.addPart("mobileNumber", new TypedString(
                FreeBCommonUtility.getMobileNo(context)));
        multipartTypedOutput.addPart(FreeBConstants.EMAIL_ID, new TypedString(
                FreeBCommonUtility.getEmailId(context)));

        return multipartTypedOutput;

    }

    public static  HashMap<String, String> getOffersParamsforVolley(Context context,
                                                                    String affiliateId, String affiliateAppId, String locationUser,
                                                                    String density, String deviceLattitude, String deviceLongitude,
                                                                    String appStatus,String uniqueId) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        HashMap<String, String> multipartTypedOutput = new HashMap<String, String>();
        multipartTypedOutput.put(FreeBConstants.VERSION,
                Build.VERSION.RELEASE);
        multipartTypedOutput.put(FreeBConstants.AFFILIATE_ID,
                affiliateId);

        multipartTypedOutput.put(FreeBConstants.AFFILIATE_APP_ID,
                affiliateAppId);

        multipartTypedOutput.put(FreeBConstants.TRACKING_ID,
                prefs.getString(FreeBConstants.ID, ""));

        multipartTypedOutput.put(FreeBConstants.CONN_TYPE,
                FreeBCommonUtility.getNetworkClass(context));
        multipartTypedOutput.put(FreeBConstants.DENSITY,
                density);
        multipartTypedOutput.put(FreeBConstants.STAGING,
                appStatus);

        multipartTypedOutput.put(FreeBConstants.MOBILE_SDK_VERSION,
                FreeBConstants.SDK_VERSION);

        multipartTypedOutput.put(FreeBConstants.DEVICE_ID,
                FreeBCommonUtility.getDeviceId(context));
        multipartTypedOutput.put("mobileApp","1");
        multipartTypedOutput.put("mobileOs", "Android");
        multipartTypedOutput.put("mobileAppVersion",
                FreeBCommonUtility.getVersionCode(context));

        multipartTypedOutput.put("location", locationUser);

        multipartTypedOutput.put(FreeBConstants.UDF1, "");
        multipartTypedOutput.put(FreeBConstants.UDF2, uniqueId);
        multipartTypedOutput.put(FreeBConstants.UDF3, "");

        multipartTypedOutput.put(FreeBConstants.UDF4, "");
        multipartTypedOutput.put(FreeBConstants.UDF5, "");

        multipartTypedOutput.put(FreeBConstants.MCC,
                FreeBCommonUtility.getMCC(context));

        multipartTypedOutput.put(FreeBConstants.MNC,
                FreeBCommonUtility.getMNC(context));
        multipartTypedOutput.put(FreeBConstants.CELLID,
                FreeBCommonUtility.getCellID(context));

        multipartTypedOutput.put(FreeBConstants.IMEI,
                FreeBCommonUtility.getImeiNumber(context));
        multipartTypedOutput.put(FreeBConstants.LATITUDE,
                String.valueOf(deviceLattitude));
        multipartTypedOutput.put(FreeBConstants.LONGITUDE,
                String.valueOf(deviceLongitude));
        multipartTypedOutput.put(FreeBConstants.HANDSET_MAKE,
                Build.MANUFACTURER);
        multipartTypedOutput.put(FreeBConstants.HANDSET_MODEL,
                Build.MODEL);
        multipartTypedOutput.put("mobileNumber",
                FreeBCommonUtility.getMobileNo(context));
        multipartTypedOutput.put(FreeBConstants.EMAIL_ID,
                FreeBCommonUtility.getEmailId(context));

        return multipartTypedOutput;

    }

    public static List<NameValuePair> getRegistrationParams(Context mContext,
                                                            String affiliateId, String affiliateAppId, String density,
                                                            String deviceLattitude, String deviceLongitude, String appStatus) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        try {

            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.VERSION,
                    Build.VERSION.RELEASE));
            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.PACKAGE_NAME_SDK,
                    FreeBConstants.SDK_PACKAGENAME));

            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.AFFILIATE_ID, affiliateId));
            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.AFFILIATE_APP_ID, affiliateAppId));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.CONN_TYPE,
                    FreeBCommonUtility.getNetworkClass(mContext)));

            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.DENSITY,
                    density));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.STAGING,
                    appStatus));

            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.EMAIL_ID,
                    FreeBCommonUtility.getEmailId(mContext)));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.DEVICE_ID,
                    FreeBCommonUtility.getDeviceId(mContext)));
            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.HANDSET_MAKE, Build.MANUFACTURER));

            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.HANDSET_MODEL, Build.MODEL));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.MOBILE_OS,
                    "Android"));

            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.MOBILE_APP_VERSION, FreeBCommonUtility
                    .getVersionCode(mContext)));
            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.PACKAGE_NAME, mContext.getPackageName()));
            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.IS_MOBILE_APP, "1"));

            nameValuePairs.add(new BasicNameValuePair(
                    FreeBConstants.MOBILE_SDK_VERSION,
                    FreeBConstants.SDK_VERSION));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.IMEI,
                    FreeBCommonUtility.getImeiNumber(mContext)));

            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.LATITUDE,
                    String.valueOf(deviceLattitude)));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.LONGITUDE,
                    String.valueOf(deviceLongitude)));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.UDF1, ""));

            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.UDF2, ""));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.UDF3, ""));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.UDF4, ""));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.UDF5, ""));

            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.MCC,
                    FreeBCommonUtility.getMCC(mContext)));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.MNC,
                    FreeBCommonUtility.getMNC(mContext)));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.CELLID,
                    FreeBCommonUtility.getCellID(mContext)));
            nameValuePairs.add(new BasicNameValuePair(FreeBConstants.LAC,
                    FreeBCommonUtility.getLAC(mContext)));

        } catch (Exception e) {

            FreeBSDKLogger.e("Exception While Creating Registration Request Array", e.getMessage() != null ? e.getMessage()
                    : FreeBConstants.WRONG);

        }

        return nameValuePairs;

    }

    public static HashMap<String, String> getRegistrationParamsforVolley(Context mContext,
                                                                         String affiliateId, String affiliateAppId, String density,
                                                                         String deviceLattitude, String deviceLongitude, String appStatus) {

        HashMap<String, String> nameValuePairs = new HashMap<String, String>();
        try {

            nameValuePairs.put(FreeBConstants.VERSION,Build.VERSION.RELEASE);
            nameValuePairs.put(
                    FreeBConstants.PACKAGE_NAME_SDK,
                    FreeBConstants.SDK_PACKAGENAME);

            nameValuePairs.put(
                    FreeBConstants.AFFILIATE_ID, affiliateId);
            nameValuePairs.put(
                    FreeBConstants.AFFILIATE_APP_ID, affiliateAppId);
            nameValuePairs.put(FreeBConstants.CONN_TYPE,
                    FreeBCommonUtility.getNetworkClass(mContext));

            nameValuePairs.put(FreeBConstants.DENSITY,
                    density);
            nameValuePairs.put(FreeBConstants.STAGING,
                    appStatus);

            nameValuePairs.put(FreeBConstants.EMAIL_ID,
                    FreeBCommonUtility.getEmailId(mContext));
            nameValuePairs.put(FreeBConstants.DEVICE_ID,
                    FreeBCommonUtility.getDeviceId(mContext));
            nameValuePairs.put(
                    FreeBConstants.HANDSET_MAKE, Build.MANUFACTURER);

            nameValuePairs.put(
                    FreeBConstants.HANDSET_MODEL, Build.MODEL);
            nameValuePairs.put(FreeBConstants.MOBILE_OS,
                    "Android");

            nameValuePairs.put(
                    FreeBConstants.MOBILE_APP_VERSION, FreeBCommonUtility
                            .getVersionCode(mContext));
            nameValuePairs.put(
                    FreeBConstants.PACKAGE_NAME, mContext.getPackageName());
            nameValuePairs.put(
                    FreeBConstants.IS_MOBILE_APP, "1");

            nameValuePairs.put(
                    FreeBConstants.MOBILE_SDK_VERSION,
                    FreeBConstants.SDK_VERSION);
            nameValuePairs.put(FreeBConstants.IMEI,
                    FreeBCommonUtility.getImeiNumber(mContext));

            nameValuePairs.put(FreeBConstants.LATITUDE,
                    String.valueOf(deviceLattitude));
            nameValuePairs.put(FreeBConstants.LONGITUDE,
                    String.valueOf(deviceLongitude));
            nameValuePairs.put(FreeBConstants.UDF1, "");

            nameValuePairs.put(FreeBConstants.UDF2, "");
            nameValuePairs.put(FreeBConstants.UDF3, "");
            nameValuePairs.put(FreeBConstants.UDF4, "");
            nameValuePairs.put(FreeBConstants.UDF5, "");

            nameValuePairs.put(FreeBConstants.MCC,
                    FreeBCommonUtility.getMCC(mContext));
            nameValuePairs.put(FreeBConstants.MNC,
                    FreeBCommonUtility.getMNC(mContext));
            nameValuePairs.put(FreeBConstants.CELLID,
                    FreeBCommonUtility.getCellID(mContext));
            nameValuePairs.put(FreeBConstants.LAC,
                    FreeBCommonUtility.getLAC(mContext));

        } catch (Exception e) {

            FreeBSDKLogger.e("Exception While Creating Registration Request Array",e.getMessage() != null ? e.getMessage()
                    : FreeBConstants.WRONG);

        }

        return nameValuePairs;

    }

    /**
     * To get device density whether it is xxhdpi/xhdpi/hdpi/mdpi/ldpi
     * @param context context of the app
     * @return device density
     */
    public static String getDensity(Context context) {
        String densityDevice = "";
        double density = context.getResources().getDisplayMetrics().density;
        if (density >= 4.0) {
            densityDevice = "xxxhdpi";
        }
        if (density >= 3.0 && density < 4.0) {
            densityDevice = "xxhdpi";
        }
        if (density >= 2.0) {
            densityDevice = "xhdpi";
        }
        if (density >= 1.5 && density < 2.0) {
            densityDevice = "hdpi";
        }
        if (density >= 1.0 && density < 1.5) {
            densityDevice = "mdpi";
        }
        if (density < 1.0) {
            densityDevice = "ldpi";
        }
        return densityDevice;
    }
}
