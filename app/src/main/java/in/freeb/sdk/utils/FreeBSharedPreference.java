package in.freeb.sdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * This Class contains functions to save and retrieve data in the form of key,value pair.
 * you have to call a method {@code getperferences} that returns the value of preferences.
 * for saving the value, you have to call {@code savePreferences}
 */

public class FreeBSharedPreference {

    public static void savePreferences(Context context, String key, String notification) {
        SharedPreferences info = context.getSharedPreferences(key,
                Context.MODE_PRIVATE);
        Editor agent = info.edit();

        agent.clear();
        agent.commit();
        agent.putString(key, notification);
        agent.commit();
        agent = null;
    }

    public static String getperferences(Context context, String key) {
        SharedPreferences info = context.getSharedPreferences(key,
                Context.MODE_PRIVATE);

        String aname = info.getString(key, "0");
        info = null;
        return aname;
    }
}
