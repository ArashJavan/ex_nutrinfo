package example.de.nutrinfo.ui;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import example.de.nutrinfo.R;
import example.de.nutrinfo.model.Food;
import example.de.nutrinfo.net.NutrinfoFetcher;
import example.de.nutrinfo.util.LogUtils;

/**
 * Created by milux on 19.08.15.
 */
public class FoodListFragment extends Fragment {

    public static final String TAG = LogUtils.makeLogTag(FoodListFragment.class);

    private RecyclerView mRecyclerView;
    private ArrayList<Food> mFoods = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foodlist, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_foodlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,
                false));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        NutrinfoListAdpater adapter =
                new NutrinfoListAdpater(getActivity(), mFoods, R.layout.fooditem);

        mRecyclerView.setAdapter(adapter);
        new FetchItemsTask(adapter).execute();
        return view;
    }


    /**
     * Async-Worker for fetching the food-items.
     */
    private class FetchItemsTask extends AsyncTask<Void, Void, ArrayList<Food>> {
        NutrinfoListAdpater mAdapter;

        public FetchItemsTask(NutrinfoListAdpater adapter) {
            mAdapter = adapter;
        }

        @Override
        protected ArrayList<Food> doInBackground(Void...params) {
            int start, end, total;
            start = end = total = 0;

            ArrayList<Food> foods = mFoods;

            try {
                JSONObject jsonItems = new NutrinfoFetcher().getList(NutrinfoFetcher.ListTypes.FOOD,
                        50, 0, NutrinfoFetcher.SortOrder.NAME);

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
    private class NutrinfoListAdpater extends RecyclerView.Adapter<NutrinfoListAdpater.ViewHolder> {

        ArrayList<Food> mFoods;
        Context mContext;
        int mLayoutId;

        public NutrinfoListAdpater(Context context, ArrayList<Food> foods, int layoutId) {
            mFoods = foods;
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
                String name = mFoods.get(position).getName();
                holder.mName.setText(name);
            } catch (Resources.NotFoundException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return mFoods.size();
        }

        public void addItem(Food item) {
            mFoods.add(item);

            notifyDataSetChanged();
        }

        public void addItems(List<Food> items) {
            mFoods.addAll(items);
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView mName;

            public ViewHolder(View itemView) {
                super(itemView);
                mName = (TextView) itemView.findViewById(R.id.food_name);
            }
        }
    }
}
