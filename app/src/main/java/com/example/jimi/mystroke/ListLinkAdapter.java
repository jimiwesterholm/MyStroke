package com.example.jimi.mystroke;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jimi.mystroke.models.HelpPage;

import java.util.List;

/**
 * Created by jimi on 25/03/2018.
 */

public class ListLinkAdapter extends BaseAdapter{
    private List<HelpPage> links;
    private LayoutInflater inflater;

    static class ViewHolder {
        TextView elementTitleText;
    }

    public ListLinkAdapter(@NonNull Context context, @NonNull List<HelpPage> objects) {
        links = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return links.size();
    }

    @Override
    public HelpPage getItem(int position) {
        return links.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HelpPage page = links.get(position);
        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            int layoutResource = R.layout.list_element_link;
            convertView = inflater.inflate(layoutResource, null);
            viewHolder.elementTitleText = convertView.findViewById(R.id.elementTitleText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.elementTitleText.setText(page.getTitle());
        return convertView;
    }
}

