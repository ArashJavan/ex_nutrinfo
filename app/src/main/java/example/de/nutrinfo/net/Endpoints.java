package example.de.nutrinfo.net;

/**
 * Endpoints enum for the different endpoint urls
 * @see <a>http://ndb.nal.usda.gov/ndb/api/doc</a>
 */
public enum Endpoints {

    SEARCH("http://api.nal.usda.gov/ndb/search"),

    FOOD_REPORTS("http://api.nal.usda.gov/ndb/reports"),

    LISTS("http://api.nal.usda.gov/ndb/list"),

    NUTRIENT_REPORT("http://api.nal.usda.gov/ndb/nutrients");

    private final String mUrl;

    Endpoints(String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }
}
