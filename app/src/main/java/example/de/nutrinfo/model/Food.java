package example.de.nutrinfo.model;

import android.net.Uri;

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

    public Food () {}

    public Food(int ndbNo, String name, String fdGrp, String comName, String manufacName) {
        mNdbNo = ndbNo;
        mName = name;
        mFdGrp = fdGrp;
        mComName = comName;
        mManufacName = manufacName;
    }

    public int getNdbNo() {
        return mNdbNo;
    }

    public void setNdbNo(int ndbNo) {
        mNdbNo = ndbNo;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getFdGrp() {
        return mFdGrp;
    }

    public void setFdGrp(String fdGrp) {
        mFdGrp = fdGrp;
    }

    public String getComName() {
        return mComName;
    }

    public void setComName(String comName) {
        mComName = comName;
    }

    public String getManufacName() {
        return mManufacName;
    }

    public void setManufacName(String manufacName) {
        mManufacName = manufacName;
    }

    /**
     * Food builder class
     * example: Food food = Food.Builder(id, name)
     *                      .fdGrp("group)
     *                      .comName("company_name")
     *                      .create()
     */
    public static class Builder {
        int ndbNo;
        String name;
        String fdGrp;
        String comName;
        String manufacName;

        public Builder() {}

        public Builder(final int ndbNo, final String name) {
            this.ndbNo = ndbNo;
            this.name = name;
        }

        public Builder ndbNo(final int ndbNo) {
            this.ndbNo = ndbNo;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder fdGrp(final String fdGrp) {
            this.fdGrp = fdGrp;
            return this;
        }

        public Builder comName(final String comName) {
            this.comName = comName;
            return this;
        }

        public Builder manufacName(final String manufacName) {
            this.manufacName = manufacName;
            return this;
        }

        public Food create() {
            return new Food(ndbNo, name, fdGrp, comName, manufacName);
        }
    }
}
