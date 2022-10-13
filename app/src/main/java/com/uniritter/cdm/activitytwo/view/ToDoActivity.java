package com.uniritter.cdm.activitytwo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.uniritter.cdm.activitytwo.R;
import com.uniritter.cdm.activitytwo.adapter.ToDoAdapter;
import com.uniritter.cdm.activitytwo.model.IToDoModel;
import com.uniritter.cdm.activitytwo.presenter.ToDoPresenterContract;

import java.util.List;

public class ToDoActivity extends AppCompatActivity implements ToDoPresenterContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        List<IToDoModel> toDos = (List<IToDoModel>) getIntent().getSerializableExtra("toDoList");

        ToDoAdapter adapter = new ToDoAdapter(toDos);
        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerViewToDos);
        rv.setHasFixedSize(true);

        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
    }

    @Override
    public void onToDosResult(List<IToDoModel> toDos) {
        if (toDos != null) {

        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}