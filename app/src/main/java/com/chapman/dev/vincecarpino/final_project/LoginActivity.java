package com.chapman.dev.vincecarpino.final_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

    private Button loginBtn;
    private EditText usernameInput;
    private EditText passwordInput;
    private TextView createAccountText;

    private Cursor c;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Database db = new Database(this);

        String table = "User";
        String[] columnsToReturn = { "Username", "Password" };
        String selection = "Username=?";
        String[] selectionArgs = { "goldie" };
//        Cursor c = db.getReadableDatabase().query(table, columnsToReturn, selection, selectionArgs, null, null, null);
        Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM User WHERE Username=?", new String[] { "goldie" });

        c.moveToFirst();

        try {
            do {
                Log.e("\n\nDATA SELECT", c.getInt(1) + ", " + c.getString(2) + ", " + c.getString(3));
            } while (c.moveToNext());
        } catch (Exception e) {
            Log.e("\n\nDATABASE TESTING", e.getMessage());
        } finally {
            c.close();
        }

        loginBtn = findViewById(R.id.loginBtn);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        createAccountText = findViewById(R.id.createAccountText);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToFeed = new Intent(getApplicationContext(), FeedActivity.class);
                startActivity(goToFeed);
            }
        });

        createAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateAccountDialog();
            }
        });

    }

    public void showCreateAccountDialog()
    {
        final EditText usernameDialog;
        usernameDialog = findViewById(R.id.username);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.create_account_dialog, null))
                // Add action buttons
                .setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.e("login",usernameDialog.getText().toString());
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).show();
        //return builder.create();
    }
}
