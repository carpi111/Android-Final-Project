package com.chapman.dev.vincecarpino.final_project;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends Activity {

    private BottomNavigationView menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        menu = findViewById(R.id.navigationView);

        menu.setOnNavigationItemSelectedListener(listener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case (R.id.navigation_profile):
                    Log.e("MAIN","CLICKED PROFILE");
                    break;
                case (R.id.navigation_home):
                    Log.e("MAIN","CLICKED HOME");
                    break;
                case (R.id.navigation_search):
                    Log.e("MAIN","CLICKED SEARCH");
                    break;
            }

             return true;
        }
    };
}
