package com.example.jimi.mystroke.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.CommentAdapter;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Comment;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetCommentsTask;
import com.example.jimi.mystroke.tasks.GetMaxCommentIdTask;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabase;

import java.lang.reflect.Array;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements AsyncResponse {
    private EditText messageText;
    private Button messageButton;
    private CommentAdapter adapter;
    private List<Comment> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new GetCommentsTask(AppDatabase.getDatabase(getApplicationContext()), this).execute();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView title = (TextView) findViewById(R.id.titleText);
        title.setText(R.string.title_activity_chat);

        messageButton = (Button) findViewById(R.id.sendMessageButton);
        messageText = (EditText) findViewById(R.id.messageText);
    }

    public void sendButtonOnClick(View view) {
        if(!messageText.getText().toString().equals(null)) {
            new GetMaxCommentIdTask(AppDatabase.getDatabase(getApplicationContext()), this).execute();
        }
    }

    public void addComment(int id) {
        Comment comment = new Comment(id+1, new Date(System.currentTimeMillis()), new Time(30), messageText.getText().toString(), 1, 1, 1);
        new RecordsToAppDatabase("comment", getApplicationContext()).execute(new Comment[]{comment});
        messages.add(comment);
        messageText.getText().clear();
        adapter.notifyDataSetChanged();
    }

    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            //Something here maybe?
        }
    };

    private void itemsToListView(List<Comment> items, AdapterView.OnItemClickListener listener) {
        messages = items;
        adapter = new CommentAdapter(this, messages);
        ListView listView = (ListView) findViewById(R.id.messages);
        listView.setAdapter(adapter);
        //listView.setOnItemClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }

    @Override
    public void respond(int var, Object... objects) {
        switch (var) {
            case GetCommentsTask.var:
                itemsToListView((List<Comment>) objects[0], mMessageClickedHandler);
                break;
            case GetMaxCommentIdTask.var:
                addComment((Integer) objects[0]);
                break;
        }
    }

}