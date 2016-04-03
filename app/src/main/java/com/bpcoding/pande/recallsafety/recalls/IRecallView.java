package com.bpcoding.pande.recallsafety.recalls;

import com.bpcoding.pande.recallsafety.models.RecallResults;

public interface IRecallView {
    void onRecallLoadedSuccess(RecallResults list, String category);
    void onRecallLoadedFailure(String error);
}
