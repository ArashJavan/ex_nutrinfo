package example.de.nutrinfo.net;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import example.de.nutrinfo.model.Food;
import example.de.nutrinfo.util.LogUtils;

/**
 * Created by milux on 15.08.15.
 */
public class NutrinfoFetcher {

    private static final String TAG = LogUtils.makeLogTag(NutrinfoFetcher.class);

    private final String API_KEY = "zy87Ma6qLIp6dgJtNN12SsZHWWn6Mtx0rgjBc0GT";
    private final String API_FORMAT = "json";


    /**
     * enum for different request parameters
     * @see <a>http://ndb.nal.usda.gov/ndb/doc/apilist/API-LIST.md</a>
     */
    public enum RequestParams {
        API_KEY("api_key"),
        MAX_ITEMS("max"),
        LIST_TYPES("lt"),
        OFFSET("offset"),
        SORT("sort"),
        FORMAT("format");

        private String mAlias;

        RequestParams(String alias) {
            mAlias = alias;
        }

        public String getAlias() {
            return mAlias;
        }
    }

    /**
     * enum for getting different listing options
     * @see <a>http://ndb.nal.usda.gov/ndb/doc/apilist/API-LIST.md</a>
     */
    public enum ListTypes {
        FOOD("f"),
        ALL_NUTRIENTS("n"),
        FOOD_GROUP("g");

        private String mAlias;

        ListTypes(String alias) {
            mAlias = alias;
        }

        public String getAlias() {
            return mAlias;
        }
    }

    /**
     * enum for getting different sorting options
     * @see <a>http://ndb.nal.usda.gov/ndb/doc/apilist/API-LIST.md</a>
     */
    public enum SortOrder {
        NAME("n"),
        ID("id");

        private String mAlias;

        SortOrder(String alias) {
            mAlias = alias;
        }

        public String getAlias() {
            return mAlias;
        }
    }

    private JSONObject makeRequest(String urlSpec) throws IOException, JSONException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            return new JSONObject(out.toString());
        } finally {
            connection.disconnect();
        }
    }

    public JSONObject downloadItems(String url) throws IOException, JSONException {
        LogUtils.d(TAG, "downloadItems" + url);
        return makeRequest(url);
}

    public JSONObject getList(ListTypes type, int maxItems, int offset, SortOrder so) throws IOException, JSONException {
        String url = Uri.parse(Endpoints.LISTS.getUrl()).buildUpon()
                .appendQueryParameter(RequestParams.API_KEY.getAlias(), API_KEY)
                .appendQueryParameter(RequestParams.MAX_ITEMS.getAlias(), String.valueOf(maxItems))
                .appendQueryParameter(RequestParams.LIST_TYPES.getAlias(), type.getAlias())
                .appendQueryParameter(RequestParams.SORT.getAlias(), so.getAlias())
                .appendQueryParameter(RequestParams.OFFSET.getAlias(), String.valueOf(offset))
                .appendQueryParameter(RequestParams.FORMAT.getAlias(), API_FORMAT)
                .toString();

        return downloadItems(url);
    }
}
