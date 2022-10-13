package com.uniritter.cdm.activitytwo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uniritter.cdm.activitytwo.R;
import com.uniritter.cdm.activitytwo.model.IPhotoModel;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<IPhotoModel> data;

    public PhotoAdapter(List<IPhotoModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list_details_image, parent,false);
        return new PhotoViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        IPhotoModel objPhoto = data.get(position);

        ImageView imageView = (ImageView)((PhotoViewHolder) holder).itemView.findViewById(R.id.item);
        Picasso.get()
                .load(objPhoto.getPhotoUrl())
                .resize(900, 900)
                .centerCrop()
                .into(imageView);
        ((TextView)((PhotoViewHolder) holder).itemView.findViewById(R.id.itemTitle)).setText(objPhoto.getPhotoTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class PhotoViewHolder extends RecyclerView.ViewHolder {
    public View view;

    public PhotoViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }
}
