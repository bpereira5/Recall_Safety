package com.example.pande.recallsafety.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class RecallResults {

    @SerializedName("results")
    @Expose
    public Results results;

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public class Results {

        @SerializedName("ALL")
        @Expose
        public List<All> mAll = new ArrayList<>();
        @SerializedName("FOOD")
        @Expose
        public List<Food> mFood = new ArrayList<>();
        @SerializedName("VEHICLE")
        @Expose
        public List<Vehicles> mVehicles = new ArrayList<>();
        @SerializedName("HEALTH")
        @Expose
        public List<Health> mHealth = new ArrayList<>();
        @SerializedName("CPS")
        @Expose
        public List<ConsumerProducts> mConsumerProducts = new ArrayList<>();

        //getters setters
        public List<Food> getmFood() {
            return mFood;
        }

        public void setmFood(List<Food> mFood) {
            this.mFood = mFood;
        }

        public List<Vehicles> getmVehicles() {
            return mVehicles;
        }

        public void setmVehicles(List<Vehicles> mVehicles) {
            this.mVehicles = mVehicles;
        }

        public List<Health> getmHealth() {
            return mHealth;
        }

        public void setmHealth(List<Health> mHealth) {
            this.mHealth = mHealth;
        }

        public List<ConsumerProducts> getmConsumerProducts() {
            return mConsumerProducts;
        }

        public void setmConsumerProducts(List<ConsumerProducts> mConsumerProducts) {
            this.mConsumerProducts = mConsumerProducts;
        }

    }

}
