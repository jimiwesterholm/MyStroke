package com.example.jimi.mystroke.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.Globals;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.daos.UserDao;
import com.example.jimi.mystroke.models.User;
import com.example.jimi.mystroke.tasks.RecordsToAppDatabase;

public class ProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText[] editFields = new EditText[4];
    private Button editButton;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editFields[0] = findViewById(R.id.textUserEdit);
        editFields[0].setText(Globals.getInstance().getUser().getUsername());
        editFields[1] = findViewById(R.id.textFirstNameEdit);
        editFields[1].setText(Globals.getInstance().getUser().getFirstName());
        editFields[2] = findViewById(R.id.textSecondNameEdit);
        editFields[2].setText(Globals.getInstance().getUser().getLastName());
        editFields[3] = findViewById(R.id.textEmailEdit);
        editFields[3].setText(Globals.getInstance().getUser().getEmail());

        editButton = (Button) findViewById(R.id.buttonEditOrCancel);
        confirmButton = (Button) findViewById(R.id.buttonConfirmChanges);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_def, menu);
        return true;
    }

    public void editButtonOnClick(View view) {
        editFields[0].setInputType(InputType.TYPE_CLASS_TEXT);
        editFields[1].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        editFields[2].setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        editFields[3].setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE);

        for (EditText editText : editFields) editText.setEnabled(true);

        editButton.setText(R.string.cancel);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelButtonOnClick(v);
            }
        });
        confirmButton.setVisibility(View.VISIBLE);
    }

    public void cancelButtonOnClick(View view) {
        editFields[0].setInputType(InputType.TYPE_NULL);
        editFields[1].setInputType(InputType.TYPE_NULL);
        editFields[2].setInputType(InputType.TYPE_NULL);
        editFields[3].setInputType(InputType.TYPE_NULL);

        for (EditText editText : editFields) editText.setEnabled(false);

        editButton.setText(R.string.edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editButtonOnClick(v);
            }
        });
        confirmButton.setVisibility(View.GONE);

        //Reset input
        editFields[0].setText(Globals.getInstance().getUser().getUsername());
        editFields[1].setText(Globals.getInstance().getUser().getFirstName());
        editFields[2].setText(Globals.getInstance().getUser().getLastName());
        editFields[3].setText(Globals.getInstance().getUser().getEmail());
    }

    public void confirmButtonOnClick(View view) {
        User user = Globals.getInstance().getUser();
        user.setUsername(editFields[0].getText().toString());
        user.setFirstName(editFields[1].getText().toString());
        user.setLastName(editFields[2].getText().toString());
        user.setEmail(editFields[3].getText().toString());

        RecordsToAppDatabase recordsToAppDatabase = new RecordsToAppDatabase();
        recordsToAppDatabase.setClassName("user");
        recordsToAppDatabase.setContext(getApplicationContext());
        recordsToAppDatabase.execute(user);

        cancelButtonOnClick(editButton);
    }
}
