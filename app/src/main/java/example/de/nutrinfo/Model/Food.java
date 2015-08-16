package example.de.nutrinfo.Model;

/**
 * Class representing a food-desc.
 * @see <a>http://ndb.nal.usda.gov/ndb/doc/apilist/API-FOOD-REPORT.md</a>
 */
public class Food {

    int mNdbNo;
    String mName;
    String mFdGrp;
    String mComName;
    String mManufacName;

    private int getNdbNo() {
        return mNdbNo;
    }

    private void setNdbNo(int ndbNo) {
        mNdbNo = ndbNo;
    }

    private String getName() {
        return mName;
    }

    private void setName(String name) {
        mName = name;
    }

    private String getFdGrp() {
        return mFdGrp;
    }

    private void setFdGrp(String fdGrp) {
        mFdGrp = fdGrp;
    }

    private String getComName() {
        return mComName;
    }

    private void setComName(String comName) {
        mComName = comName;
    }

    private String getManufacName() {
        return mManufacName;
    }

    private void setManufacName(String manufacName) {
        mManufacName = manufacName;
    }
}
