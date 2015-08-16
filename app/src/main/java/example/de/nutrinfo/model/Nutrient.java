package example.de.nutrinfo.model;

/**
 * metadata elements for each nutrient
 * included in the food report.
 * @see <a>http://ndb.nal.usda.gov/ndb/doc/apilist/API-FOOD-REPORT.md</a>
 */
public class Nutrient {

    private int mId;        // nutrient number
    private String mName;   // nutrient name
    private String mGroup;  // nutrient group
    private int mValue;     // 100 g equivalent value of the nutrient
    private String mUnit;   // unit of measure for this nutrient (mg, g, μg, and so on)

    private int getId() {
        return mId;
    }

    private void setId(int id) {
        mId = id;
    }

    private String getName() {
        return mName;
    }

    private void setName(String name) {
        mName = name;
    }

    private String getGroup() {
        return mGroup;
    }

    private void setGroup(String group) {
        mGroup = group;
    }

    private int getValue() {
        return mValue;
    }

    private void setValue(int value) {
        mValue = value;
    }

    private String getUnit() {
        return mUnit;
    }

    private void setUnit(String unit) {
        mUnit = unit;
    }
}
