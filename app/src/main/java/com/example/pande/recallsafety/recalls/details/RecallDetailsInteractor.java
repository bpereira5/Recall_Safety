package com.example.pande.recallsafety.recalls.details;

import com.example.pande.recallsafety.models.details.Details;
import com.example.pande.recallsafety.recalls.RecallAPI;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RecallDetailsInteractor {

    private static Details details;
    private OnRecallDetailsFinishedListener listener;

    public RecallDetailsInteractor(OnRecallDetailsFinishedListener listener) {
        this.listener = listener;
    }

    public void queryDetailsAPI(String recallID) {
        RecallAPI service;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://healthycanadians.gc.ca")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(RecallAPI.class);

        Call<Details> call = service.recallDetails(recallID);

        call.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(Response<Details> response, Retrofit r) {
                details = response.body();
                if (response.body() != null) {
                    listener.onSuccess(details);
                } else {
                    listener.onFailure(response.message());
                }

            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }
}
