package com.tcc.lucca.scoutup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tcc.lucca.scoutup.R;

import java.util.List;

/**
 * Created by lucca on 02/10/17.
 */

public class ListItemMaterialAdapter extends ArrayAdapter<String> {


    private List<String> info;
    private LayoutInflater layoutInflate;


    public ListItemMaterialAdapter(Context ctx, List<String> info) {
        super(ctx, 0, info);
        this.info = info;
        this.layoutInflate = LayoutInflater.from(ctx);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final String info = getItem(i);
        if (view == null) {
            view = layoutInflate.inflate(R.layout.materiallistitem, viewGroup, false);
        }

        TextView txtItem = view.findViewById(R.id.textView6);
        txtItem.setText(info);



        return view;
    }


    public List<String> getInfo() {
        return info;
    }

    public void setInfo(List<String> info) {
        this.info = info;
    }
}
