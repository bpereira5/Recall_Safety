package com.bpcoding.pande.recallsafety.recalls.search;

import com.bpcoding.pande.recallsafety.models.SearchResults;

public interface IRecallSearchView {
    void onRecallLoadedSuccess(SearchResults results);
    void onRecallLoadedFailure(String error);
    void onRecalEmpty(SearchResults results);
}
