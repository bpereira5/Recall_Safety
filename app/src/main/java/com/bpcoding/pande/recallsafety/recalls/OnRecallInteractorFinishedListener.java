package com.bpcoding.pande.recallsafety.recalls;

import com.bpcoding.pande.recallsafety.models.RecallResults;

public interface OnRecallInteractorFinishedListener {
    void onSuccess(RecallResults list, String cat);
    void onFailure(String error);
}
