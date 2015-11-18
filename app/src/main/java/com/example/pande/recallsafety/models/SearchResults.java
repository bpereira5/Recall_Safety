package com.example.pande.recallsafety.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResults {

    @SerializedName("results")
    @Expose
    public List<Result> results = new ArrayList<>();
    @SerializedName("results_count")
    @Expose
    public Integer resultsCount;

    public class Result {

        @SerializedName("recallId")
        @Expose
        public String recallId;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("department")
        @Expose
        public String department;
        @SerializedName("date_published")
        @Expose
        public Integer datePublished;
        @SerializedName("category")
        @Expose
        public List<String> category = new ArrayList<>();
        @SerializedName("url")
        @Expose
        public String url;

        public String getRecallId() {
            return recallId;
        }

        public String getTitle() {
            return title;
        }

        public String getDepartment() {
            return department;
        }

        public Integer getDatePublished() {
            return datePublished;
        }

        public List<String> getCategory() {
            return category;
        }

        public String getUrl() {
            return url;
        }
    }

    public List<Result> getResults() {
        return results;
    }

    public Integer getResultsCount() {
        return resultsCount;
    }
}
