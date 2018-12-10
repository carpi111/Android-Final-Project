package com.chapman.dev.vincecarpino.final_project;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class ProfileFragment extends Fragment {

    private static final int ADD_ITEM_ID  = Menu.FIRST;
    private static final int EDIT_PROF_ID = Menu.FIRST + 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        int id = Database.getCurrentUserId();
        Log.e("***********ID:", String.valueOf(id));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(Menu.NONE, ADD_ITEM_ID, Menu.NONE, "ADD ITEM");
        menu.add(Menu.NONE, EDIT_PROF_ID, Menu.NONE, "EDIT PROFILE");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (item.getItemId()) {
            case ADD_ITEM_ID:
                transaction.replace(R.id.container, new AddItemFragment());
                break;
            case EDIT_PROF_ID:
                // TODO: Edit profile page
                break;
        }

        transaction.commit();

        return true;
    }
}
