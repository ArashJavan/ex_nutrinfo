package example.de.nutrinfo.util;

/**
 * Created by milux on 09.08.15.
 */
public class LogUtils {
    private static final String LOG_PREFIX = "nutrinfo_";

    public static String makeLogTag(Class cls) {
        return LOG_PREFIX + cls.getSimpleName();
    }

}
