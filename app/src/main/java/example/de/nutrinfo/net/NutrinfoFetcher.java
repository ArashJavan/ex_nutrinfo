package example.de.nutrinfo.net;

import android.net.Uri;

import java.net.URI;

/**
 * Created by milux on 15.08.15.
 */
public class NutrinfoFetcher {

    private final String API_KEY = "zy87Ma6qLIp6dgJtNN12SsZHWWn6Mtx0rgjBc0GT";

    /**
     * enum for different request parameters
     * @see <a>http://ndb.nal.usda.gov/ndb/doc/apilist/API-LIST.md</a>
     */
    public enum RequestParams {
        API_KEY("api_key"),
        MAX_ITEMS("max"),
        LIST_TYPES("lt"),
        OFFSET("offset"),
        SORT("sort");

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

    public void fetchItems(String url) {


    }

    public void getList(ListTypes type, int maxItems, int offset, SortOrder so) {
        String url = Uri.parse(Endpoints.LISTS.getUrl()).buildUpon()
                .appendQueryParameter(RequestParams.API_KEY.getAlias(), API_KEY)
                .appendQueryParameter(RequestParams.MAX_ITEMS.getAlias(), String.valueOf(maxItems))
                .appendQueryParameter(RequestParams.LIST_TYPES.getAlias(), type.getAlias())
                .appendQueryParameter(RequestParams.SORT.getAlias(), so.getAlias())
                .toString();

        fetchItems(url);
    }
}
