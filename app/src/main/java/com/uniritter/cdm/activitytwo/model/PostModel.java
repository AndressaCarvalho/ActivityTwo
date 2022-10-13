package com.uniritter.cdm.activitytwo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PostModel implements IPostModel, Parcelable {
    private int postId;
    private int postUserId;
    private String postTitle;
    private String postBody;

    public PostModel(int postId, int postUserId, String postTitle, String postBody) {
        this.postId = postId;
        this.postUserId = postUserId;
        this.postTitle = postTitle;
        this.postBody = postBody;
    }

    protected PostModel(Parcel in) {
        postId = in.readInt();
        postUserId = in.readInt();
        postTitle = in.readString();
        postBody = in.readString();
    }

    @Override
    public int getPostId() {
        return this.postId;
    }

    @Override
    public int getPostUserId() {
        return this.postUserId;
    }

    @Override
    public String getPostTitle() {
        return this.postTitle;
    }

    @Override
    public String getPostBody() {
        return this.postBody;
    }

    public static final Creator<PostModel> CREATOR = new Creator<PostModel>() {
        @Override
        public PostModel createFromParcel(Parcel in) {
            return new PostModel(in);
        }

        @Override
        public PostModel[] newArray(int size) {
            return new PostModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(postId);
        parcel.writeInt(postUserId);
        parcel.writeString(postTitle);
        parcel.writeString(postBody);
    }
}
