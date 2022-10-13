package com.uniritter.cdm.activitytwo.presenter;

import android.app.Activity;

import com.uniritter.cdm.activitytwo.model.IToDoModel;

import java.util.List;

public class ToDoPresenterContract {
    public interface View {
        void onToDosResult(List<IToDoModel> toDos);

        Activity getActivity();
    }

    public interface Presenter {
        void getToDos(int userId);
    }
}
