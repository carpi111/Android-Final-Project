package com.chapman.dev.vincecarpino.final_project;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: Make this do stuff

public class SearchFragment extends Fragment {
    private Spinner categorySpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search, container, false);
        categorySpinner = rootView.findViewById(R.id.categorySpinner);

        setUpCategorySpinner();

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setUpCategorySpinner()
    {
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
}
