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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class ProfileFragment extends Fragment {
    private static final int ADD_ITEM_ID  = Menu.FIRST;
    private static final int LOGOUT_ID = Menu.FIRST + 1;
    private static final String SELECTED_TEXT_COLOR = "#920027";
    private static final String DESELECTED_TEXT_COLOR = "#000000";
    int ID = Database.getCurrentUserId();
    Database db = Database.getInstance(getActivity());

    private TextView profileUsername;
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
        profileRating   = rootView.findViewById(R.id.userRatingBar);
        filterLayout    = rootView.findViewById(R.id.profileLinLayout);
        boughtButton    = rootView.findViewById(R.id.boughtBtn);
        soldButton      = rootView.findViewById(R.id.soldBtn);
        sellingButton   = rootView.findViewById(R.id.sellingBtn);

        profileRating.setIsIndicator(true);
        populateProfile(ID);

        setupSellingButton();

        setupBoughtButton();

        setupSoldButton();

        soldButton.setTextColor(Color.parseColor(SELECTED_TEXT_COLOR));
        populateLayoutWithSold();

        ScrollView scrollView = rootView.findViewById(R.id.scrollView2);
        scrollView.setBackgroundResource(R.drawable.border);

        return rootView;
    }

    private void setupSoldButton() {
        soldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soldButton.setTextColor(Color.parseColor(SELECTED_TEXT_COLOR));
                boughtButton.setTextColor(Color.parseColor(DESELECTED_TEXT_COLOR));
                sellingButton.setTextColor(Color.parseColor(DESELECTED_TEXT_COLOR));
                populateLayoutWithSold();
            }
        });
    }

    private void setupBoughtButton() {
        boughtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soldButton.setTextColor(Color.parseColor(DESELECTED_TEXT_COLOR));
                boughtButton.setTextColor(Color.parseColor(SELECTED_TEXT_COLOR));
                sellingButton.setTextColor(Color.parseColor(DESELECTED_TEXT_COLOR));
                populateLayoutWithBought();
            }
        });
    }

    private void setupSellingButton() {
        sellingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soldButton.setTextColor(Color.parseColor(DESELECTED_TEXT_COLOR));
                boughtButton.setTextColor(Color.parseColor(DESELECTED_TEXT_COLOR));
                sellingButton.setTextColor(Color.parseColor(SELECTED_TEXT_COLOR));
                populateLayoutWithSelling();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
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

    public void populateProfile(int id) {
        User user = db.getUserById(id);
        profileUsername.setText(user.getUsername());
        profileRating.setRating(user.getRating());
    }

    private void populateLayoutWithSelling() {
        filterLayout.removeAllViews();

        for (final Product p : db.getAllItemsUnsoldByCurrentUser()) {
            TextView itemName = new TextView(getActivity());
            TextView itemPrice = new TextView(getActivity());
            ImageView tag = new ImageView(getActivity());

            itemName.setText(p.getName());
            itemPrice.setText(String.format("$%s0", String.valueOf(p.getPrice())));
            tag.setBackgroundResource(R.drawable.ic_tag);

            itemName.setTypeface(null, Typeface.BOLD);

            float textSize = 20;

            itemName.setTextSize(textSize);
            itemPrice.setTextSize(textSize);

            itemName.setPadding(10, 30, 10, 30);
            itemPrice.setPadding(10, 30, 10, 30);
            tag.setPadding(10,30,10,30);

            LinearLayout newLayout = new LinearLayout(getActivity());
            LinearLayout padLayout = new LinearLayout(getActivity());
            padLayout.setPadding(10,10,10,10);

            newLayout.addView(tag);
            newLayout.addView(itemName);
            newLayout.addView(itemPrice);
            newLayout.setPadding(10, 10, 10, 10);

            newLayout.setClickable(true);

            newLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        showDetailsDialog(p);
                }
            });

            filterLayout.addView(newLayout);
            filterLayout.addView(padLayout);
        }
    }

    private void populateLayoutWithBought() {
        filterLayout.removeAllViews();

        for (final Product p : db.getAllItemsBoughtByCurrentUser()) {
            TextView itemName = new TextView(getActivity());
            TextView itemPrice = new TextView(getActivity());
            ImageView tag = new ImageView(getActivity());

            itemName.setText(p.getName());
            itemPrice.setText(String.format("$%s0", String.valueOf(p.getPrice())));
            tag.setBackgroundResource(R.drawable.ic_tag);

            itemName.setTypeface(null, Typeface.BOLD);

            float textSize = 20;

            itemName.setTextSize(textSize);
            itemPrice.setTextSize(textSize);

            itemName.setPadding(10, 30, 10, 30);
            itemPrice.setPadding(10, 30, 10, 30);
            tag.setPadding(10,30,10,30);

            LinearLayout newLayout = new LinearLayout(getActivity());
            LinearLayout padLayout = new LinearLayout(getActivity());

            newLayout.addView(tag);
            newLayout.addView(itemName);
            newLayout.addView(itemPrice);
            newLayout.setPadding(10, 10, 10, 10);

            newLayout.setClickable(true);

            newLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetailsDialog(p);
                }
            });

            filterLayout.addView(newLayout);
            filterLayout.addView(padLayout);
        }
    }

    private void populateLayoutWithSold() {
        filterLayout.removeAllViews();

        for (final Product p : db.getAllItemsSoldByCurrentUser()) {
            TextView itemName = new TextView(getActivity());
            TextView itemPrice = new TextView(getActivity());
            ImageView tag = new ImageView(getActivity());

            itemName.setText(p.getName());
            itemPrice.setText(String.format("$%s0", String.valueOf(p.getPrice())));
            tag.setBackgroundResource(R.drawable.ic_tag);

            itemName.setTypeface(null, Typeface.BOLD);

            float textSize = 20;

            itemName.setTextSize(textSize);
            itemPrice.setTextSize(textSize);

            itemName.setPadding(10, 30, 10, 30);
            itemPrice.setPadding(10, 30, 10, 30);
            tag.setPadding(10,30,10,30);

            LinearLayout newLayout = new LinearLayout(getActivity());
            LinearLayout padLayout = new LinearLayout(getActivity());

            newLayout.addView(tag);
            newLayout.addView(itemName);
            newLayout.addView(itemPrice);
            newLayout.setPadding(10, 10, 10, 10);

            newLayout.setClickable(true);

            newLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetailsDialog(p);
                }
            });

            filterLayout.addView(newLayout);
            filterLayout.addView(padLayout);
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
        itemPrice.setText(String.format("$%s0", String.valueOf(p.getPrice())));
        itemSeller.setText(db.getUserById(p.getSellerId()).getUsername());
        itemCategory.setText(db.getCategoryNameById(p.getCategoryId()));
        itemDesc.setText(p.getDescription());

        builder.setView(root)
                .setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { }
                }).show();
    }
}
