package com.example.pande.recallsafety.recalls;

import com.example.pande.recallsafety.models.RecallResults;

public class RecallPresenter implements IRecallPresenter, OnRecallInteractorFinishedListener {

    private IRecallView view;
    private RecallInteractor interactor;

    public RecallPresenter(IRecallView view){
        this.view = view;
        this.interactor = new RecallInteractor(this);
    }

    @Override
    public void queryAPI(String category){
        interactor.queryAPI(category);
    }

    @Override
    public void onSuccess(RecallResults results, String category){
        view.onRecallLoadedSuccess(results, category);
    }

    @Override
    public void onFailure(String error){
        view.onRecallLoadedFailure(error);
    }
}
