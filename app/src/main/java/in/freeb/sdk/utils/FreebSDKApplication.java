package in.freeb.sdk.utils;

import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;

import in.freeb.sdk.interfaces.FreeBOffersListener;

/**
 * This application can be used to maintain global state across an application, contain common methods, etc.
 * You need to extend the android.app.Application class and override the OnCreate method. This method is invoked by the Android runtime once when your application instance created
 */
public class FreebSDKApplication extends android.app.Application {

    /**Application wide instance variables*/
	public static FreeBOffersListener freeBOffersListener;

	@Override
	public void onCreate() {
		super.onCreate();
		// Fabric.with(this, new Crashlytics());

        /** set the max number of icons (image width <= 50) to be cached in memory, default is 20 */
		BitmapAjaxCallback.setIconCacheLimit(20);

		/** set the max number of images (image width > 50) to be cached in memory, default is 20 */
		BitmapAjaxCallback.setCacheLimit(30);
	}

    /**
     * This is called when the overall system is running low on memory, and actively running processes should trim their memory usage.
     */
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		/** clear all memory cached images when system is in low memory */
		try {
			BitmapAjaxCallback.clearCache();
			AQUtility.cleanCacheAsync(this);
		} catch (Exception e) {
		}
	}
}
