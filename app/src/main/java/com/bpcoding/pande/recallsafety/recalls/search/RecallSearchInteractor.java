package com.bpcoding.pande.recallsafety.recalls.search;


import com.bpcoding.pande.recallsafety.models.SearchResults;
import com.bpcoding.pande.recallsafety.recalls.RecallAPI;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RecallSearchInteractor {

    private static SearchResults results;
    private OnSearchFinishedListener listener;

    public RecallSearchInteractor(OnSearchFinishedListener listener) {
        this.listener = listener;
    }

    public void querySearchAPI(final String search) {
        RecallAPI service;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://healthycanadians.gc.ca")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(RecallAPI.class);

        Call<SearchResults> call = service.searchRecalls(search);

        call.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Response<SearchResults> response, Retrofit r) {
                results = response.body();
                if (response.body() != null) {
                    if(results.getResults().size() > 0) {
                        listener.onSuccess(results);
                    }else{
                        listener.onEmpty(results);
                    }
                } else {
                    listener.onEmpty(results);
                }

            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }

}
