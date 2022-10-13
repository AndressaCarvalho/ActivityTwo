package com.uniritter.cdm.activitytwo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniritter.cdm.activitytwo.R;
import com.uniritter.cdm.activitytwo.model.IToDoModel;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<IToDoModel> data;

    public ToDoAdapter(List<IToDoModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list_2, parent,false);
        return new ToDoViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        IToDoModel objToDo = data.get(position);

        ((TextView)((ToDoViewHolder) holder).itemView.findViewById(R.id.item)).setText(objToDo.getToDoTitle());
        ((CheckBox)((ToDoViewHolder) holder).itemView.findViewById(R.id.itemCheckbox)).setChecked(objToDo.getToDoCompleted());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class ToDoViewHolder extends RecyclerView.ViewHolder {
    public View view;

    public ToDoViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }
}
