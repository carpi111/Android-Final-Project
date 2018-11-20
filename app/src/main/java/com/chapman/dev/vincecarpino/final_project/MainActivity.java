package com.chapman.dev.vincecarpino.final_project;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

        menu.setSelectedItemId(R.id.navigation_home);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case (R.id.navigation_profile):
                    transaction.replace(R.id.container, new ProfileFragment());
                    transaction.commit();
                    break;
                case (R.id.navigation_home):
                    Log.e("MAIN","CLICKED HOME");
                    transaction.replace(R.id.container, new FeedFragment());
                    transaction.commit();
                    break;
                case (R.id.navigation_search):
                    Log.e("MAIN","CLICKED SEARCH");
                    transaction.replace(R.id.container, new ProfileFragment());
                    transaction.commit();
                    break;
                case (R.id.navigation_notifications):
                    Log.e("MAIN","CLICKED SEARCH");
                    transaction.replace(R.id.container, new NotificationsFragment());
                    transaction.commit();
                    break;
            }

            return true;
        }
    };
}
