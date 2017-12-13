package com.example.mriley2.stocktracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

/**
 * Created by Mike on 12/5/17.
 */

public class DBAdapter {

    static final String KEY_ROWID = "_id";
    static final String KEY_SYMBOL = "symbol";
    static final String KEY_COMPANY_NAME = "companyName";
    static final String TAG = "StockSQLiteHelper";
    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "stocks";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE = "create table " + DATABASE_TABLE +
            " (" + KEY_ROWID + " integer primary key autoincrement, "
            + KEY_SYMBOL + " text not null, " + KEY_COMPANY_NAME + " text not null);";

    final Context context;
    StockSQLiteHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new StockSQLiteHelper(context);
    }

    public static class StockSQLiteHelper extends SQLiteOpenHelper {
        StockSQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }


    // Opens the database
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    // Closes the database
    public void close() {

        DBHelper.close();
    }

    // Clears all information in the table
    public void emptyTable() {

        db.execSQL("delete from " + DATABASE_TABLE);
    }

    public void addStock(Stock stock){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SYMBOL, stock.getSymbol());
        initialValues.put(KEY_COMPANY_NAME, stock.getCompanyName());
        long stockID = db.insert(DATABASE_TABLE, null, initialValues);
        int id = (int) stockID;
        stock.setID(id);
    }

    public ArrayList<Stock> getAllStocks() {
        Cursor mCursor = db.query(DATABASE_TABLE, null, null,
                null, null, null, null, null);

        ArrayList stockList = new ArrayList();
        if (mCursor.moveToFirst()){
            do {
                int id = Integer.parseInt(mCursor.getString(0));
                String symbol = mCursor.getString(1);
                String companyName = mCursor.getString(2);
                Stock newStock = new Stock(symbol, companyName);
                newStock.setID(id);
                stockList.add(newStock);

            } while (mCursor.moveToNext());
        }
        return stockList;
    }

    public boolean deleteStock(Stock stock){
        long rowID = new Long(stock.getID());

        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowID, null) > 0;
    }
}