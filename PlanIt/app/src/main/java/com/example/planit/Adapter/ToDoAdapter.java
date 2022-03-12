package com.example.planit.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.DialogTitle;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planit.AddNewTask;
import com.example.planit.MainActivity;
import com.example.planit.Model.ToDoModel;
import com.example.planit.R;
import com.example.planit.Utils.Databasehandler;

import java.util.List;



        import android.content.Context;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
/*
        import net.penguincoders.doit.AddNewTask;
        import net.penguincoders.doit.MainActivity;
        import net.penguincoders.doit.Model.ToDoModel;
        import net.penguincoders.doit.R;
        import net.penguincoders.doit.Utils.DatabaseHandler;
*/
        import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> todoList;

    private MainActivity activity;
    private static final String TAG = "TodoAdapter";
    private Databasehandler db;

    public ToDoAdapter(Databasehandler db, MainActivity activity) {
          this.db = db;
        this.activity = activity;
    }

    //this is standard and I don't understand its role
  @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewBuilderCalled");
        db.openDatabase();

        ToDoModel item = todoList.get(position);
        holder.textView.setText(item.getTask());

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textview);
        }
    }


    public void setTasks(List<ToDoModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

  /*  private boolean toBoolean(int n) {
        return n != 0;
    }*/

    public Context getContext() {
        return activity;
    }


    public void deleteItem(int position) {
        ToDoModel item = todoList.get(position);
        db.deleteTask(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        ToDoModel item = todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }


}
