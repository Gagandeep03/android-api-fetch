package com.androidsample.beans;

import com.androidsample.roomdatabase.tables.ResultsEntity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponse {


    @SerializedName("status")
    private String status;
    @SerializedName("copyright")
    private String copyright;
    @SerializedName("num_results")
    private int numResults;
    @SerializedName("results")
    private List<ResultsEntity> results;
    /**
     * message : Invalid authentication credentials
     */

    @SerializedName("message")
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public int getNumResults() {
        return numResults;
    }

    public void setNumResults(int numResults) {
        this.numResults = numResults;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
