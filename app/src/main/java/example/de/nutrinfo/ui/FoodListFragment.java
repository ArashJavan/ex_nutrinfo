package example.de.nutrinfo.ui;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import example.de.nutrinfo.R;
import example.de.nutrinfo.model.Food;
import example.de.nutrinfo.net.NutrinfoFetcher;
import example.de.nutrinfo.util.LogUtils;
import example.de.nutrinfo.util.CategoryEntry;

/**
 * Created by milux on 19.08.15.
 */
public class FoodListFragment extends Fragment {

    public static final String TAG = LogUtils.makeLogTag(FoodListFragment.class);

    private RecyclerView mRecyclerView;
    private ArrayList<Food> mFoods = new ArrayList<>();

    private ArrayList<Map.Entry<String, Integer>> mCatList = new ArrayList<>();

    private ArrayList<CategoryEntry> mFoodCategory = new CategoryEntry.Builder()
            .put("All", R.drawable.ic_all)
            .put("Dairy & Egg", R.drawable.ic_eggs)
            .put("Spices & Herbs", R.drawable.ic_vegan)
            .put("Baby Foods", R.drawable.ic_cooking_pot)
            .put("Fats & Oils", R.drawable.ic_wine_bottle)
            .put("Poultry", R.drawable.ic_chicken)
            .put("Soups, Sauces & Gravies", R.drawable.ic_soup)
            .put("Sausages & Luncheon Meats", R.drawable.ic_hotdog)
            .put("Breakfast Cereals", R.drawable.ic_bread)
            .put("Fruits & Fruit Juices", R.drawable.ic_fruit_juice)
            .put("Pork Products", R.drawable.ic_pig)
            .put("Vegetables", R.drawable.ic_sesame)
            .put("Nut & Seed Product", R.drawable.ic_nut)
            .put("Beef Products", R.drawable.ic_steak)
            .put("Beverages", R.drawable.ic_water)
            .put("Finfish & Shellfish", R.drawable.ic_fish)
            .put("Legumes & Legume", R.drawable.ic_corn)
            .put("Lamb", R.drawable.ic_lamb)
            .put("Baked Prod.", R.drawable.ic_cinnamon)
            .put("Sweets", R.drawable.ic_ice_cream)
            .put("Pasta", R.drawable.ic_spaghetti)
            .put("Fast Foods", R.drawable.ic_hamburger)
            .put("Meals & Entrees", R.drawable.ic_sushi)
            .put("Snacks", R.drawable.ic_wrap)
            .put("Restaurant Foods", R.drawable.ic_restaurant)
            .build();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foodlist, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_foodlist);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        CategoryAdapter adapter =
                new CategoryAdapter(getActivity(), mFoodCategory, R.layout.category_items);

        mRecyclerView.setAdapter(adapter);
        //new FetchItemsTask(adapter).execute();
        return view;
    }


    /**
     * Async-Worker for fetching the food-items.
     */
    private class FetchItemsTask extends AsyncTask<Void, Void, ArrayList<Food>> {
        CategoryAdapter mAdapter;

        public FetchItemsTask(CategoryAdapter adapter) {
            mAdapter = adapter;
        }

        @Override
        protected ArrayList<Food> doInBackground(Void...params) {
            int start, end, total;
            start = end = total = 0;

            ArrayList<Food> foods = mFoods;

            try {
                JSONObject jsonItems = new NutrinfoFetcher().getList(NutrinfoFetcher.ListTypes.FOOD_GROUP,
                        50, 0, NutrinfoFetcher.SortOrder.ID);

                JSONObject jsonList = jsonItems.getJSONObject("list");

                if (jsonList.has("start")) {
                    start = jsonList.getInt("start");
                }

                if (jsonList.has("end")) {
                    end = jsonList.getInt("end");
                }

                if (jsonList.has("total")) {
                    total = jsonList.getInt("total");
                }

                JSONArray jsonArray = jsonList.getJSONArray("item");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);

                    int id = 0;
                    String name = "";

                    if (item.has("id")) {
                        id = item.getInt("id");
                    }

                    if (item.has("name")) {
                        name = item.getString("name");
                    }

                    Food food = new Food.Builder(id, name).create();
                    foods.add(food);
                }

                return foods;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Food> result) {
            mAdapter.addItems(result);
        }
    }

    /**
     * Custom adapter class for listing food items insde a recyclerview
     */
    private class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

        List<CategoryEntry> mCategories;
        Context mContext;
        int mLayoutId;

        public CategoryAdapter(Context context, ArrayList<CategoryEntry> categories, int layoutId) {
            mCategories = categories;
            mContext = context;
            mLayoutId = layoutId;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            try {

                String categoryName = mCategories.get(position).getCategory();
                int id = mCategories.get(position).getId();

                holder.mCategory.setText(categoryName);
                holder.mImageView.setImageResource(id);
            } catch (Resources.NotFoundException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return mCategories.size();
        }

        public void addItem(CategoryEntry entry) {
            mCategories.add(entry);
            notifyDataSetChanged();
        }

        public void addItems(List<Food> items) {
            notifyDataSetChanged();
        }

        /**
         * Viewholder class for Recyclerview Adapter
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView mImageView;
            TextView mCategory;
            TextView mFoodCounts;

            public ViewHolder(View itemView) {
                super(itemView);
                mImageView = (ImageView) itemView.findViewById(R.id.card_view_img);
                mCategory = (TextView) itemView.findViewById(R.id.category_name);
                mFoodCounts = (TextView) itemView.findViewById(R.id.count_foods);
            }
        }
    }
}
