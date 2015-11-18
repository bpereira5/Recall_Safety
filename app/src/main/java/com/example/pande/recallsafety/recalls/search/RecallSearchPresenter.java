package com.example.pande.recallsafety.recalls.search;

import com.example.pande.recallsafety.models.SearchResults;

public class RecallSearchPresenter implements IRecallSearchPresenter, OnSearchFinishedListener{
    private IRecallSearchView view;
    private RecallSearchInteractor interactor;

    public RecallSearchPresenter(IRecallSearchView view){
        this.view = view;
        this.interactor = new RecallSearchInteractor(this);
    }

    @Override
    public void querySearchAPI(String category){
        interactor.querySearchAPI(category);
    }

    @Override
    public void onSuccess(SearchResults results){
        view.onRecallLoadedSuccess(results);
    }

    @Override
    public void onFailure(String error){
        view.onRecallLoadedFailure(error);
    }

    @Override
    public void onEmpty(SearchResults results) {
        view.onRecalEmpty(results);
    }
}
