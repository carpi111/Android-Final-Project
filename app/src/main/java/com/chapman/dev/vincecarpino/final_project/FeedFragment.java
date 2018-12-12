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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        for (final Product p : db.getAllUnsoldProducts()) {
            TextView itemName = new TextView(getActivity());
            TextView itemPrice = new TextView(getActivity());
            ImageView tag = new ImageView(getActivity());

            itemName.setText(p.getName());
            itemPrice.setText(String.format("$%s0", String.valueOf(p.getPrice())));
            tag.setImageResource(R.drawable.tag);

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
            newLayout.setBackgroundResource(R.drawable.border);
            newLayout.setClickable(true);

            newLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetailsDialog(p);
                }
            });

            scrollviewLayout.addView(newLayout);
            scrollviewLayout.addView(padLayout);
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
        itemPrice.setText(String.format("$%s0", String.valueOf(p.getPrice())));
        itemSeller.setText(db.getUserById(p.getSellerId()).getUsername());
        itemCategory.setText(db.getCategoryNameById(p.getCategoryId()));
        itemDesc.setText(p.getDescription());

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(root)
                .setPositiveButton("BUY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        scrollviewLayout.removeAllViews();
                        db.updateBuyerIdOfProductByProductId(p.getId());
                        populateItemsScrollview();
                        showBoughtItemDialog(p);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { }
                }).show();
    }

    public void showBoughtItemDialog(final Product p)
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
                        db.updateUserRating(p.getSellerId(), sellerRating.getRating());
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { }
                }).show();
    }

}
