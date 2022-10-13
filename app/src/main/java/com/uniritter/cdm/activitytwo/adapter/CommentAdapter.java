package com.uniritter.cdm.activitytwo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniritter.cdm.activitytwo.R;
import com.uniritter.cdm.activitytwo.model.ICommentModel;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ICommentModel> data;

    public CommentAdapter(List<ICommentModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list_details, parent,false);
        return new CommentViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ICommentModel objComment = data.get(position);

        ((TextView)((CommentViewHolder) holder).itemView.findViewById(R.id.item)).setText("Title: " + objComment.getCommentName()
                + System.getProperty("line.separator")
                + "From: " + objComment.getCommentEmail()
                + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + objComment.getCommentBody()
        );
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class CommentViewHolder extends RecyclerView.ViewHolder {
    public View view;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }
}
