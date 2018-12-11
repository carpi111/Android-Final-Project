package com.chapman.dev.vincecarpino.final_project;

import android.app.Fragment;
import android.graphics.Typeface;
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

// TODO: Populate with fake notifications

// TODO: Create notification when item is bought

// TODO: Long press to delete

// your item sold
// new item listed
// new rating

public class NotificationsFragment extends Fragment {
    private String[] itemSoldNotifs = new String[] {
            "Your book was sold!",
            "Your shirt was sold!",
            "Your camera was sold!",
            "Your old tv was sold!",
            "Your laptop was sold!"
    };
    private String[] newItemNotifs = new String[] {
            "A new computer has been listed!",
            "A new camera bag has been listed!",
            "A new laptop case has been listed!",
            "A new sweater has been listed!",
            "A new textbook has been listed!"
    };
    private String[] newRatingNotifs = new String[] {
            "You have a new 3 star rating",
            "You have a new 5 star rating",
            "You have a new 2 star rating",
            "You have a new 1 star rating",
            "You have a new 4 star rating",
    };

    private LinearLayout scrollviewLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notifications, container, false);

        scrollviewLayout = rootView.findViewById(R.id.notifLinLayout);

        populateScrollview();

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void populateScrollview() {
        for (final String s : itemSoldNotifs) {
            ImageView notifIcon = new ImageView(getActivity());
            TextView notifTitle = new TextView(getActivity());

            notifIcon.setImageResource(R.drawable.ic_sold);
            notifTitle.setText(s);

            notifTitle.setTypeface(null, Typeface.BOLD);

            float textSize = 20;

            notifTitle.setTextSize(textSize);

            notifIcon.setPadding(10, 30, 10, 30);
            notifTitle.setPadding(10, 30, 10, 30);

            LinearLayout newLayout = new LinearLayout(getActivity());

            newLayout.addView(notifIcon);
            newLayout.addView(notifTitle);

            scrollviewLayout.addView(newLayout);
        }

        for (final String s : newRatingNotifs) {
            ImageView notifIcon = new ImageView(getActivity());
            TextView notifTitle = new TextView(getActivity());

            notifIcon.setImageResource(R.drawable.ic_new_rating);
            notifTitle.setText(s);

            notifTitle.setTypeface(null, Typeface.BOLD);

            float textSize = 20;

            notifTitle.setTextSize(textSize);

            notifIcon.setPadding(10, 30, 10, 30);
            notifTitle.setPadding(10, 30, 10, 30);

            LinearLayout newLayout = new LinearLayout(getActivity());

            newLayout.addView(notifIcon);
            newLayout.addView(notifTitle);

            scrollviewLayout.addView(newLayout);
        }

        for (final String s : newItemNotifs) {
            ImageView notifIcon = new ImageView(getActivity());
            TextView notifTitle = new TextView(getActivity());

            notifIcon.setImageResource(R.drawable.ic_new_listing);
            notifTitle.setText(s);

            notifTitle.setTypeface(null, Typeface.BOLD);

            float textSize = 20;

            notifTitle.setTextSize(textSize);

            notifIcon.setPadding(10, 30, 10, 30);
            notifTitle.setPadding(10, 30, 10, 30);

            LinearLayout newLayout = new LinearLayout(getActivity());

            newLayout.addView(notifIcon);
            newLayout.addView(notifTitle);

            scrollviewLayout.addView(newLayout);
        }
    }
}
