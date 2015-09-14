package in.freeb.sdk.utils;

import android.util.Log;

/**
 * <p>
 * Logger utility is for logging debugging and error message of the FreeB SDK.
 * This will simply print errors/exception occurs, web services request and response values in your logcat.
 * It will be useful for us to track the issues.
 * In the your project whether you want to log or not that is depends on {@code enableLogging}.
 * </p>
 *
 *  The recommended way is to put a call to
 *  {@code FreeBSDKLogger.enableLogging} in your {@code Application}'s {@code onCreate} method like this:
 *
 * <pre>
 *
 *  public class MyApplication extends Application {
 *       public void onCreate() {
 *            FreeBSDKLogger.enableLogging(true);
 *       }
 *   }
 * </pre>
 *
 */
public class FreeBSDKLogger {

    /**
     * Send a DEBUG log message and log the exception.
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void d(String tag, String message) {
        if (FreeBBuildConfig.DEBUG)
            Log.d(tag, message != null ? message : "");
    }

    /**
     * Send an ERROR log message.
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void e(String tag, String message) {
        if (FreeBBuildConfig.DEBUG)
            Log.e(tag, message != null ? message : "");
    }

    /**
     * Send a VERBOSE log message and log the exception.
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void v(String tag, String message) {
        if (FreeBBuildConfig.DEBUG)
            Log.v(tag, message != null ? message : "");
    }

    /**
     * Start logging debugging and error messages of our application
     * @param shouldLogging True to enable logging else false
     */
    public static void enableLogging(boolean shouldLogging) {
        FreeBBuildConfig.DEBUG = shouldLogging;
    }
}
