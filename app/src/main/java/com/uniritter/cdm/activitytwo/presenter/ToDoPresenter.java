package com.uniritter.cdm.activitytwo.presenter;

import com.uniritter.cdm.activitytwo.repository.ToDoRepository;

public class ToDoPresenter implements ToDoPresenterContract.Presenter {
    private ToDoPresenterContract.View toDoView;
    private ToDoRepository repository;

    public ToDoPresenter(ToDoPresenterContract.View toDoView) {
        this.toDoView = toDoView;
        this.repository = ToDoRepository.getInstance(toDoView.getActivity());
    }

    @Override
    public void getToDos(int userId) {
        this.toDoView.onToDosResult(this.repository.getToDosByUserId(userId));
    }
}
