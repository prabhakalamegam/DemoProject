package com.example.demo.domain;

import java.util.List;

public class DynamicRequest {

    private String searchId;
    private List<String> selectedFields;
    private String criterias;
    private boolean convertBigDecimalToString;
    private boolean onlyGettingCountForFirstPage;
    private String[][] aggregation;

    public DynamicRequest() {
       }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public List<String> getSelectedFields() {
        return selectedFields;
    }

    public void setSelectedFields(List<String> selectedFields) {
        this.selectedFields = selectedFields;
    }

    public String getCriterias() {
        return criterias;
    }

    public void setCriterias(String criterias) {
        this.criterias = criterias;
    }

    public boolean isConvertBigDecimalToString() {
        return convertBigDecimalToString;
    }

    public void setConvertBigDecimalToString(boolean convertBigDecimalToString) {
        this.convertBigDecimalToString = convertBigDecimalToString;
    }

    public boolean isOnlyGettingCountForFirstPage() {
        return onlyGettingCountForFirstPage;
    }

    public void setOnlyGettingCountForFirstPage(boolean onlyGettingCountForFirstPage) {
        this.onlyGettingCountForFirstPage = onlyGettingCountForFirstPage;
    }

    public String[][] getAggregation() {
        return aggregation;
    }

    public void setAggregation(String[][] aggregation) {
        this.aggregation = aggregation;
    }
}
