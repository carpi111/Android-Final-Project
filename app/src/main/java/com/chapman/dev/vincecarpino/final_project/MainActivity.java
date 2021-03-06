package com.chapman.dev.vincecarpino.final_project;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.Toolbar;

public class MainActivity extends Activity {

    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case (R.id.navigation_profile):
                    transaction.replace(R.id.container, new ProfileFragment());
                    getActionBar().show();
                    break;
                case (R.id.navigation_home):
                    transaction.replace(R.id.container, new FeedFragment());
                    getActionBar().hide();
                    break;
                case (R.id.navigation_search):
                    transaction.replace(R.id.container, new SearchFragment());
                    getActionBar().hide();
                    break;
                case (R.id.navigation_notifications):
                    transaction.replace(R.id.container, new NotificationsFragment());
                    getActionBar().hide();
                    break;
            }

            transaction.commit();

            return true;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        Toolbar actionbar = findViewById(R.id.action_bar);
        actionbar.setTitle("Your Profile");

        setActionBar(actionbar);

        BottomNavigationView menu;

        menu = findViewById(R.id.navigationView);

        menu.setOnNavigationItemSelectedListener(listener);

        menu.setSelectedItemId(R.id.navigation_home);

        int currentUserId = getIntent().getIntExtra("UserId", -1);

        Database.setCurrentUserId(currentUserId);
    }

    @Override
    public void onBackPressed() { }
}
