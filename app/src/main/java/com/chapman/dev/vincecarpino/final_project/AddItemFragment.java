package com.chapman.dev.vincecarpino.final_project;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddItemFragment extends Fragment {
    private EditText    itemNameInput;
    private EditText    itemPriceInput;
    private Spinner     categorySpinner;
    private EditText    itemDescInput;
    private ImageButton itemSaveButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView   = inflater.inflate(R.layout.new_item, container, false);

        itemNameInput   = rootView.findViewById(R.id.newItemNameInput);
        itemPriceInput  = rootView.findViewById(R.id.newItemPriceInput);
        categorySpinner = rootView.findViewById(R.id.newItemCategorySpinner);
        itemDescInput   = rootView.findViewById(R.id.newItemDescriptionInput);
        itemSaveButton  = rootView.findViewById(R.id.newItemSaveButton);

        setUpCategorySpinner();
        setUpSaveButton();

        itemSaveButton.setEnabled(false);

        itemNameInput.addTextChangedListener(watcher);
        itemPriceInput.addTextChangedListener(watcher);
        itemDescInput.addTextChangedListener(watcher);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setUpCategorySpinner() {
        ArrayAdapter<String> adapter;

        String[] allCategories = new String[] {
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

        ArrayList<String> categories = new ArrayList<>(Arrays.asList(allCategories));
        List<String> list = new ArrayList<>(categories);

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);
    }

    private void setUpSaveButton() {
        itemSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItemName = getEditTextString(itemNameInput);
                String newItemPrice = getEditTextString(itemPriceInput);
                String newItemCat = categorySpinner.getSelectedItem().toString();
                String newItemDesc = getEditTextString(itemDescInput);



                Log.e("NEW ITEM", getEditTextString(itemNameInput));
                Log.e("NEW ITEM", getEditTextString(itemPriceInput));
                Log.e("NEW ITEM", categorySpinner.getSelectedItem().toString());
                Log.e("NEW ITEM", getEditTextString(itemDescInput));
            }
        });
    }

    private boolean anyInputsAreEmpty() {
        return getEditTextString(itemNameInput).equals("")
                || getEditTextString(itemPriceInput).equals("")
                || getEditTextString(itemDescInput).equals("");
    }

    private String getEditTextString(EditText e) {
        return e.getText().toString();
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void afterTextChanged(Editable s) {
            if (anyInputsAreEmpty()) {
                itemSaveButton.setEnabled(false);
            } else {
                itemSaveButton.setEnabled(true);
            }
        }
    };
}
