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
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Comment;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetCommentsTask;
import com.example.jimi.mystroke.tasks.GetMinCommentIdTask;
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
    private int pId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if (Globals.getInstance().isPatient() == 1) {
            new GetCommentsTask(AppDatabase.getDatabase(getApplicationContext()), Globals.getInstance().getUser().getPatient(), this).execute();
        } else {
            pId = getIntent().getIntExtra("EXTRA_PATIENT_ID", -1);
            new GetCommentsTask(AppDatabase.getDatabase(getApplicationContext()), pId, this).execute();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView title = findViewById(R.id.titleText);
        title.setText(R.string.title_activity_chat);

        messageButton = findViewById(R.id.sendMessageButton);
        messageText = findViewById(R.id.messageText);
    }

    public void sendButtonOnClick(View view) {
        if(messageText.getText() != null) {
            new GetMinCommentIdTask(AppDatabase.getDatabase(getApplicationContext()), this).execute();
        }
    }

    private void addComment(int id) {
        //TODO get actual values for patient id etc
        Comment comment = new Comment(new Date(System.currentTimeMillis()), new Time(30), messageText.getText().toString(), Globals.getInstance().getUser().getPatient(), 1, Globals.getInstance().isPatient());
        new RecordsToAppDatabase("comment", AppDatabase.getDatabase(getApplicationContext())).execute(new Comment[]{comment});
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
        adapter = new CommentAdapter(this, messages, Globals.getInstance().isPatient());
        ListView listView = findViewById(R.id.messages);
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
            case GetMinCommentIdTask.var:
                int id = (Integer) objects[0];
                if(id > 0) id = 0;
                id--;
                addComment(id);
                break;
        }
    }

}