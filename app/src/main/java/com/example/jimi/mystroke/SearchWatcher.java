package com.example.jimi.mystroke;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;

/**
 * Created by jimi on 09/03/2018.
 */

public class SearchWatcher implements TextWatcher {
private ArrayAdapter adapter;
    private EditText search;

    public SearchWatcher(ArrayAdapter adapter, EditText search) {
        this.adapter = adapter;
        this.search = search;
    }

    @Override
    public void afterTextChanged(Editable arg0) {
        adapter.getFilter().filter(search.getText().toString());
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1,
        int arg2, int arg3) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2,
        int arg3) {
        // TODO Auto-generated method stub
    }
}