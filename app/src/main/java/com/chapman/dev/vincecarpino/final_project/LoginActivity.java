package com.chapman.dev.vincecarpino.final_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

    private Button   loginBtn;
    private EditText usernameInput;
    private EditText passwordInput;

    private EditText createAcctUsernameInput;
    private EditText createAcctPasswordInput;
    private EditText createAcctConfPassInput;
    private TextView createAccountText;

    private Cursor c;
    Database db = new Database(this);

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginBtn          = findViewById(R.id.loginBtn);
        usernameInput     = findViewById(R.id.usernameInput);
        passwordInput     = findViewById(R.id.passwordInput);
        createAccountText = findViewById(R.id.createAccountText);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameString = usernameInput.getText().toString();
                String passwordString = passwordInput.getText().toString();

                int result = db.checkIfUserExists(usernameString, passwordString);

                if (result == -1) {
                    showInvalidCredentialsDialog();
                } else {
                    Intent goToMain = new Intent(getApplicationContext(), MainActivity.class);
                    goToMain.putExtra("UserId", result);
                    startActivity(goToMain);
                }
            }
        });

        createAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateAccountDialog();
            }
        });
    }

    public void showCreateAccountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View root = inflater.inflate(R.layout.create_account_dialog, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(root)
                .setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        createAcctUsernameInput = root.findViewById(R.id.username);
                        createAcctPasswordInput = root.findViewById(R.id.password);
                        createAcctConfPassInput = root.findViewById(R.id.passwordConfirmation);

                        String usernameString = createAcctUsernameInput.getText().toString();
                        String passwordString = createAcctPasswordInput.getText().toString();

                        if (!anyInputsAreEmpty() && createAccountPasswordsIdentical()) {
                            User newUser = new User(usernameString, passwordString);

                            db.insertIntoUser(newUser);
                        }

//                        Intent goToMain = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(goToMain);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { }
                }).show();
        //return builder.create();
    }

    private void showInvalidCredentialsDialog() {
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage("We couldn't find an account with that info.\n\nWould you like to create one now?")
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                })
                .setPositiveButton("SURE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showCreateAccountDialog();
                    }
                }).show();
    }

    private boolean anyInputsAreEmpty() {
        return createAcctUsernameInput.getText().toString().trim().equals("")
                || createAcctPasswordInput.getText().toString().trim().equals("")
                || createAcctConfPassInput.getText().toString().trim().equals("");
    }

    private boolean createAccountPasswordsIdentical() {
        return createAcctPasswordInput.getText().toString().equals(createAcctConfPassInput.getText().toString());
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            anyInputsAreEmpty();
        }
    };
}
