package example.de.nutrinfo.provider;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import example.de.nutrinfo.util.LogUtils;

/**
 * Created by milux on 05.09.15.
 */
public class NutrinfoOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = LogUtils.makeLogTag(NutrinfoOpenHelper.class);

    private static final int CUR_DATABASE_VERSION = 1;

    private static final String PACKAGE_NAME = "example.de.nutrinfo";

    private static String DB_NAME = "usda.sql3";
    private static String DB_PATH = "/data/data/" + PACKAGE_NAME + "/databases/";

    private NutrinfoOpenHelper mHelper = null;
    private Context mContext;

    interface Tables {
        String COMMON_NUTRIENT = "common_nutrient";
        String FOOD = "food";
        String FOOD_GROUP = "food_group";
        String NUTRIENT = "nutrient";
        String NUTRITION = "nutrition";
    }

    public NutrinfoOpenHelper(Context context) {
        super(context, DB_NAME, null, CUR_DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.d(TAG, "onCreate called.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public synchronized void close() {
    }

    /**
     * check if the database already exist to avoid re-copying the file everytime app is opend
     * @return true if db exists, false if not
     */
    private boolean checkDataBase() {
        SQLiteDatabase db = null;

        try {
            return mContext.getDatabasePath(DB_NAME).exists();
        } catch (SQLiteException ex) {
            LogUtils.d(TAG, ex.getMessage());
            return false;
        }
    }

    private void copyDataBase() {

        AssetManager asset = mContext.getAssets();

        try {
            // local db as inputstream
            InputStream in = asset.open(DB_NAME);

            // path to db
            final String outName = DB_PATH + DB_NAME;

            // open the empty db as outputstream
            OutputStream out = new FileOutputStream(outName);

            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer);
            }
        } catch (IOException ex) {
            LogUtils.e(TAG, "Error Copying database " + ex.getMessage());
        }
    }
}
