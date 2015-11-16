package com.example.pande.recallsafety.recalls.details;

import com.example.pande.recallsafety.models.details.Details;

public interface IRecallDetailsView {
    void onRecallLoadedSuccess(Details details);
    void onRecallLoadedFailure(String error);
}
