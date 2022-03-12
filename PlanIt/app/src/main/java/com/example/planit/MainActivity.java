package com.example.planit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.planit.Adapter.ToDoAdapter;
import com.example.planit.Model.ToDoModel;
import com.example.planit.Utils.Databasehandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener  {

private Button button;
    private RecyclerView tasksRecycler;
    private ToDoAdapter tasksAdapter;
    private FloatingActionButton fab;

    public Databasehandler db;

    private List<ToDoModel> taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //hiding the action bar
        getSupportActionBar().hide();
        tasksRecycler = findViewById(R.id.tasksRecycleView);
        tasksRecycler.setLayoutManager(new LinearLayoutManager(this));
        db = new Databasehandler(this);
        db.openDatabase();

        tasksAdapter = new ToDoAdapter(db,MainActivity.this);
        tasksRecycler.setAdapter(tasksAdapter);
        fab = findViewById(R.id.add_tasksd);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);

        tasksAdapter.setTasks(taskList);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecycler);
button= findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTime();

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

    }

    public void openTime(){
        Intent intent = new Intent(this,Time.class);
        startActivity(intent);

    }
    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}