package com.example.pande.recallsafety.recalls.details;

import com.example.pande.recallsafety.models.details.Details;

public interface OnRecallDetailsFinishedListener {
    void onSuccess(Details details);
    void onFailure(String error);
}
