package com.chapman.dev.vincecarpino.final_project;

import android.content.Context;
import android.database.sqlite.*;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ChappyFAFS";

    // insertIntoTable

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
                + " Rating DECIMAL(1,1))";
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

//    private static void createDatabase() {
//        database = SQLiteDatabase.openOrCreateDatabase("ChappyFAFS", MODE_PRIVATE, null);
//    }
//
//    private void createTables() {
//        database.execSQL("CREATE TABLE IF NOT EXISTS User("
//                + "ID INT PRIMARY KEY AUTOINCREMENT,"
//                + " Username VARCHAR,"
//                + " Password VARCHAR,"
//                + " Rating DECIMAL(1,1))");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS Product("
//                + "ID INT PRIMARY KEY AUTOINCREMENT,"
//                + " Name VARCHAR,"
//                + " Description VARCHAR,"
//                + " CategoryID INT,"
//                + " SellerID INT,"
//                + " Price DECIMAL(4,2));");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS Category("
//                + "ID INT PRIMARY KEY AUTOINCREMENT,"
//                + "Name VARCHAR);");
//    }
}
