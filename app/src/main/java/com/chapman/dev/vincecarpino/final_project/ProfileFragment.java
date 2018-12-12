package com.chapman.dev.vincecarpino.final_project;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

// TODO: Populate items lists

public class ProfileFragment extends Fragment {

    private static final int ADD_ITEM_ID  = Menu.FIRST;
    private static final int LOGOUT_ID = Menu.FIRST + 1;
    int ID = Database.getCurrentUserId();
    Database db = Database.getInstance(getActivity());

    private TextView profileUsername;
    private TextView profileLocation;
    private RatingBar profileRating;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile, container, false);


        profileUsername = rootView.findViewById(R.id.usernameText);
        profileLocation = rootView.findViewById(R.id.locationText);
        profileRating   = rootView.findViewById(R.id.userRatingBar);
        //profileRating.setEnabled(false); //not clickable
        //profileRating.setClickable(false);
        profileRating.setIsIndicator(true);
        populateProfile(ID);
        Log.e("**********Profile", " OnCreateView");
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("**********Profile", " OnCreate");

        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("**********Profile", " OnResume");
        populateProfile(ID);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(Menu.NONE, ADD_ITEM_ID, Menu.NONE, "ADD ITEM");
        menu.add(Menu.NONE, LOGOUT_ID, Menu.NONE, "LOG OUT");
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
            case LOGOUT_ID:
                Intent goToLogin = new Intent(getActivity(), LoginActivity.class);
                startActivity(goToLogin);
                break;
        }

        transaction.commit();

        return true;
    }

    public void populateProfile(int id)
    {
        User user = db.getUserById(id);
        profileUsername.setText(user.getUsername());
        profileRating.setRating(user.getRating());

    }


}
