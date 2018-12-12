package com.chapman.dev.vincecarpino.final_project;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

            if (arrayContainsItem(itemSoldNotifs, s)) {
                notifIcon.setImageResource(R.drawable.ic_sold);
            } else if (arrayContainsItem(newRatingNotifs, s)) {
                notifIcon.setImageResource(R.drawable.ic_new_rating);
            } else if (arrayContainsItem(newItemNotifs, s)) {
                notifIcon.setImageResource(R.drawable.ic_new_listing);
            }
            notifTitle.setText(s);

            notifTitle.setTypeface(null, Typeface.BOLD);

            float textSize = 20;

            notifTitle.setTextSize(textSize);

            notifIcon.setPadding(10, 30, 10, 30);
            notifTitle.setPadding(10, 30, 10, 30);

            LinearLayout newLayout = new LinearLayout(getActivity());
            LinearLayout padLayout = new LinearLayout(getActivity());

            newLayout.addView(notifIcon);
            newLayout.addView(notifTitle);

            newLayout.setBackgroundResource(R.drawable.border);
            newLayout.setPadding(10, 10, 10, 10);
            padLayout.setPadding(10,10,10,10);

            newLayout.setClickable(true);
            addLongClickListenerToLayout(newLayout);

//            registerForContextMenu(newLayout);

            scrollviewLayout.addView(newLayout);
            scrollviewLayout.addView(padLayout);
        }
    }

    private boolean arrayContainsItem(String[] array, String item) {
        return Arrays.asList(array).contains(item);
    }

    private void addLongClickListenerToLayout(final LinearLayout layout) {
        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                layoutToRemove = layout;
                showDeleteNotificationDialog(layout);
                return true;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "DELETE");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
//        showDeleteNotificationDialog();
        return super.onContextItemSelected(item);
    }

    private void showDeleteNotificationDialog(final LinearLayout layout) {
        new AlertDialog.Builder(getActivity())
                .setMessage("Do you want to clear this notification?")
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                })
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (scrollviewLayout.getChildCount() > 0) {
                            int indexOfViewToRemove = scrollviewLayout.indexOfChild(layout);
                            scrollviewLayout.removeView(layout);
                            allNotifs.remove(indexOfViewToRemove);
                        }
                    }
                }).show();
    }
}
