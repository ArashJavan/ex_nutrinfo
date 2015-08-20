package example.de.nutrinfo.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        new FetchItemsTask().execute();
        return null;
    }


    private class FetchItemsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void...params) {
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
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
