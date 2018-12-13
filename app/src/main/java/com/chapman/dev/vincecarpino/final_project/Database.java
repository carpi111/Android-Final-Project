package com.chapman.dev.vincecarpino.final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;

import java.util.ArrayList;


public class Database extends SQLiteOpenHelper {
    private static int CURRENT_USER_ID = -1;
    private static Database sInstance;
    private static final String DATABASE_NAME  = "ChappyFAFS";
    private static final String USER_TABLE     = "User";
    private static final String PRODUCT_TABLE  = "Product";
    private static final String CATEGORY_TABLE = "Category";
    private static final String[] userColumns  = {
            "Username",
            "Password",
            "Rating"
    };
    private static final String[] productColumns = {
            "Name",
            "Description",
            "CategoryID",
            "SellerID",
            "BuyerID",
            "Price",
            "IsSold"
    };
    private static final String[] categories = {
            "Art",
            "Books",
            "Clothing",
            "Crafts",
            "Electronics",
            "Everything else",
            "Furniture",
            "Health & Beauty",
            "Jewelry",
            "Musical Instruments",
            "Real Estate",
            "Sporting Goods"
    };

    private Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static synchronized Database getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Database(context);
        }

        return sInstance;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);

        createProductTable(db);

        createCategoryTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    private void createUserTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS User("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Username VARCHAR, "
                + "Password VARCHAR, "
                + "Rating DECIMAL(1,1));";
        db.execSQL(sql);
    }

    private void createProductTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS Product("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Name VARCHAR, "
                + "Description VARCHAR, "
                + "CategoryID INTEGER, "
                + "SellerID INTEGER, "
                + "BuyerID INTEGER, "
                + "Price DECIMAL(4,2), "
                + "IsSold TINYINT(1));";
        db.execSQL(sql);
    }

    private void createCategoryTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS Category("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Name VARCHAR);";
        db.execSQL(sql);

        populateCategoryTable(db);
    }

    public void insertIntoUser(User u) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();

            values.put(userColumns[0], u.getUsername());
            values.put(userColumns[1], u.getPassword());
            values.put(userColumns[2], 0f);

            db.insertOrThrow(USER_TABLE, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("DATABASE", "Error adding to User table");
        } finally {
            db.endTransaction();
        }
    }

    public void insertIntoProduct(Product p) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();

            values.put(productColumns[0], p.getName());
            values.put(productColumns[1], p.getDescription());
            values.put(productColumns[2], p.getCategoryId());
            values.put(productColumns[3], p.getSellerId());
            values.put(productColumns[4], p.getBuyerId());
            values.put(productColumns[5], p.getPrice());
            values.put(productColumns[6], p.getIsSold());

            db.insertOrThrow(PRODUCT_TABLE, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("DATABASE", "Error adding to Product table");
        } finally {
            db.endTransaction();
        }
    }

    private void populateCategoryTable(SQLiteDatabase db) {
        for (String c : categories) {

            db.beginTransaction();

            try {
                ContentValues values = new ContentValues();

                values.put("Name", c);

                db.insertOrThrow(CATEGORY_TABLE, null, values);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.e("DATABASE", "Error adding to Category table");
            } finally {
                db.endTransaction();
            }
        }
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM User WHERE ID=?;";

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, new String[] { String.valueOf(id) });
        c.moveToFirst();
        User user = new User();

        user.setId((c.getInt(0)));
        user.setUsername(c.getString(1));
        user.setPassword(c.getString(2));
        user.setRating((c.getFloat(3)));

        c.close();

        return user;
    }

    public int checkIfUserExists(String username, String password) {
        int idOfResult;
        String sql = "SELECT * FROM User WHERE Username=? AND Password=?;";
        Cursor c = this.getReadableDatabase().rawQuery(sql, new String[] { username, password });

        c.moveToFirst();

        idOfResult = c.getCount() == 0 ? -1 : c.getInt(0);

        c.close();

        return idOfResult;
    }

    public int getCategoryIdByName(String name) {
        int idOfResult;
        String sql = "SELECT ID FROM Category WHERE Name=?;";
        Cursor c = this.getReadableDatabase().rawQuery(sql, new String[] { name });

        c.moveToFirst();

        idOfResult = c.getInt(0);

        c.close();

        return idOfResult;
    }

    public ArrayList<Product> getAllUnsoldProducts() {
        ArrayList<Product> results = new ArrayList<>();

        String sql = "SELECT * FROM Product WHERE IsSold=?;";
        Cursor c = this.getReadableDatabase().rawQuery(sql, new String[] { "0" });

        while (c.moveToNext()) {
            Product p = new Product();
            p.setId(c.getInt(0));
            p.setName(c.getString(1));
            p.setDescription(c.getString(2));
            p.setCategoryId(c.getInt(3));
            p.setSellerId(c.getInt(4));
            p.setBuyerId(c.getInt(5));
            p.setPrice(c.getFloat(6));

            results.add(p);
        }

        c.close();

        return results;
    }

    public ArrayList<Product> getAllItemsBoughtByCurrentUser() {
        ArrayList<Product> results = new ArrayList<>();

        String sql = "SELECT * FROM Product WHERE IsSold=? AND BuyerID=?;";
        Cursor c = this.getReadableDatabase().rawQuery(sql, new String[] { "1", String.valueOf(Database.getCurrentUserId()) });

        while (c.moveToNext()) {
            Product p = new Product();
            p.setId(c.getInt(0));
            p.setName(c.getString(1));
            p.setDescription(c.getString(2));
            p.setCategoryId(c.getInt(3));
            p.setSellerId(c.getInt(4));
            p.setBuyerId(c.getInt(5));
            p.setPrice(c.getFloat(6));

            results.add(p);
        }

        c.close();

        return results;
    }

    public ArrayList<Product> getAllItemsSoldByCurrentUser() {
        ArrayList<Product> results = new ArrayList<>();

        String sql = "SELECT * FROM Product WHERE IsSold=? AND SellerID=?;";
        Cursor c = this.getReadableDatabase().rawQuery(sql, new String[] { "1", String.valueOf(Database.getCurrentUserId()) });

        while (c.moveToNext()) {
            Product p = new Product();
            p.setId(c.getInt(0));
            p.setName(c.getString(1));
            p.setDescription(c.getString(2));
            p.setCategoryId(c.getInt(3));
            p.setSellerId(c.getInt(4));
            p.setBuyerId(c.getInt(5));
            p.setPrice(c.getFloat(6));

            results.add(p);
        }

        c.close();

        return results;
    }

    public ArrayList<Product> getAllItemsUnsoldByCurrentUser() {
        ArrayList<Product> results = new ArrayList<>();

        String sql = "SELECT * FROM Product WHERE IsSold=? AND SellerID=?;";
        Cursor c = this.getReadableDatabase().rawQuery(sql, new String[] { "0", String.valueOf(Database.getCurrentUserId()) });

        while (c.moveToNext()) {
            Product p = new Product();
            p.setId(c.getInt(0));
            p.setName(c.getString(1));
            p.setDescription(c.getString(2));
            p.setCategoryId(c.getInt(3));
            p.setSellerId(c.getInt(4));
            p.setBuyerId(c.getInt(5));
            p.setPrice(c.getFloat(6));

            results.add(p);
        }

        c.close();

        return results;
    }

    public String getCategoryNameById(int id) {
        String nameOfResult;
        String sql = "SELECT Name FROM Category WHERE ID=?;";
        Cursor c = this.getReadableDatabase().rawQuery(sql, new String[] { String.valueOf(id) });
        c.moveToFirst();

        nameOfResult = c.getString(0);

        c.close();

        return nameOfResult;
    }

    public static void setCurrentUserId(int id) {
        CURRENT_USER_ID = id;
    }

    public static int getCurrentUserId() {
        return CURRENT_USER_ID;
    }

    public float getUserRatingById(int id) {
        Log.e("************DATABASE", String.valueOf(id));
        float currentRating;
        String sql = "SELECT Rating FROM User WHERE ID=?;";
        Cursor c = this.getReadableDatabase().rawQuery(sql, new String[] { String.valueOf(id) });
        c.moveToFirst();

        currentRating = c.getFloat(0);

        c.close();

        return currentRating;
    }

    public void updateUserRating(int id, float newRating) {
        float updateValue = getUserRatingById(id) == 0 ? newRating : ((getUserRatingById(id) + newRating) / 2f);

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(userColumns[2], updateValue);
        db.update(USER_TABLE, values, "ID=?", new String[] { String.valueOf(id) });
    }

    public void updateBuyerIdOfProductByProductId(int id) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(productColumns[4], Database.getCurrentUserId());
        values.put(productColumns[6], 1);
        db.update(PRODUCT_TABLE, values, "ID=?", new String[] { String.valueOf(id) });
    }
}
