package com.example.foodmunch.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.foodmunch.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public static  final String DB_NAME="FoodMunchDB.db";
    public static final String TABLE_NAME= "OdrerDetail";
    public static final String COL_1= "ID";
    public static final String COL_2= "ProductId";
    public static final String COL_3= "ProductName";
    public static final String COL_4= "Quantity";
    public static final String COL_5= "Price";
    public static final String COL_6= "Discount";

    public Database( Context context) {
        super(context, DB_NAME, null, 1);
        SQLiteDatabase db= this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, ProductId TEXT, ProductName TEXT, Quantity TEXT, Price TEXT, Discount TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }

    public List<Order> getCarts ()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect= {"ProductId", "ProductName", "Quantity", "Price", "Discount"};
        String sqlTable=TABLE_NAME;

        qb.setTables(sqlTable);
        Cursor c= qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Order> result = new ArrayList<>();
        if (c.moveToFirst())
        {
            do {
                result.add(new Order(c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount"))
                ));
            }while (c.moveToNext());
        }
        return result;
    }

    public void addToCart (Order order)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        String query =String.format("INSERT INTO " + TABLE_NAME + "(ProductId,ProductName,Quantity,Price,Discount)VALUES('%s','%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount());
        db.execSQL(query);
    }

    public void cleanCart ()
    {
        SQLiteDatabase db=getReadableDatabase();
        String query =String.format("DELETE FROM " +TABLE_NAME);
        db.execSQL(query);
    }
}