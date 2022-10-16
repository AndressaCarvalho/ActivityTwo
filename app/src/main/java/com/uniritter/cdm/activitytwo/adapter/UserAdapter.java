package com.uniritter.cdm.activitytwo.adapter;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniritter.cdm.activitytwo.R;
import com.uniritter.cdm.activitytwo.model.IUserModel;
import com.uniritter.cdm.activitytwo.view.UserDetailsActivity;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<IUserModel> data;

    public UserAdapter(List<IUserModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list_1, parent,false);
        return new UserViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        IUserModel objUser = this.data.get(position);

        ((TextView)((UserViewHolder) holder).itemView.findViewById(R.id.item)).setText("Username: " + objUser.getUserName()
                + System.getProperty("line.separator")
                + "Email: " + objUser.getUserEmail()
        );

        holder.itemView.setOnClickListener((view)->{
            List<IUserModel> listUser = Collections.singletonList(objUser);

            Intent intent = new Intent(view.getContext(), UserDetailsActivity.class);
            intent.putExtra("userData", (Serializable) listUser);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}

class UserViewHolder extends RecyclerView.ViewHolder {
    public View view;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
    }
}
