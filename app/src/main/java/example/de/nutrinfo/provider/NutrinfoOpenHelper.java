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

    // Current version of database
    private static final int CUR_DATABASE_VERSION = 1;

    // Databse related infos
    private static String PACKAGE_NAME;
    private static String DB_PATH;
    private static String mDbName = "usda.sql3";

    private SQLiteDatabase mDataBase;
    private final Context mContext;

    interface Tables {
        String COMMON_NUTRIENT = "common_nutrient";
        String FOOD = "food";
        String FOOD_GROUP = "food_group";
        String NUTRIENT = "nutrient";
        String NUTRITION = "nutrition";
    }

    public NutrinfoOpenHelper(Context context, String dbname) {
        super(context, dbname, null, CUR_DATABASE_VERSION);
        mContext = context;
        mDbName = dbname;
        PACKAGE_NAME = context.getPackageName();

        // get the full path to the data base
        DB_PATH = String.format("//data//data//%s//databases//", PACKAGE_NAME);
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
        if (mDataBase != null) {
            mDataBase.close();
        }
        super.close();
    }


    public SQLiteDatabase openDatabase() {
        String path = DB_PATH + mDbName;
        if (mDataBase == null) {
            createDataBase();
            mDataBase = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        return mDataBase;
    }

    /**
     * open and create the database if it does not already exists
     */
    public void createDataBase() {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException ex) {
                LogUtils.e(TAG, "Error Copying databse: " + ex.getMessage());
            }
        } else {
            LogUtils.i(TAG, "Database already exists.");
        }
    }

    /**
     * check if the database already exist to avoid re-copying the file everytime app is opend
     * @return true if db exists, false if not
     */
    private boolean checkDataBase() {
        SQLiteDatabase db = null;

        try {
            return mContext.getDatabasePath(mDbName).exists();
        } catch (SQLiteException ex) {
            LogUtils.d(TAG, ex.getMessage());
            return false;
        }
    }

    /**
     * Copy the database located in the assets to application folder "/data/data/...."
     * @throws IOException
     */
    private void copyDataBase() throws IOException{

        AssetManager asset = mContext.getAssets();

        // local db as inputstream,
        //The stream source is located in the asset
        InputStream in = asset.open(mDbName);

        // path to db
        String outName = DB_PATH + mDbName;

        // open the empty db as outputstream
        OutputStream out = new FileOutputStream(outName);

        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer);
        }

        out.flush();
        out.close();
        in.close();
    }
}
