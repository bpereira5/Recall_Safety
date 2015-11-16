package com.example.pande.recallsafety.recalls;

import com.example.pande.recallsafety.models.RecallResults;

public interface OnRecallInteractorFinishedListener {
    void onSuccess(RecallResults list, String cat);
    void onFailure(String error);
}
