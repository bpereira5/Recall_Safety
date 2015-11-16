package com.example.pande.recallsafety.recalls;

import com.example.pande.recallsafety.models.RecallResults;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RecallInteractor {

    private static RecallResults results;
    private OnRecallInteractorFinishedListener listener;

    public RecallInteractor(OnRecallInteractorFinishedListener listener) {
        this.listener = listener;
    }

    public void queryAPI(final String category) {
        if(results == null) {
            RecallAPI service;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://healthycanadians.gc.ca")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(RecallAPI.class);

            Call<RecallResults> call = service.listRecalls();

            call.enqueue(new Callback<RecallResults>() {
                @Override
                public void onResponse(Response<RecallResults> response, Retrofit r) {
                    results = response.body();
                    if(response.body() != null) {
                        listener.onSuccess(results, category);
                    }else{
                        listener.onFailure(response.message());
                    }

                }

                @Override
                public void onFailure(Throwable t) {
                    listener.onFailure(t.getMessage());
                }
            });
        }else{
            listener.onSuccess(results, category);
        }
    }

}