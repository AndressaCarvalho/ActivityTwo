package com.uniritter.cdm.activitytwo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ToDoModel implements IToDoModel, Parcelable {
    private int toDoId;
    private int toDoUserId;
    private String toDoTitle;
    private Boolean toDoCompleted;

    public ToDoModel(int toDoId, int toDoUserId, String toDoTitle, Boolean toDoCompleted) {
        this.toDoId = toDoId;
        this.toDoUserId = toDoUserId;
        this.toDoTitle = toDoTitle;
        this.toDoCompleted = toDoCompleted;
    }

    protected ToDoModel(Parcel in) {
        toDoId = in.readInt();
        toDoUserId = in.readInt();
        toDoTitle = in.readString();
        byte tmpToDoCompleted = in.readByte();
        toDoCompleted = tmpToDoCompleted == 0 ? null : tmpToDoCompleted == 1;
    }

    @Override
    public int getToDoId() {
        return this.toDoId;
    }

    @Override
    public int getToDoUserId() {
        return this.toDoUserId;
    }

    @Override
    public String getToDoTitle() {
        return this.toDoTitle;
    }

    @Override
    public Boolean getToDoCompleted() {
        return this.toDoCompleted;
    }

    public static final Creator<ToDoModel> CREATOR = new Creator<ToDoModel>() {
        @Override
        public ToDoModel createFromParcel(Parcel in) {
            return new ToDoModel(in);
        }

        @Override
        public ToDoModel[] newArray(int size) {
            return new ToDoModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(toDoId);
        parcel.writeInt(toDoUserId);
        parcel.writeString(toDoTitle);
        parcel.writeByte((byte) (toDoCompleted == null ? 0 : toDoCompleted ? 1 : 2));
    }
}
