package com.chapman.dev.vincecarpino.final_project;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

// TODO: Populate items lists

public class ProfileFragment extends Fragment {
    private static final int ADD_ITEM_ID  = Menu.FIRST;
    private static final int LOGOUT_ID = Menu.FIRST + 1;
    private static final String SELECTED_BUTTON_COLOR = "#920027";
    private static final String DESELECTED_BUTTON_COLOR = "#000000";
    int ID = Database.getCurrentUserId();
    Database db = Database.getInstance(getActivity());

    private TextView profileUsername;
    private TextView profileLocation;
    private RatingBar profileRating;

    private LinearLayout filterLayout;
    private Button boughtButton;
    private Button soldButton;
    private Button sellingButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile, container, false);

        profileUsername = rootView.findViewById(R.id.usernameText);
        profileLocation = rootView.findViewById(R.id.locationText);
        profileRating   = rootView.findViewById(R.id.userRatingBar);
        filterLayout    = rootView.findViewById(R.id.profileLinLayout);
        boughtButton    = rootView.findViewById(R.id.boughtBtn);
        soldButton      = rootView.findViewById(R.id.soldBtn);
        sellingButton   = rootView.findViewById(R.id.sellingBtn);
        //profileRating.setEnabled(false); //not clickable
        //profileRating.setClickable(false);
        profileRating.setIsIndicator(true);
        populateProfile(ID);

        setupSellingButton();

        setupBoughtButton();

        setupSoldButton();

        Log.e("**********Profile", " OnCreateView");
        return rootView;
    }

    private void setupSoldButton() {
        soldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soldButton.setTextColor(Color.parseColor(SELECTED_BUTTON_COLOR));
                boughtButton.setTextColor(Color.parseColor(DESELECTED_BUTTON_COLOR));
                sellingButton.setTextColor(Color.parseColor(DESELECTED_BUTTON_COLOR));
            }
        });
    }

    private void setupBoughtButton() {
        boughtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soldButton.setTextColor(Color.parseColor(DESELECTED_BUTTON_COLOR));
                boughtButton.setTextColor(Color.parseColor(SELECTED_BUTTON_COLOR));
                sellingButton.setTextColor(Color.parseColor(DESELECTED_BUTTON_COLOR));
            }
        });
    }

    private void setupSellingButton() {
        sellingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soldButton.setTextColor(Color.parseColor(DESELECTED_BUTTON_COLOR));
                boughtButton.setTextColor(Color.parseColor(DESELECTED_BUTTON_COLOR));
                sellingButton.setTextColor(Color.parseColor(SELECTED_BUTTON_COLOR));
                populateLayoutWithSelling();
            }
        });
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
        //TODO: location
        profileRating.setRating(user.getRating());

    }

    private void populateLayoutWithSelling() {
        filterLayout.removeAllViews();

        for (final Product p : db.getAllProductsBySellerId(Database.getCurrentUserId())) {
            TextView itemName = new TextView(getActivity());
            TextView itemPrice = new TextView(getActivity());

            itemName.setText(p.getName());
            itemPrice.setText(String.valueOf(p.getPrice()));

            itemName.setTypeface(null, Typeface.BOLD);

            float textSize = 20;

            itemName.setTextSize(textSize);
            itemPrice.setTextSize(textSize);

            itemName.setPadding(10, 10, 10, 10);
            itemPrice.setPadding(10, 10, 10, 10);

            LinearLayout newLayout = new LinearLayout(getActivity());

            newLayout.addView(itemName);
            newLayout.addView(itemPrice);

            newLayout.setClickable(true);

            newLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                        showDetailsDialog(p);
                }
            });

            filterLayout.addView(newLayout);
        }
    }

    public void showDetailsDialog(final Product p) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View root = inflater.inflate(R.layout.details, null);

        TextView itemName = root.findViewById(R.id.itemTitleText);
        TextView itemPrice = root.findViewById(R.id.priceInput);
        TextView itemSeller = root.findViewById(R.id.sellerNameInput);
        TextView itemCategory = root.findViewById(R.id.categoryName);
        TextView itemDesc = root.findViewById(R.id.detailsInput);

        itemName.setText(p.getName());
        itemPrice.setText(String.valueOf(p.getPrice()));
        itemSeller.setText(db.getUserById(p.getSellerId()).getUsername());
        itemCategory.setText(db.getCategoryNameById(p.getCategoryId()));
        itemDesc.setText(p.getDescription());

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(root)
                .setPositiveButton("BUY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
//                        showBoughtItemDialog(p);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { }
                }).show();
    }
}
