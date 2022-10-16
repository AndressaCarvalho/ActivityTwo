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
import com.uniritter.cdm.activitytwo.model.IPostModel;
import com.uniritter.cdm.activitytwo.view.PostDetailsActivity;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<IPostModel> data;

    public PostAdapter(List<IPostModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list_1, parent,false);
        return new PostViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        IPostModel objPost = this.data.get(position);

        ((TextView)((PostViewHolder) holder).itemView.findViewById(R.id.item)).setText("Title: " + objPost.getPostTitle());

        holder.itemView.setOnClickListener((view)->{
            Intent intent = new Intent(view.getContext(), PostDetailsActivity.class);
            intent.putExtra("postObject", (Parcelable) objPost);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}

class PostViewHolder extends RecyclerView.ViewHolder {
    public View view;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
    }
}
