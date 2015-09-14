package in.freebsdk.broadcastreceiver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import in.freebsdk.services.FreeBAppInstallIntentService;

/**
 * This class implements the receiver which will be execute whenever user performed the installation and uninstallation of Applications
 */
public class WakefullReceiverAppInstall extends WakefulBroadcastReceiver {

    /**
     * Android calls the onReceive() method on all registered broadcast receivers whenever the event occurs.
     *
     * @param context The Context object you can use to access additional information or to start services or activities
     * @param intent  The Intent object with the action you used to register your receiver. This object contains additional information that you can use in your implementation
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        //if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean("accepted_terms", false)) {
        ComponentName componentName = new ComponentName(context.getPackageName(), FreeBAppInstallIntentService.class.getName());
        startWakefulService(context, intent.setComponent(componentName));
        //}
    }
}