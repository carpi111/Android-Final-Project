package com.chapman.dev.vincecarpino.final_project;

import android.content.Context;
import android.database.sqlite.*;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ChappyFAFS";

    // deleteFromTable

    // selectFromTable

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS User("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " Username VARCHAR,"
                + " Password VARCHAR,"
                + " Rating DECIMAL(1,1));";
        SQLiteStatement stmt = this.getWritableDatabase().compileStatement(sql);
        stmt.execute();

        sql = "CREATE TABLE IF NOT EXISTS Product("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " Name VARCHAR,"
                + " Description VARCHAR,"
                + " CategoryID INTEGER,"
                + " SellerID INTEGER,"
                + " Price DECIMAL(4,2));";
        stmt = this.getWritableDatabase().compileStatement(sql);
        stmt.execute();

        sql = "CREATE TABLE IF NOT EXISTS Category("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "Name VARCHAR);";
        stmt = this.getWritableDatabase().compileStatement(sql);
        stmt.execute();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertIntoUser(String username, String password, float rating)
    {
        String sql = "INSERT INTO User(Username, Password, Rating) VALUES(?,?,?);";
        SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);
        stmt.bindString(1, username);
        stmt.bindString(2, password);
        stmt.bindDouble(3, rating);

        stmt.executeInsert();
    }

    public void insertIntoProduct(String name, String description, int categoryID, int sellerID, float price)
    {
        String sql = "INSERT INTO Product(Name, Desription, CategoryID, SellerID, Price) VALUES(?,?,?,?,?);";
        SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);
        stmt.bindString(1, name);
        stmt.bindString(2, description);
        stmt.bindDouble(3, categoryID);
        stmt.bindDouble(4, sellerID);
        stmt.bindDouble(5, price);

        stmt.executeInsert();
    }

    public void makeCategoryTable()
    {
        String[] categories = {"Art", "Books", "Clothing", "Crafts", "Electronics", "Everything else",
        "Furniture", "Health & Beauty", "Jewelry", "Musical Instruments", "Real Estate", "Sporting Goods"};

        for (String c : categories)
        {
            String sql = "INSERT INTO Category(Name) VALUES(?);";
            SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);
            stmt.bindString(1,c);

            stmt.executeInsert();
        }
    }

    public void deleteProduct(int id)
    {
        String sql = "DELETE FROM Product WHERE ID=" + String.valueOf(id);
        SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);
        stmt.executeInsert();
    }

    public String getUsername(int id)
    {
        String sql = "SELECT Username FROM User WHERE ID=" + String.valueOf(id);
        SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);
        String username = stmt.simpleQueryForString();

        return username;
    }

//    public ArrayList<String> getProductDetails(int id)
//    {
//        ArrayList<String> productDetails = new ArrayList<>();
//
//        String sql = "SELECT Name, Description, CategoryID, sellerID, Price FROM Product WHERE ID=" + String.valueOf(id);
//        SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);
//        //not finished
//    }


}
