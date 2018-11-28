package com.chapman.dev.vincecarpino.final_project;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

public class Database extends Activity {
    SQLiteDatabase database;

    // insertIntoTable

    // deleteFromTable

    // selectFromTable

    public Database() {
        database = openOrCreateDatabase("ChappyFAFS", MODE_PRIVATE, null);

        database.execSQL("CREATE TABLE IF NOT EXISTS User("
                + "ID INT PRIMARY KEY AUTOINCREMENT,"
                + " Username VARCHAR,"
                + " Password VARCHAR,"
                + " Rating DECIMAL(1,1))");

        database.execSQL("CREATE TABLE IF NOT EXISTS Product("
                + "ID INT PRIMARY KEY AUTOINCREMENT,"
                + " Name VARCHAR,"
                + " Description VARCHAR,"
                + " CategoryID INT,"
                + " SellerID INT,"
                + " Price DECIMAL(4,2));");

        database.execSQL("CREATE TABLE IF NOT EXISTS Category("
                + "ID INT PRIMARY KEY AUTOINCREMENT,"
                + "Name VARCHAR);");
    }
}
