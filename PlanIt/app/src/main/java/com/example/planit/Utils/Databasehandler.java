package com.example.planit.Utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.planit.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;


public class Databasehandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "toDoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, "
            + STATUS + " INTEGER)";


    private SQLiteDatabase db;


    public Databasehandler(Context context){
    super(context,NAME,null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
     sqLiteDatabase.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //DROP THE TABLE IF EXIST ALREADY
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        //CREATE A NEW TABLE
        onCreate(sqLiteDatabase);
    }
    public void openDatabase(){
        db = this.getWritableDatabase();
    }

    public void insertTask(ToDoModel task){
        ContentValues cv = new ContentValues();
        cv.put(TASK , task.getTask());
        db.insert(TODO_TABLE,null, cv);
    }


    public List<ToDoModel> getAllTasks(){
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {

            cur = db.query(TODO_TABLE, null, null, null, null, null, null, null);


            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndexOrThrow(ID)));
                        task.setTask(cur.getString(cur.getColumnIndexOrThrow(TASK)));
                        taskList.add(task);
                    }while(cur.moveToNext());


                }


            }


        }

        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
    return taskList;

    }

    public void updateTask(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TODO_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
    }
}