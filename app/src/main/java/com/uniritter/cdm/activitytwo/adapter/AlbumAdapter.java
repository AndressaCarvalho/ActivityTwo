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
import com.uniritter.cdm.activitytwo.model.IAlbumModel;
import com.uniritter.cdm.activitytwo.view.AlbumDetailsActivity;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<IAlbumModel> data;

    public AlbumAdapter(List<IAlbumModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list_1, parent,false);
        return new AlbumViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        IAlbumModel objAlbum = this.data.get(position);

        ((TextView)((AlbumViewHolder) holder).itemView.findViewById(R.id.item)).setText("Title: " + objAlbum.getAlbumTitle());

        holder.itemView.setOnClickListener((view)->{
            Intent intent = new Intent(view.getContext(), AlbumDetailsActivity.class);
            intent.putExtra("albumObject", (Parcelable) objAlbum);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}

class AlbumViewHolder extends RecyclerView.ViewHolder {
    public View view;

    public AlbumViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
    }
}
