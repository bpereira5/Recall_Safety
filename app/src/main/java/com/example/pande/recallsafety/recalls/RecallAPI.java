package com.example.pande.recallsafety.recalls;


import com.example.pande.recallsafety.models.RecallResults;
import com.example.pande.recallsafety.models.SearchResults;
import com.example.pande.recallsafety.models.details.Details;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.Url;

public interface RecallAPI {
    @GET("/recall-alert-rappel-avis/api/recent/en")
    Call<RecallResults> listRecalls();

    @GET("/recall-alert-rappel-avis/api/{id}")
    Call<Details> recallDetails(@Path("id") String id);

    @GET
    Call<SearchResults> searchRecalls(@Url String search);
}
