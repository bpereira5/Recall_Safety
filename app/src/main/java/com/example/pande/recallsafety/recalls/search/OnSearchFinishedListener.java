package com.example.pande.recallsafety.recalls.search;

import com.example.pande.recallsafety.models.SearchResults;

public interface OnSearchFinishedListener {
    void onSuccess(SearchResults results);
    void onFailure(String error);
    void onEmpty(SearchResults results);
}
