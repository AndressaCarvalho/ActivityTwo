package com.uniritter.cdm.activitytwo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniritter.cdm.activitytwo.R;
import com.uniritter.cdm.activitytwo.model.IUserModel;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<IUserModel> data;

    public UserAdapter(List<IUserModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home, parent,false);
        return new UserViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class UserViewHolder extends RecyclerView.ViewHolder {
    public View view;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }
}
