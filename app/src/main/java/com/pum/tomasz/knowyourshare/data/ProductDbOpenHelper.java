package com.pum.tomasz.knowyourshare.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.pum.tomasz.knowyourshare.Utilities;

/**
 * Created by tomasz on 23.07.2015.
 */
public class ProductDbOpenHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "products";
    public static final int DBVERSION = 1;
    public static final String TABLE_PRODUCT = "product";

    public ProductDbOpenHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(Utilities.TAG, "Creating new database...");
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE ").append(TABLE_PRODUCT).append(" (");
        sqlBuilder.append("_id INTEGER PRIMARY KEY, ");
        sqlBuilder.append("name TEXT NOT NULL, ");
        sqlBuilder.append("buy_date TEXT NOT NULL, ");
        sqlBuilder.append("end_of_usage_date TEXT, ");
        sqlBuilder.append("size REAL, ");
        sqlBuilder.append("unit TEXT NOT NULL, ");
        sqlBuilder.append("expiration_date TEXT, ");
        sqlBuilder.append("price REAL ");
        sqlBuilder.append(");");

        try {
            db.execSQL(sqlBuilder.toString());
        } catch (SQLException ex) {
            Log.e(Utilities.TAG, "Error creating application database.", ex);
        }
        Log.d(Utilities.TAG, "... database creation finished.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(Utilities.TAG,"ProductDbOpenHelper onUpgrade called");
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

}
