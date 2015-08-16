package example.de.nutrinfo.util;

import android.util.Log;

/**
 * Created by milux on 09.08.15.
 */
public class LogUtils {
    private static final String LOG_PREFIX = "nutrinfo_";

    public static String makeLogTag(Class cls) {
        return LOG_PREFIX + cls.getSimpleName();
    }

    public static void i(final String TAG, String msg) {
        Log.i(TAG, msg);
    }

    public static void d(final String TAG, String msg) {
        Log.d(TAG, msg);
    }

    public static void e(final String TAG, String msg) {
        Log.e(TAG, msg);
    }
}
