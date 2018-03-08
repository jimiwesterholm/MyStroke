package com.example.jimi.mystroke;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jimi.mystroke.models.Comment;

import java.util.Calendar;
import java.util.List;

/**
 * Created by jimi on 18/02/2018.
 */

public class CommentAdapter extends BaseAdapter {
    private List<Comment> comments;
    //TODO: therapist side displaying correctly
    private int[] layoutResources = {R.layout.list_element_view_received_message, R.layout.list_element_view_sent_message};
    private LayoutInflater inflater;
    private int isPatient;

    static class ViewHolder {
        public TextView textView;
        public TextView timeView;
    }

    public CommentAdapter(@NonNull Context context, @NonNull List<Comment> objects, int isPatient) {
        //super(context, resource, objects);
        comments = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.isPatient = isPatient;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Comment getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment = comments.get(position);
        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            if(isPatient == 1) {
                convertView = inflater.inflate(layoutResources[comment.getSentByPatient()], null);
            } else {
                if(comment.getSentByPatient() == 1) {
                    convertView = inflater.inflate(layoutResources[0], null);
                } else {
                    convertView = inflater.inflate(layoutResources[1], null);
                }
            }
            viewHolder.textView = convertView.findViewById(R.id.textView);
            viewHolder.timeView = convertView.findViewById(R.id.timeView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(comment.getText());
        //timeView.setText(comment.getDate().toString() + ": " + comment.getTime().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(comment.getTimestamp());
        //TODO: Use resource string with placeholders instead?
        viewHolder.timeView.setText(calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR) + "\t" + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE));

        return convertView;
    }
}
