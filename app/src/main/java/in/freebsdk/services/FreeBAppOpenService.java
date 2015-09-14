package in.freebsdk.services;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import in.freeb.sdk.R;
import in.freeb.sdk.utils.FreeBAsynWebReqUtils;
import in.freeb.sdk.utils.FreeBCommonUtility;
import in.freeb.sdk.utils.FreeBConstants;
import in.freeb.sdk.utils.FreeBSDKLogger;
import in.freeb.sdk.utils.FreebSDKApplication;
import in.freebsdk.database.FreeBDataBaseAdapter;

/**
 * A Service class which will be running in the background indefinitely, which help us to track the current status of the application.
 * Displaying whether user has opened it or not.
 */
public class FreeBAppOpenService extends Service {

    String appName;
    static int i = 0;
    Boolean isTimeCompeted = false;
    Boolean isInstalled = false;
    private Context context;
    private String packageName;
    private FreeBDataBaseAdapter dataBaseAdapter;
    private String timeInSeconds;
    String TAG = "ServiceAppInstalled";
    Timer timer;
    TimerTask timerTask;
    CountDownTimer countDownTimer;

    public FreeBAppOpenService() {

    }

    /**
     * Monitoring the current status of applications, accordance to that  {@code t} will send information
     * to the server about the app status and save same information in FreeB SDK database.
     *
     * @param context Context to get the resources
     * @param pkgName Application Package Name
     */
    private void setAppStatusRun(final Context context, final String pkgName) {
        try {
            dataBaseAdapter = new FreeBDataBaseAdapter(context);
            if (dataBaseAdapter != null) {
                if (dataBaseAdapter.getPackageName().contains(pkgName)
                        && !dataBaseAdapter.getAppStatus(pkgName).equals(
                        FreeBConstants.APP_STATUS_RUN)
                        && (dataBaseAdapter.getAppStatus(pkgName).equals(
                        FreeBConstants.APP_STATUS_INSTALL) || dataBaseAdapter
                        .getAppStatus(pkgName).equals(
                                FreeBConstants.APP_STATUS_INTEREST))) {
                    try {
                        AppReportingUsingVolley(context,pkgName);

                    } catch (Exception e) {
                        if (FreebSDKApplication.freeBOffersListener != null) {
                            FreebSDKApplication.freeBOffersListener
                                    .onOffersInstallFailure(
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

                        FreeBSDKLogger.e(getClass().getName(),
                                e.getMessage() != null ? e.getMessage()
                                        : FreeBConstants.WRONG);
                        // Crashlytics.logException(e);
                    }
                }
            }
        } catch (Exception e) {
            // Crashlytics.logException(e);
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
            }
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }

    }

    /**
     * Starting timer to keep checking whether the installed offer has run or not.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = FreeBAppOpenService.this;

        try {
            dataBaseAdapter = new FreeBDataBaseAdapter(context);
            startTimer();

        } catch (Exception e) {
            // Crashlytics.logException(e);
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
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
            }
        }
        return START_STICKY;
    }

    /**
     * This method schedule a task for repeated fixed-delay, that is the 10000ms and execute the `setAppStatusRun` method
     * after a specific delay
     */
    private void startTimer() {

        //set a new Timer
        timer = new Timer();
        //initialize the TimerTask's job
        timerTask = new TimerTask() {

            @Override
            public void run() {

                try {
                    packageName = getRunningApp();
                    if (dataBaseAdapter.getPackageName().contains(packageName)
                            && !dataBaseAdapter.getAppStatus(packageName)
                            .equals(FreeBConstants.APP_STATUS_RUN)
                            && (dataBaseAdapter.getAppStatus(packageName)
                            .equals(FreeBConstants.APP_STATUS_INSTALL) || dataBaseAdapter
                            .getAppStatus(packageName).equals(
                                    FreeBConstants.APP_STATUS_INTEREST))) {

                        setAppStatusRun(FreeBAppOpenService.this, packageName);

                    }
                } catch (Exception e) {
                    // Crashlytics.logException(e);
                    FreeBSDKLogger.e(getClass().getName(),
                            e.getMessage() != null ? e.getMessage()
                                    : FreeBConstants.WRONG);
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
                }
            }
        };
        /**schedule the timer, after the first 0ms the TimerTask will run every 10000ms*/
        timer.scheduleAtFixedRate(timerTask, 0, 10 * 1000); // every 10
        // seconds

    }

    /**
     * Called when server returns response whether request was executed successfully or failed.
     *
     * @param output response sent by the server
     * @param code   response code, tell us about the process success or failure
     */
    public void processFinish(String output, String code) {
        String jsonFromBundle = output;
        if (!jsonFromBundle.contains(FreeBCommonUtility.NETWORK_UNAVAILABLE)) {
            if (code.equals(FreeBConstants.RESULT_CODE_APP_REPORTING)) {
                if (!FreeBCommonUtility.isStringEmtyOrNull(jsonFromBundle)
                        && !jsonFromBundle.contains("fail")) {
                    if (dataBaseAdapter != null) {
                        // Log.i("ActivityMain", "processFinish open ");
                        int id = dataBaseAdapter.updateAppStatus(packageName,
                                FreeBConstants.APP_STATUS_RUN);
                        dataBaseAdapter.close();

                    }
                    // stopSelf();
                }
            }
        }
        dataBaseAdapter.close();

    }

    /**
     * A client is binding to the service with bindService()
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Called when the service is no longer used and is being destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (timer != null && timerTask != null) {
                timer.cancel();
                timerTask.cancel();
            }
        } catch (Exception e) {
            // Crashlytics.logException(e);
            FreeBSDKLogger.e(getClass().getName(),
                    e.getMessage() != null ? e.getMessage()
                            : FreeBConstants.WRONG);
        }
    }

    /**
     * Getting running application name
     * @return running app name
     */
    @SuppressWarnings("deprecation")
    private String getRunningApp() {
        String processName = "";
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            ActivityManager activityManager = (ActivityManager) this
                    .getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningAppProcessInfo> appProcesses = activityManager
                    .getRunningAppProcesses();
            for (RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    // Log.i("Foreground App", appProcess.processName);
                    processName = appProcess.processName;
                    return processName;
                }
            }
        } else {
            final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            processName = activityManager.getRunningTasks(1).get(0).topActivity
                    .getPackageName();
            return processName;

        }
        return processName;
    }
    public void AppReportingUsingVolley(final Context context, final String pkgName){

        String url = FreeBConstants.CORE_REPORT_URL + ""+ FreeBConstants.APP_REPORTING;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            String result = response;

                            processFinish(result,
                                    FreeBConstants.RESULT_CODE_APP_REPORTING);
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



                HashMap<String, String> multipartTypedOutput = FreeBAsynWebReqUtils
                        .getRequestParamsAppReportingforVolley(context,
                                dataBaseAdapter, pkgName,
                                FreeBConstants.APP_STATUS_RUN);

                Map<String, String>  params = new HashMap<>();
                params=multipartTypedOutput;
                return params;
            }
        };
        Volley.newRequestQueue(context).add(postRequest);
    }
}
