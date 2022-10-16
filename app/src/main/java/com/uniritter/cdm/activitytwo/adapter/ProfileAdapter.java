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

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<IUserModel> data;

    public ProfileAdapter(List<IUserModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list_details_3, parent,false);
        return new ProfileViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        IUserModel objUser = this.data.get(position);

        ((TextView)((ProfileViewHolder) holder).itemView.findViewById(R.id.itemOne)).setText(
                "General"
                + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "Username: " + objUser.getUserName()
                + System.getProperty("line.separator")
                + "Email: " + objUser.getUserEmail()
                + System.getProperty("line.separator")
                + "Phone: " + objUser.getUserPhone()
                + System.getProperty("line.separator")
                + "Website: " + objUser.getUserWebsite()
        );

        ((TextView)((ProfileViewHolder) holder).itemView.findViewById(R.id.itemTwo)).setText(
                "Company"
                + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "Name: " + objUser.getUserCompany().getName() + ": " + objUser.getUserCompany().getCatchPhrase()
                + System.getProperty("line.separator")
                + "BS: " + objUser.getUserCompany().getBs()
        );

        ((TextView)((ProfileViewHolder) holder).itemView.findViewById(R.id.itemThree)).setText(
                "Address"
                + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + "Zip code: " + objUser.getUserAddress().getZipCode()
                + System.getProperty("line.separator")
                + "Street: " + objUser.getUserAddress().getStreet()
                + System.getProperty("line.separator")
                + "Suite: " + objUser.getUserAddress().getSuite()
                + System.getProperty("line.separator")
                + "Geo: " + objUser.getUserAddress().getGeo().getLat() + " x " + objUser.getUserAddress().getGeo().getLng()
        );
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}

class ProfileViewHolder extends RecyclerView.ViewHolder {
    public View view;

    public ProfileViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
    }
}
