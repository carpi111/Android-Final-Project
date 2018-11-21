package com.chapman.dev.vincecarpino.final_project;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

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

//        createAccountText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                public Dialog onCreateDialog(Bundle savedInstanceState) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    // Get the layout inflater
//                    LayoutInflater inflater = getActivity().getLayoutInflater();
//
//                    // Inflate and set the layout for the dialog
//                    // Pass null as the parent view because its going in the dialog layout
//                    builder.setView(inflater.inflate(R.layout.dialog_signin, null))
//                            // Add action buttons
//                            .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int id) {
//                                    // sign in the user ...
//                                }
//                            })
//                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    LoginDialogFragment.this.getDialog().cancel();
//                                }
//                            });
//                    return builder.create();
//                }
//            }
//        });

    }
}
