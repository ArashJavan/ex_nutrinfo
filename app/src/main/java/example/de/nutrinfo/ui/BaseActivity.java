package example.de.nutrinfo.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;

import java.io.IOException;

import example.de.nutrinfo.R;
import example.de.nutrinfo.net.NutrinfoFetcher;

import static example.de.nutrinfo.util.LogUtils.makeLogTag;


public class BaseActivity extends AppCompatActivity {

    private final static String TAG = makeLogTag(BaseActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        FragmentManager fm = getSupportFragmentManager();
        FoodListFragment fragment = (FoodListFragment) fm.findFragmentById(R.id.container);

        if (fragment == null) {
            fragment = new FoodListFragment();
            fm.beginTransaction().add(R.id.container, fragment)
                    .addToBackStack(fragment.TAG).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
