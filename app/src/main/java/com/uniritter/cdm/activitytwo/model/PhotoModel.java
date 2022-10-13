package com.uniritter.cdm.activitytwo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PhotoModel implements IPhotoModel, Parcelable {
    private int photoId;
    private int photoAlbumId;
    private String photoTitle;
    private String photoUrl;
    private String photoThumbnailUrl;

    public PhotoModel(int photoId, int photoAlbumId, String photoTitle, String photoUrl, String photoThumbnailUrl) {
        this.photoId = photoId;
        this.photoAlbumId = photoAlbumId;
        this.photoTitle = photoTitle;
        this.photoUrl = photoUrl;
        this.photoThumbnailUrl = photoThumbnailUrl;
    }

    protected PhotoModel(Parcel in) {
        photoId = in.readInt();
        photoAlbumId = in.readInt();
        photoTitle = in.readString();
        photoUrl = in.readString();
        photoThumbnailUrl = in.readString();
    }

    @Override
    public int getPhotoId() {
        return this.photoId;
    }

    @Override
    public int getPhotoAlbumId() {
        return this.photoAlbumId;
    }

    @Override
    public String getPhotoTitle() {
        return this.photoTitle;
    }

    @Override
    public String getPhotoUrl() {
        return this.photoUrl;
    }

    @Override
    public String getPhotoThumbnailUrl() {
        return this.photoThumbnailUrl;
    }

    public static final Creator<PhotoModel> CREATOR = new Creator<PhotoModel>() {
        @Override
        public PhotoModel createFromParcel(Parcel in) {
            return new PhotoModel(in);
        }

        @Override
        public PhotoModel[] newArray(int size) {
            return new PhotoModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(photoId);
        parcel.writeInt(photoAlbumId);
        parcel.writeString(photoTitle);
        parcel.writeString(photoUrl);
        parcel.writeString(photoThumbnailUrl);
    }
}
