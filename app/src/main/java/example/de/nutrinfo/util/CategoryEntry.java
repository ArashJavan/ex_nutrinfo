package example.de.nutrinfo.util;

import java.util.ArrayList;

/**
 * Created by milux on 28.08.15.
 */
public class CategoryEntry {
    private String mCategory;
    private int mId;

    public CategoryEntry(String category, int id) {
        mCategory = category;
        mId = id;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public static class Builder {
        private ArrayList<CategoryEntry> mList = new ArrayList<>();

        public Builder put(String cat, int id) {
            mList.add(new CategoryEntry(cat, id));
            return this;
        }

        public ArrayList<CategoryEntry> build() {
            return mList;
        }
    }
}
