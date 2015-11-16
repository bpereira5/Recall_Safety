package com.example.pande.recallsafety.recalls.details;

import com.example.pande.recallsafety.models.details.Details;

public class RecallDetailsPresenter implements IRecallDetailsPresenter, OnRecallDetailsFinishedListener {

    private IRecallDetailsView view;
    private RecallDetailsInteractor interactor;

    public RecallDetailsPresenter(IRecallDetailsView view){
        this.view = view;
        this.interactor = new RecallDetailsInteractor(this);
    }
    @Override
    public void queryDetailsAPI(String details){
        interactor.queryDetailsAPI(details);
    }

    @Override
    public void onSuccess(Details details){
        view.onRecallLoadedSuccess(details);
    }

    @Override
    public void onFailure(String error){
        view.onRecallLoadedFailure(error);
    }
}
