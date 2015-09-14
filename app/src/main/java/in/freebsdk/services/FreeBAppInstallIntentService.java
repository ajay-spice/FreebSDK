package in.freebsdk.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.freeb.sdk.R;
import in.freeb.sdk.utils.FreeBAsynWebReqUtils;
import in.freeb.sdk.utils.FreeBCommonUtility;
import in.freeb.sdk.utils.FreeBConstants;
import in.freeb.sdk.utils.FreeBSDKLogger;
import in.freeb.sdk.utils.FreebSDKApplication;
import in.freebsdk.FreeBOffersListActivity;
import in.freebsdk.broadcastreceiver.WakefullReceiverAppInstall;
import in.freebsdk.database.FreeBDataBaseAdapter;

/**
 * Called when an offer will installed or uninstall through FreeB SDK, it will take the response from the services and will help us to handle the offer installation or uninstallation process.
 */
public class FreeBAppInstallIntentService extends IntentService {

    private final String packageAdded = "android.intent.action.PACKAGE_ADDED";
    private final String packageInstalled = "android.intent.action.PACKAGE_INSTALL";
    private final String packageUninstalled = "android.intent.action.PACKAGE_REMOVED";
    private String pkgName;
    FreeBDataBaseAdapter dataBaseAdapter;
    Context context;
    Intent intent;
    boolean isAppInstall;

    public FreeBAppInstallIntentService() {
        super("AppInstallIntentService");
    }

    public FreeBAppInstallIntentService(String name) {
        super(name);
    }

    /**
     * This method is invoked on the worker thread with a request to process.
     * Called when any offer is installed/updated/uninstalled by the user or Google Play
     *
     * @param intent The value passed to startService(Intent).
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        this.intent = intent;
        this.context = this;
        Uri uri = intent.getData();
        // this will be the name of the package
        pkgName = uri != null ? uri.getSchemeSpecificPart() : null;
        // Log.i("ladoo", "package name  " + pkgName);
        dataBaseAdapter = new FreeBDataBaseAdapter(context);

        switch (intent.getAction().toString()) {

            case packageAdded:

                // Log.i("freeb", "app added");
                FreeBSDKLogger.d("FreeB App Install", "App Added");
                setAppStatusInstall(context, pkgName);

                break;
            case packageUninstalled:
                if (FreeBCommonUtility.isNetworkAvailable(context)) {
                    // Log.i("freeb", "app uninstall");
                    FreeBSDKLogger.d("FreeB App Install", "App Uninstall");
                    setAppStatusUninstall(context, pkgName);
                } else {
                    if (dataBaseAdapter != null) {
                        if (dataBaseAdapter.getPackageName().contains(pkgName)
                                && !dataBaseAdapter.getAppStatus(pkgName).equals(
                                FreeBConstants.APP_STATUS_INTEREST)) {
                            dataBaseAdapter.updateAppStatus(pkgName,
                                    FreeBConstants.APP_STATUS_UNINSTALL);
                            dataBaseAdapter.updateSentStatus(pkgName, "false");
                            dataBaseAdapter.close();
                            FreeBSDKLogger.d("FreeB App Install", FreeBConstants.APP_STATUS_UNINSTALL);
                        }
                    }
                }
                break;
            default:
                break;
        }
        WakefullReceiverAppInstall.completeWakefulIntent(intent);

    }

    /**
     * When an app will Uninstall,and it will send information to the server about the App uninstallation
     *
     * @param context Context to get the resources
     * @param pkgName Package Name  App on which action has to be performed
     */
    private void setAppStatusUninstall(final Context context,
                                       final String pkgName) {
        try {

            dataBaseAdapter = new FreeBDataBaseAdapter(context);
            if (dataBaseAdapter.getPackageName().contains(pkgName)
                    && !dataBaseAdapter.getAppStatus(pkgName).equals(
                    FreeBConstants.APP_STATUS_INTEREST)) {
                try {
                    if (FreeBCommonUtility.isNetworkAvailable(context)) {
                        isAppInstall = false;
                        AppReportingUsingVolley(context,pkgName,"uninstall");
                    }
                } catch (Exception e) {
                    // Crashlytics.logException(e);
                    FreeBSDKLogger.e(getClass().getName(),
                            e.getMessage() != null ? e.getMessage()
                                    : FreeBConstants.WRONG);
                }

            }

        } catch (Exception e) {
            // Crashlytics.logException(e);
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
    }

    /**
     * When an app will be install ,and it will send information to the server about the App installation
     *
     * @param context Context to get the resources
     * @param pkgName Package Name  App on which action has to be performed
     */
    private void setAppStatusInstall(Context context, final String pkgName) {
        // Log.i("freeb", "setAppStatusInstall");
        try {
            dataBaseAdapter = new FreeBDataBaseAdapter(context);
            if (dataBaseAdapter.getPackageName().contains(pkgName)
                    && dataBaseAdapter.getAppStatus(pkgName).equals(
                    FreeBConstants.APP_STATUS_INTEREST)) {
                try {
                    if (FreeBCommonUtility.isNetworkAvailable(context)) {

                        isAppInstall = true;
                        AppReportingUsingVolley(context,pkgName,"install");
                    }
                } catch (Exception e) {

                    // Crashlytics.logException(e);
                    if (FreebSDKApplication.freeBOffersListener != null) {
                        FreebSDKApplication.freeBOffersListener.onOffersInstallFailure(
                                FreeBConstants.offersFailedCode,
                                FreeBConstants.WRONG);
                        if (context != null) {
                            if (context.getResources()
                                    .getString(R.string.showErrorMessage)
                                    .equals("true")) {
                                FreeBCommonUtility.showToast(context,
                                        FreeBConstants.WRONG);
                            }
                        }

                        FreeBSDKLogger.e("App Install setAppStatusUninstall", e
                                .getMessage() != null ? e.getMessage()
                                : FreeBConstants.WRONG);
                    }
                }

            }

        } catch (Exception e) {
            if (FreebSDKApplication.freeBOffersListener != null) {
                FreebSDKApplication.freeBOffersListener.onOffersInstallFailure(
                        FreeBConstants.offersFailedCode, FreeBConstants.WRONG);
                if (context != null) {
                    if (context.getResources()
                            .getString(R.string.showErrorMessage)
                            .equals("true")) {
                        FreeBCommonUtility.showToast(context,
                                FreeBConstants.WRONG);
                    }
                }
                FreeBSDKLogger.e("App Install setAppStatusUninstall", e
                        .getMessage() != null ? e.getMessage()
                        : FreeBConstants.WRONG);
            }
        }
    }

    /**
     * Called when installation process is completed successfully, and also the user who executed the install package.
     * when a software package is removed successfully, again logging the user behind the operation.
     * App install/uninstall process finish and it will contain information about the app installation or uninstallation
     *
     * @param output Repsonse sent by the server
     * @param code   Response code
     */
    public void processFinish(String output, String code) {

        if (isAppInstall) {
            // Installation operation completed successfully.
            try {
                JSONObject jObj = new JSONObject(output);

                if (jObj.optString("status").equals("ok"))
                    if (FreebSDKApplication.freeBOffersListener != null) {
                        FreebSDKApplication.freeBOffersListener.onOffersInstallSuccess(
                                code, output);
                        FreeBSDKLogger.d("App Install SUCCESS", code);
                    } else if (jObj.optString("status").equals("fail"))
                        if (FreebSDKApplication.freeBOffersListener != null) {
                            FreebSDKApplication.freeBOffersListener
                                    .onOffersInstallFailure(code, output);
                            FreeBSDKLogger.d("App Install FAILED", code + "  " + output);
                        }
                FreeBOffersListActivity.instance.finish();
            } catch (JSONException e) {
                // Crashlytics.logException(e);
                if (FreebSDKApplication.freeBOffersListener != null) {
                    FreebSDKApplication.freeBOffersListener.onOffersInstallFailure(
                            FreeBConstants.offersFailedCode,
                            FreeBConstants.WRONG);
                    if (context != null) {
                        if (context.getResources()
                                .getString(R.string.showErrorMessage)
                                .equals("true")) {
                            FreeBCommonUtility.showToast(context,
                                    FreeBConstants.WRONG);
                        }
                    }
                }
                FreeBSDKLogger.e("App Install Process Finish", e
                        .getMessage() != null ? e.getMessage()
                        : FreeBConstants.WRONG);
            }

        }
        String jsonFromBundle = output;
        if (!jsonFromBundle.contains(FreeBCommonUtility.NETWORK_UNAVAILABLE)) {
            //  Removal completed successfully
            if (code.equals(FreeBConstants.RESULT_CODE_APP_REPORTING)) {
                if (!FreeBCommonUtility.isStringEmtyOrNull(jsonFromBundle)
                        && !jsonFromBundle.contains("fail")) {

                    FreeBSDKLogger.d("App Install Process Finish", "App has installed onfinish");
                    if (dataBaseAdapter != null) {
                        dataBaseAdapter.updateAppStatus(pkgName,
                                FreeBConstants.APP_STATUS_INSTALL);
                        dataBaseAdapter.close();
                    } else {
                        dataBaseAdapter.close();
                    }
                    context.startService(new Intent(context,
                            FreeBAppOpenService.class));
                }
            } else if (code
                    .equals(FreeBConstants.RESULT_CODE_APP_UNINSTALL_REPORTING)) {

                if (!FreeBCommonUtility.isStringEmtyOrNull(jsonFromBundle)
                        && !jsonFromBundle.contains("fail")) {
                    // Log.i("ladoo","app has installed onfinish");
                    FreeBSDKLogger.d("App Uninstall Process Finish", "App has uninstalled onfinish");
                    if (!FreeBCommonUtility.isStringEmtyOrNull(jsonFromBundle)
                            && !jsonFromBundle.contains("fail")) {
                        // Log.i("ladoo","app has installed onfinish");
                        FreeBSDKLogger.d("App Uninstall Process Finish", "App has uninstalled onfinish");
                        if (dataBaseAdapter != null) {
                            dataBaseAdapter.updateAppStatus(pkgName,
                                    FreeBConstants.APP_STATUS_UNINSTALL);
                            dataBaseAdapter.updateSentStatus(pkgName, "true");
                            dataBaseAdapter.close();
                        }
                    } else {
                        dataBaseAdapter.close();
                    }
                }
            }
            if (code.equals(FreeBConstants.RESULT_CODE_INSTALLED_APPS)) {
                if (!FreeBCommonUtility.isStringEmtyOrNull(jsonFromBundle)
                        && !jsonFromBundle.contains("fail")) {

                }
            }
        }

        try {
            dataBaseAdapter.close();
        } catch (Exception e) {
            // Crashlytics.logException(e);
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
    }
    public void AppReportingUsingVolley(final Context context, final String pkgName,final String Action){

        String url = FreeBConstants.CORE_REPORT_URL + ""+ FreeBConstants.APP_REPORTING;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            String result = response;
                            if(Action.equals("install")){
                                processFinish(result,
                                        FreeBConstants.RESULT_CODE_APP_REPORTING);
                            }else {
                                processFinish(
                                        result,
                                        FreeBConstants.RESULT_CODE_APP_UNINSTALL_REPORTING);
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

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {

                HashMap<String, String> nameValuePairs= new HashMap<String,String>();
                if(Action.equals("install")) {
                    nameValuePairs = FreeBAsynWebReqUtils
                            .getRequestParamsAppReportingforVolley(context,
                                    dataBaseAdapter, pkgName,
                                    FreeBConstants.APP_STATUS_INSTALL);
                }else{
                    nameValuePairs = FreeBAsynWebReqUtils
                            .getRequestParamsAppReportingforVolley(context,
                                    dataBaseAdapter, pkgName,
                                    FreeBConstants.APP_STATUS_UNINSTALL);
                }

                Map<String, String>  params = new HashMap<>();
                params=nameValuePairs;
                return params;
            }
        };
        Volley.newRequestQueue(context).add(postRequest);
    }
}
