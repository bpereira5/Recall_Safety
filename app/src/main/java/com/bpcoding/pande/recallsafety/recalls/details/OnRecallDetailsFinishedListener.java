package com.bpcoding.pande.recallsafety.recalls.details;

import com.bpcoding.pande.recallsafety.models.details.Details;

public interface OnRecallDetailsFinishedListener {
    void onSuccess(Details details);
    void onFailure(String error);
}
