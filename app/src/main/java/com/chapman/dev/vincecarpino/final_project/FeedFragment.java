package com.chapman.dev.vincecarpino.final_project;

import android.app.AlertDialog;
import android.app.Fragment;
import android.graphics.Typeface;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

// TODO: Remove items from list when sold

public class FeedFragment extends Fragment {
    private Database db = Database.getInstance(getActivity());
    private LinearLayout scrollviewLayout;

    //Details Dialog
    private TextView itemName;
    private TextView itemPrice;
    private TextView itemSeller;
    private TextView itemCategory;
    private TextView itemDesc;

    //BoughtItem Dialog
    private TextView rateSellerQuestion;
    private RatingBar sellerRating;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feed, container, false);

        scrollviewLayout = rootView.findViewById(R.id.feedLinLayout);
        populateItemsScrollview();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void populateItemsScrollview() {
        for (final Product p : db.getAllProducts()) {
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
                    showDetailsDialog(p);
                }
            });

            scrollviewLayout.addView(newLayout);
        }
    }

    public void showDetailsDialog(final Product p)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View root = inflater.inflate(R.layout.details, null);

        itemName = root.findViewById(R.id.itemTitleText);
        itemPrice = root.findViewById(R.id.priceInput);
        itemSeller = root.findViewById(R.id.sellerNameInput);
        itemCategory = root.findViewById(R.id.categoryName);
        itemDesc = root.findViewById(R.id.detailsInput);

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
                        showBoughtItemDialog(p);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { }
                }).show();
    }

    public void showBoughtItemDialog(Product p)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View root = inflater.inflate(R.layout.buy_item_dialog, null);

        rateSellerQuestion = root.findViewById(R.id.rateSellerTxt);
        sellerRating = root.findViewById(R.id.buyRatingBar);



        String sellerQ = "How would you rate " + db.getUserById(p.getSellerId()).getUsername().toUpperCase() + "?";
        rateSellerQuestion.setText(sellerQ);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(root)
                .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        db.updateUserRating(Database.getCurrentUserId(), sellerRating.getRating());
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { }
                }).show();
    }
}
