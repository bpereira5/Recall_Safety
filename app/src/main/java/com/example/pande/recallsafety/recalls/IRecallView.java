package com.example.pande.recallsafety.recalls;

import com.example.pande.recallsafety.models.RecallResults;

public interface IRecallView {
    void onRecallLoadedSuccess(RecallResults list, String category);
    void onRecallLoadedFailure(String error);
}
