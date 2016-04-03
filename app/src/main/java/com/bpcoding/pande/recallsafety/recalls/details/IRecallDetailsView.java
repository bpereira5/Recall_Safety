package com.bpcoding.pande.recallsafety.recalls.details;

import com.bpcoding.pande.recallsafety.models.details.Details;

public interface IRecallDetailsView {
    void onRecallLoadedSuccess(Details details);
    void onRecallLoadedFailure(String error);
}
