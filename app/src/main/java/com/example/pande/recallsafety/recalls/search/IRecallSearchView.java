package com.example.pande.recallsafety.recalls.search;

import com.example.pande.recallsafety.models.SearchResults;

public interface IRecallSearchView {
    void onRecallLoadedSuccess(SearchResults results);
    void onRecallLoadedFailure(String error);
    void onRecalEmpty(SearchResults results);
}
