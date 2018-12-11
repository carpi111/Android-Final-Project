package com.chapman.dev.vincecarpino.final_project;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// TODO: Long press to delete

public class NotificationsFragment extends Fragment {
    private ArrayList<String> allNotifs = new ArrayList<>();
    private LinearLayout scrollviewLayout;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notifications, container, false);

        scrollviewLayout = rootView.findViewById(R.id.notifLinLayout);

        populateAllNotifsList();

        populateScrollview();

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void populateAllNotifsList() {
        allNotifs.addAll(Arrays.asList(itemSoldNotifs));
        allNotifs.addAll(Arrays.asList(newRatingNotifs));
        allNotifs.addAll(Arrays.asList(newItemNotifs));

        Collections.shuffle(allNotifs);
    }

    private void populateScrollview() {
        for (final String s : allNotifs) {
            ImageView notifIcon = new ImageView(getActivity());
            TextView notifTitle = new TextView(getActivity());

            // if in itemsold, sold icon
            // if in new rating, rating icon
            // if in newitem, new listing icon
            if (Arrays.asList(itemSoldNotifs).contains(s)) {
                notifIcon.setImageResource(R.drawable.ic_sold);
            } else if (Arrays.asList(newRatingNotifs).contains(s)) {
                notifIcon.setImageResource(R.drawable.ic_new_rating);
            } else if (Arrays.asList(newItemNotifs).contains(s)) {
                notifIcon.setImageResource(R.drawable.ic_new_listing);
            }
            notifTitle.setText(s);

            notifTitle.setTypeface(null, Typeface.BOLD);

            float textSize = 20;

            notifTitle.setTextSize(textSize);

            notifIcon.setPadding(10, 30, 10, 30);
            notifTitle.setPadding(10, 30, 10, 30);

            LinearLayout newLayout = new LinearLayout(getActivity());

            newLayout.addView(notifIcon);
            newLayout.addView(notifTitle);

            newLayout.setBackgroundResource(R.drawable.border);
            newLayout.setPadding(10, 10, 10, 10);

            newLayout.setClickable(true);

            scrollviewLayout.addView(newLayout);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "DELETE");
        super.onCreateContextMenu(menu, v, menuInfo);
    }
}
