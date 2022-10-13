package com.uniritter.cdm.activitytwo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CommentModel implements ICommentModel, Parcelable {
    private int commentId;
    private int commentPostId;
    private String commentName;
    private String commentEmail;
    private String commentBody;

    public CommentModel(int commentId, int CommentPostId, String commentName, String commentEmail, String commentBody) {
        this.commentId = commentId;
        this.commentPostId = CommentPostId;
        this.commentName = commentName;
        this.commentEmail = commentEmail;
        this.commentBody = commentBody;
    }

    protected CommentModel(Parcel in) {
        commentId = in.readInt();
        commentPostId = in.readInt();
        commentName = in.readString();
        commentEmail = in.readString();
        commentBody = in.readString();
    }

    @Override
    public int getCommentId() {
        return this.commentId;
    }

    @Override
    public int getCommentPostId() {
        return this.commentPostId;
    }

    @Override
    public String getCommentName() {
        return this.commentName;
    }

    @Override
    public String getCommentEmail() {
        return this.commentEmail;
    }

    @Override
    public String getCommentBody() {
        return this.commentBody;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(commentId);
        dest.writeInt(commentPostId);
        dest.writeString(commentName);
        dest.writeString(commentEmail);
        dest.writeString(commentBody);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommentModel> CREATOR = new Creator<CommentModel>() {
        @Override
        public CommentModel createFromParcel(Parcel in) {
            return new CommentModel(in);
        }

        @Override
        public CommentModel[] newArray(int size) {
            return new CommentModel[size];
        }
    };
}
