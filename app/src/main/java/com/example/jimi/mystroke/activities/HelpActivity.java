package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.ListLinkAdapter;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.HelpPage;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.GetHelpPagesByIds;
import com.example.jimi.mystroke.tasks.GetHelpPagesByParentIds;

import java.util.List;

public class HelpActivity extends AppCompatActivity implements AsyncResponse, AdapterView.OnItemClickListener {
    //private Toolbar toolbar;
    private HelpPage page;
    private List<HelpPage> children;
    private ListLinkAdapter<HelpPage> adapter;
    private View content;
    private TextView contentText;
    private ListView links;
    private TextView label;
    private ImageView border;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);/*
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        String hID = getIntent().getStringExtra("EXTRA_HELP_ID");
        if(hID == null) {
            //TODO do this in a neater way?
            hID = "02fd41dc-2fc8-11e8-b467-0ed5f89f718b";
        }
        new GetHelpPagesByIds(AppDatabase.getDatabase(getApplicationContext()), this).execute(hID);
        new GetHelpPagesByParentIds(AppDatabase.getDatabase(getApplicationContext()), this).execute(hID);

        View view = findViewById(R.id.contentHelp);
        label = view.findViewById(R.id.labelTextView);
        links = view.findViewById(R.id.linkListView);
        content = view.findViewById(R.id.helpTextContent);
        border = view.findViewById(R.id.linkListBorder);
        contentText = content.findViewById(R.id.helpContentTextView);
    }

    private void itemsToListView() {
        adapter = new ListLinkAdapter<HelpPage>(this, children);
        links.setAdapter(adapter);
        links.setOnItemClickListener(this);
        adapter.notifyDataSetChanged();
    }

    public void onItemClick(AdapterView parent, View v, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), HelpActivity.class);
        intent.putExtra("EXTRA_HELP_ID", children.get(position).getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }

    @Override
    public void respond(int var, Object... objects) {
        switch (var) {
            case GetHelpPagesByIds.var:
                List<HelpPage> helpPages = (List<HelpPage>) objects[0];
                page = helpPages.get(0);
                label.setText(page.getTitle());
                if(page.getBody() == null) {
                    content.setVisibility(View.GONE);
                    border.setVisibility(View.GONE);
                } else {
                    content.setVisibility(View.VISIBLE);
                    contentText.setText(page.getBody());
                }
                break;
            case GetHelpPagesByParentIds.var:
                children = (List<HelpPage>) objects[0];
                if(children.size() != 0) {
                    itemsToListView();
                    links.setVisibility(View.VISIBLE);
                } else {
                    border.setVisibility(View.GONE);
                    links.setVisibility(View.GONE);
                }
                break;
        }
    }
}
