package com.chapman.dev.vincecarpino.final_project;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SearchFragment extends Fragment {
    private Spinner categorySpinner;
    private EditText searchInput;
    private LinearLayout searchLinLay;
    private ImageButton searchBtn;
    ArrayList<Product> resultList = new ArrayList<>();
    ArrayList<Product> tempList   = new ArrayList<>();
    ArrayList<Product> realList   = new ArrayList<>();

    private TextView itemName;
    private TextView itemPrice;
    private TextView itemSeller;
    private TextView itemCategory;
    private TextView itemDesc;

    private TextView rateSellerQuestion;
    private RatingBar sellerRating;

    private Database db = Database.getInstance(getActivity());
    String categorySelection;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search, container, false);
        categorySpinner = rootView.findViewById(R.id.categorySpinner);
        searchInput = rootView.findViewById(R.id.searchInput);
        searchLinLay = rootView.findViewById(R.id.searchLinLayout);
        searchBtn = rootView.findViewById(R.id.searchBtn);

        setUpCategorySpinner();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realList.clear();
                searchLinLay.removeAllViews();
                realList = getResultList();
                populateLinLay(realList);
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setUpCategorySpinner() {
        List<String> list = new ArrayList<>();
        ArrayList<String> categories = new ArrayList<>();
        ArrayAdapter<String> adapter;

        String[] allCategories = new String[]{
                "All Categories",
                "Art",
                "Books",
                "Clothing",
                "Crafts",
                "Electronics",
                "Everything else",
                "Furniture",
                "Health & Beauty",
                "Jewelry",
                "Musical Instruments",
                "Real Estate",
                "Sporting Goods"
        };

        categories.addAll(Arrays.asList(allCategories));
        list.addAll(categories);

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);
    }

    public ArrayList<Product> getResultList() {
        int catID;
        String catName;
        String prodName;
        String search;
        resultList = db.getAllUnsoldProducts();

        for (int i = 0; i < resultList.size(); ++i) {
            catID = resultList.get(i).getCategoryId();
            catName = db.getCategoryNameById(catID);
            prodName = resultList.get(i).getName().toLowerCase();
            search = searchInput.getText().toString().toLowerCase();
            categorySelection = categorySpinner.getSelectedItem().toString();

            if(categorySelection.equals("All Categories")) {
                if(prodName.contains(search)) {
                    tempList.add(resultList.get(i));
                }
            } else if(catName.equals(categorySelection) && prodName.contains(search)) {
                    tempList.add(resultList.get(i));
            } else {
                Log.e("************SEARCH", "nothing printed");
            }
        }

        return tempList;
    }

    private void populateLinLay(ArrayList<Product> productList) {
        for (final Product product : productList) {
            TextView itemName = new TextView(getActivity());
            TextView itemPrice = new TextView(getActivity());
            ImageView tag = new ImageView(getActivity());

            itemName.setText(product.getName());
            itemPrice.setText(String.format("$%s0", String.valueOf(product.getPrice())));
            tag.setImageResource(R.drawable.ic_tag);

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

            newLayout.setBackgroundResource(R.drawable.border);
            newLayout.setPadding(10, 10, 10, 10);
            newLayout.setClickable(true);

            newLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDetailsDialog(product);
                }
            });

            searchLinLay.addView(newLayout);
            searchLinLay.addView(padLayout);
        }
    }

    public void showDetailsDialog(final Product p) {
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

    public void showBoughtItemDialog(Product p) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View root = inflater.inflate(R.layout.buy_item_dialog, null);

        rateSellerQuestion = root.findViewById(R.id.rateSellerTxt);
        sellerRating = root.findViewById(R.id.buyRatingBar);

        String sellerQ = "How would you rate " + db.getUserById(p.getSellerId()).getUsername().toUpperCase() + "?";
        rateSellerQuestion.setText(sellerQ);

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
