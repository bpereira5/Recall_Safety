package com.bpcoding.pande.recallsafety.recalls.search;

import com.bpcoding.pande.recallsafety.models.SearchResults;

public interface OnSearchFinishedListener {
    void onSuccess(SearchResults results);
    void onFailure(String error);
    void onEmpty(SearchResults results);
}
