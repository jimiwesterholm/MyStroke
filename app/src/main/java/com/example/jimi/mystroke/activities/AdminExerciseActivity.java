package com.example.jimi.mystroke.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jimi.mystroke.AppDatabase;
import com.example.jimi.mystroke.SearchWatcher;
import com.example.jimi.mystroke.R;
import com.example.jimi.mystroke.models.Exercise;
import com.example.jimi.mystroke.models.Imagery;
import com.example.jimi.mystroke.tasks.AsyncResponse;
import com.example.jimi.mystroke.tasks.DeleteExercisesTask;
import com.example.jimi.mystroke.tasks.DeleteImageriesTask;
import com.example.jimi.mystroke.tasks.GetExercisesTask;
import com.example.jimi.mystroke.tasks.GetImageriesTask;

import java.util.List;

public class AdminExerciseActivity extends AppCompatActivity implements AsyncResponse {
    private ListView list;
    private EditText search;
    private TextView title;
    private TextView label;
    private boolean imageryOn;
    private Exercise exercise;
    private Imagery imagery;
    private List<Imagery> imageries;
    private List<Exercise> exercises;
    private Button switchButton;
    private ArrayAdapter<Exercise> exerciseArrayAdapter;
    private ArrayAdapter<Imagery> imageryArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_exercise);
        new GetExercisesTask(getApplicationContext(), this).execute();
        new GetImageriesTask(getApplicationContext(), this).execute();
        search = findViewById(R.id.searchEditText);
        title = findViewById(R.id.titleText);
        label = findViewById(R.id.labelTextView);
        label.setText(R.string.exercise_list_label);
        title.setText(R.string.exercise_list);
        imageryOn = false;
    }

    private void itemsToListView(ArrayAdapter adapter, AdapterView.OnItemClickListener listener) {
        list.setAdapter(adapter);
        list.setOnItemClickListener(listener);
        search.addTextChangedListener(new SearchWatcher(adapter, search));
    }

    private AdapterView.OnItemClickListener exerciseClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            exercise = (Exercise) parent.getAdapter().getItem(position);
            //Change colour etc.
        }
    };

    private AdapterView.OnItemClickListener imageryClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            imagery = (Imagery) parent.getAdapter().getItem(position);
            //Change colour etc.
        }
    };

    public void onChangeListButtonClick(View view) {
        Button button = (Button) view;
        if(!imageryOn) {
            title.setText(R.string.imagery_list);
            itemsToListView(new ArrayAdapter<Imagery>(this, R.layout.support_simple_spinner_dropdown_item, imageries.toArray(new Imagery[0])), imageryClickedHandler);
            button.setText(R.string.exerciseBut);
            label.setText(R.string.imageryBut);
            imageryOn = true;
        } else {
            title.setText(R.string.exercise_list);
            itemsToListView(new ArrayAdapter<Exercise>(this, R.layout.support_simple_spinner_dropdown_item, exercises.toArray(new Exercise[0])), exerciseClickedHandler);
            button.setText(R.string.imageryBut);
            label.setText(R.string.exerciseBut);
            imageryOn = false;
        }
    }

    public void onAddListItemButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), AddExerciseActivity.class);
        startActivity(intent);
    }

    public void onDeleteListItemButtonClick(View view) {
        if(imageryOn) {
            if(imagery == null) {
                //TODO error message, delete from listview
                return;
            }
            new DeleteImageriesTask(this, AppDatabase.getDatabase(getApplicationContext())).execute(imagery);
            imageryArrayAdapter.remove(imagery);
            imagery = null;
        } else {
            if(exercise == null) {
                //TODO error message
                return;
            }
            new DeleteExercisesTask(this, AppDatabase.getDatabase(getApplicationContext())).execute(exercise);
            exerciseArrayAdapter.remove(exercise);
            exercise = null;
        }
    }

    @Override
    public void respond(int var, Object... objects) {
        switch (var) {
            case GetImageriesTask.var:
                imageries = (List<Imagery>) objects[0];
                imageryArrayAdapter = new ArrayAdapter<Imagery>(this, R.layout.support_simple_spinner_dropdown_item, imageries.toArray(new Imagery[0]));
                break;
            case GetExercisesTask.var:
                exercises = (List<Exercise>) objects[0];
                exerciseArrayAdapter = new ArrayAdapter<Exercise>(this, R.layout.support_simple_spinner_dropdown_item, exercises.toArray(new Exercise[0]));
                itemsToListView(exerciseArrayAdapter, exerciseClickedHandler);
                break;
        }
    }
}
