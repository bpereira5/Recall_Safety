package com.bpcoding.pande.recallsafety.recalls;


import com.bpcoding.pande.recallsafety.models.RecallResults;
import com.bpcoding.pande.recallsafety.models.SearchResults;
import com.bpcoding.pande.recallsafety.models.details.Details;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Url;

public interface RecallAPI {
    @GET("/recall-alert-rappel-avis/api/recent/en")
    Call<RecallResults> listRecalls();

    @GET("/recall-alert-rappel-avis/api/{id}")
    Call<Details> recallDetails(@Path("id") String id);

    @GET
    Call<SearchResults> searchRecalls(@Url String search);
}
