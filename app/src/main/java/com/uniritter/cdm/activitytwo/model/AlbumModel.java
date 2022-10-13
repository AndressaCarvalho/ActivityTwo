package com.uniritter.cdm.activitytwo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AlbumModel implements IAlbumModel, Parcelable {
    private int albumId;
    private int albumUserId;
    private String albumTitle;

    public AlbumModel(int albumId, int albumUserId, String albumTitle) {
        this.albumId = albumId;
        this.albumUserId = albumUserId;
        this.albumTitle = albumTitle;
    }

    protected AlbumModel(Parcel in) {
        albumId = in.readInt();
        albumUserId = in.readInt();
        albumTitle = in.readString();
    }

    public static final Creator<AlbumModel> CREATOR = new Creator<AlbumModel>() {
        @Override
        public AlbumModel createFromParcel(Parcel in) {
            return new AlbumModel(in);
        }

        @Override
        public AlbumModel[] newArray(int size) {
            return new AlbumModel[size];
        }
    };

    @Override
    public int getAlbumId() {
        return this.albumId;
    }

    @Override
    public int getAlbumUserId() {
        return this.albumUserId;
    }

    @Override
    public String getAlbumTitle() {
        return this.albumTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(albumId);
        parcel.writeInt(albumUserId);
        parcel.writeString(albumTitle);
    }
}
