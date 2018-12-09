package com.chapman.dev.vincecarpino.final_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

//TODO: SERVICE????????????????
public class Database extends SQLiteOpenHelper {

    SQLiteDatabase myDB;
    private static final String DATABASE_NAME = "ChappyFAFS";
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

    // deleteFromTable

    // selectFromTable

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //myDB = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        SQLiteDatabase.CursorFactory factory = new SQLiteDatabase.CursorFactory() {
            @Override
            public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
                return null;
            }
        };

        myDB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, factory, null);

//        if (getCountOfCategoryTable() != categories.length) {
//            String sql = "DROP TABLE IF EXISTS Category;";
//            SQLiteStatement stmt = this.getWritableDatabase().compileStatement(sql);
//            stmt.executeUpdateDelete();
//
//            createCategoryTable();
//
//            populateCategoryTable();
//        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable();

        createProductTable();

        createCategoryTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    private void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS User("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Username VARCHAR, "
                + "Password VARCHAR, "
                + "Rating DECIMAL(1,1));";
        SQLiteStatement stmt = this.getWritableDatabase().compileStatement(sql);
        stmt.execute();
    }

    private void createProductTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Product("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Name VARCHAR, "
                + "Description VARCHAR, "
                + "CategoryID INTEGER, "
                + "SellerID INTEGER, "
                + "Price DECIMAL(4,2));";
        SQLiteStatement stmt = this.getWritableDatabase().compileStatement(sql);
        stmt.execute();
    }

    private void createCategoryTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Category("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Name VARCHAR);";
        SQLiteStatement stmt = this.getWritableDatabase().compileStatement(sql);
        stmt.execute();
    }

    public void insertIntoUser(User u) {
        String sql = "INSERT INTO User(Username, Password) VALUES(?,?);";
        SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);
        stmt.bindString(1, u.getUsername());
        stmt.bindString(2, u.getPassword());

        stmt.executeInsert();

        // TODO: return id of new user
    }

    public void insertIntoProduct(Product p) {
        String sql = "INSERT INTO Product(Name, Desription, CategoryID, SellerID, Price) VALUES(?,?,?,?,?);";
        SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);
        stmt.bindString(1, p.getName());
        stmt.bindString(2, p.getDescription());
        stmt.bindDouble(3, p.getCategoryId());
        stmt.bindDouble(4, p.getSellerId());
        stmt.bindDouble(5, p.getPrice());

        stmt.executeInsert();
    }

    private long getCountOfCategoryTable() {
        String sql = "SELECT COUNT(*) FROM Category;";
        SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);

        return stmt.simpleQueryForLong();
    }

    private void populateCategoryTable() {
        for (String c : categories) {
            String sql = "INSERT INTO Category(Name) VALUES(?);";
            SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);
            stmt.bindString(1, c);

            stmt.executeInsert();
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM Product WHERE ID=" + String.valueOf(id);
        SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);
        stmt.executeInsert();
    }

    public String getUsername(int id) {
        String sql = "SELECT Username FROM User WHERE ID=" + String.valueOf(id);
        SQLiteStatement stmt = this.getReadableDatabase().compileStatement(sql);
        String username = stmt.simpleQueryForString();

        return username;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM User WHERE ID=?;";
        Cursor c = this.getReadableDatabase().rawQuery(sql, new String[] { String.valueOf(id) });

        User user = new User();

        user.setId(Integer.valueOf(c.getString(1)));
        user.setUsername(c.getString(2));
        user.setPassword(c.getString(3));
        user.setRating(Float.valueOf(c.getString(4)));

        c.close();

        return user;
    }

    public int checkIfUserExists(String username, String password) {
        int idOfResult;
        String sql = "SELECT * FROM User WHERE Username=? AND Password=?;";
        Cursor c = this.getReadableDatabase().rawQuery(sql, new String[] { username, password });

        c.moveToFirst();

        idOfResult = c.getCount() == 0 ? -1 : c.getInt(1);

        c.close();

        return idOfResult;
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
