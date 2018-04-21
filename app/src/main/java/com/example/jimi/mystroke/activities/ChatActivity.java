package com.example.jimi.mystroke.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.CommentAdapter;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Comment;
import com.example.jimi.mystroke.models.Patient;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetCommentsTask;
import com.example.jimi.mystroke.tasks.GetPatientByUserIdTask;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabaseTask;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements AsyncResponse {
    private EditText messageText;
    private Button messageButton;
    private CommentAdapter adapter;
    private List<Comment> messages;
    private String pId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if (Globals.getInstance().isLoggedAsPatient() == 1) {
            Patient patient = Globals.getInstance().getPatientOb();
            pId = patient.getId();
            if(patient != null) {
                new GetCommentsTask(AppDatabase.getDatabase(getApplicationContext()), patient.getId(), this).execute();
            } else {
                new GetPatientByUserIdTask(this, Globals.getInstance().getUser().getId(), getApplicationContext()).execute();
            }
        } else {
            pId = getIntent().getStringExtra("EXTRA_PATIENT_ID");
            if(pId != null) new GetCommentsTask(AppDatabase.getDatabase(getApplicationContext()), pId, this).execute();
        }
/*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
        TextView title = findViewById(R.id.titleText);
        title.setText(R.string.title_activity_chat);

        messageButton = findViewById(R.id.sendMessageButton);
        messageText = findViewById(R.id.messageText);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(messageText.getText().toString().length() != 0) {
                    addComment();
                } else {
                    //TODO error message
                }
            }
        });
        messages = new ArrayList<Comment>(0);
        itemsToListView(messages, mMessageClickedHandler);
    }

    @Override
    protected void onResume() {
        if (Globals.getInstance().isLoggedAsPatient() == 1) {
            Patient patient = Globals.getInstance().getPatientOb();
            if(patient != null) {
                new GetCommentsTask(AppDatabase.getDatabase(getApplicationContext()), patient.getId(), this).execute();
            } else {
                new GetPatientByUserIdTask(this, Globals.getInstance().getUser().getId(), getApplicationContext()).execute();
            }
        } else {
            pId = getIntent().getStringExtra("EXTRA_PATIENT_ID");
            if(pId != null) new GetCommentsTask(AppDatabase.getDatabase(getApplicationContext()), pId, this).execute();
        }
        super.onResume();
    }

    private void addComment() {
        //TODO get actual values for patient id etc
        Comment comment = new Comment(new Date(System.currentTimeMillis()), new Time(30), messageText.getText().toString(), pId, null, Globals.getInstance().isLoggedAsPatient());
        new RecordsToAppDatabaseTask("comment", AppDatabase.getDatabase(getApplicationContext())).execute(comment);
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
        adapter = new CommentAdapter(this, messages, Globals.getInstance().isLoggedAsPatient());
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
            case GetPatientByUserIdTask.var:
                Patient patient = (Patient) objects[0];
                Globals.getInstance().setPatientOb(patient);
                new GetCommentsTask(AppDatabase.getDatabase(getApplicationContext()), patient.getId(), this).execute();
                break;
        }
    }

}