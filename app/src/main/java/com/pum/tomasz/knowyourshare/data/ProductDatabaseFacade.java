package com.pum.tomasz.knowyourshare.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pum.tomasz.knowyourshare.Utilities;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tomasz on 23.07.2015.
 */
public class ProductDatabaseFacade {
    private SQLiteDatabase db;


    public ProductDatabaseFacade(SQLiteDatabase db) {
        this.db = db;
    }

    public void dispose() {
        if (db != null && db.isOpen()) {
            db.close();
        }
        db = null;
    }

    public void insert(Product p) {
        validate();
        ContentValues v = new ContentValues();
        v.put("name", p.getName());
        v.put("buy_date", Utilities.convertDateToString(p.getBuyDate()));
        v.put("size", p.getSize());
        v.put("unit", p.getMeasureUnitString());

        long id = db.insert(ProductDbOpenHelper.TABLE_PRODUCT, null, v);
        if (id >= 0) {
            p.setId(id);
        }
    }

    public boolean update(Product p) {
        validate();
        ContentValues v = new ContentValues();
        v.put("name", p.getName());
        v.put("buy_date", Utilities.convertDateToString(p.getBuyDate()));
        v.put("size", p.getSize());
        v.put("unit", p.getMeasureUnitString());

        int rowsAffected = db.update(ProductDbOpenHelper.TABLE_PRODUCT, v, "_id="
                + p.getId(), null);

        return (rowsAffected == 1);
    }


    public boolean delete(Product p) {
        return delete(p.getId());
    }

    public boolean delete(long id) {
        validate();
        return (1 == db
                .delete(ProductDbOpenHelper.TABLE_PRODUCT, "_id=" + id, null));
    }


    public Product getById(long id) {
        validate();
        Cursor cur = null;
        try {
            cur = db.query(true, ProductDbOpenHelper.TABLE_PRODUCT, null /* all */,
                    "_id=" + id, null, null, null, null, null);
            List<Product> tmpList = new LinkedList<Product>();
            extractProductsFromCursor(tmpList, cur);
            if (tmpList != null && !tmpList.isEmpty()) {
                return tmpList.get(0);
            } else {
                return null;
            }
        } catch (SQLException e) {
            Log.e(Utilities.TAG, "Error searching application database.", e);
            return null;
        } finally {
            if (cur != null && !cur.isClosed()) {
                cur.close();
            }
        }
    }

    public List<Product> findByName(String name) {
        validate();
        List<Product> result = new LinkedList<Product>();
        Cursor cur = null;
        try {
            cur = db.query(true, ProductDbOpenHelper.TABLE_PRODUCT, null /* all */,
                    "name='" + name + "'", null, null, null, "date", null);
            extractProductsFromCursor(result, cur);
        } catch (SQLException e) {
            Log.e("topics.database", "Error searching application database.", e);
        } finally {
            if (cur != null && !cur.isClosed()) {
                cur.close();
            }
        }

        return Collections.unmodifiableList(result);
    }


    public List<Product> listAll() {
        validate();
        List<Product> result = new LinkedList<Product>();
        Cursor cur = null;
        try {
            cur = db.query(true, ProductDbOpenHelper.TABLE_PRODUCT, null /* all */,
                    null, null, null, null, "name", null);
            extractProductsFromCursor(result, cur);
        } catch (SQLException e) {
            Log.e("topics.database", "Error searching application database.", e);
        } finally {
            if (cur != null && !cur.isClosed()) {
                cur.close();
            }
        }

        return Collections.unmodifiableList(result);
    }


    private Cursor getCursorForAllProducts() {
        validate();
        Cursor cur = null;
        try {
            cur = db.query(true, ProductDbOpenHelper.TABLE_PRODUCT, null /* all */,
                    null, null, null, null, "name", null);
        } catch (SQLException e) {
            Log.e(Utilities.TAG, "Error searching application database.", e);
            cur = null;
        }
        return cur;
    }

    public List<Product> listAllToday(){
        validate();
        List<Product> result = new LinkedList<Product>();
        Cursor cur = null;
        String todayDayString = Utilities.convertDateToString(new Date());
        Log.d(Utilities.TAG,"Today date is: "+todayDayString);
        try {
            cur = db.query(true, ProductDbOpenHelper.TABLE_PRODUCT, null /* all */,
                    "buy_date="+"\""+todayDayString+"\"", null, null, null, "name", null);
            extractProductsFromCursor(result, cur);
        } catch (SQLException e) {
            Log.e("topics.database", "Error searching application database.", e);
        } finally {
            if (cur != null && !cur.isClosed()) {
                cur.close();
            }
        }

        return Collections.unmodifiableList(result);
    }

    private Cursor getCursorForAllTodayProducts() {
        validate();
        Cursor cur = null;
        String todayDayString = Utilities.convertDateToString(new Date());
        try {
            cur = db.query(true, ProductDbOpenHelper.TABLE_PRODUCT, null /* all */,
                    "buy_date="+"\""+todayDayString+"\"", null, null, null, "name", null);
        } catch (SQLException e) {
            Log.e(Utilities.TAG, "Error searching application database.", e);
            cur = null;
        }
        return cur;
    }


    private void extractProductsFromCursor(List<Product> list, Cursor cur) {
        if (cur.moveToFirst()) {
            for (int i = 0; i < cur.getCount(); i++) {
                Product p = new Product();
                p.setId(cur.getLong(ProductRecordColumnsEnum.id.getIndex()));
                p.setName(cur.getString(ProductRecordColumnsEnum.name.getIndex()));
                p.setBuyDate(cur.getString(ProductRecordColumnsEnum.buyDate.getIndex()));
                p.setSize(cur.getDouble(ProductRecordColumnsEnum.size.getIndex()));

                String unitStr =  cur.getString(ProductRecordColumnsEnum.unit.getIndex());
                MeasureUnitTypeEnum unitEnum = MeasureUnitTypeEnum.valueOf(unitStr);

                p.setMeasureUnitTypeEnum(unitEnum);

                double price = cur.getDouble(ProductRecordColumnsEnum.price.getIndex());
                p.setPrice(price);

                list.add(p);

                cur.moveToNext();
            }
        }
    }


    private void validate() {
        if (db == null) {
            throw new IllegalStateException(
                    "Illegal access to the disposed ProductDbHelper object.");
        }
    }

    public Cursor getCursor(ProductsListConfigurationEnum productsListConfiguration) {
        Cursor result = null;
        switch (productsListConfiguration){
            case ALL_PRODUCTS:
                result = getCursorForAllProducts();
                break;
            case TODAY_PRODUCTS:
                result =  getCursorForAllTodayProducts();
                break;
        }
        return result;
    }
}
