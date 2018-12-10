package com.chapman.dev.vincecarpino.final_project;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FeedFragment extends Fragment {
    private Database db = Database.getInstance(getActivity());
    private LinearLayout scrollviewLayout;

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
        for (Product p : db.getAllProducts()) {
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
                    // TODO: show item details
                }
            });

            scrollviewLayout.addView(newLayout);
        }
    }
}
