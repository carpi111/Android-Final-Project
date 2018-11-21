package com.chapman.dev.vincecarpino.final_project;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.style.TtsSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddItemFragment extends Fragment {
    private Spinner categorySpinner;
    private EditText dollarInput;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_item, container, false);
        categorySpinner = rootView.findViewById(R.id.newItemCategorySpinner);
        dollarInput = rootView.findViewById(R.id.dollarInput);
        //dollarInput.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(3,2)});
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

//    public class DecimalDigitsInputFilter implements InputFilter {
//
//        Pattern mPattern;
//
//        public DecimalDigitsInputFilter(int digitsBeforeZero,int digitsAfterZero) {
//            mPattern=Pattern.compile("[0-9]{0," + (digitsBeforeZero-1) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
//        }
//
//        @Override
//        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//
//            Matcher matcher=mPattern.matcher(dest);
//            if(!matcher.matches())
//                return "";
//            return null;
//        }
//
//    }

}
