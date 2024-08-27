package com.example.demo.domain;

public class QueryFieldConfig {

    private String id;
    private String searchId;
    private String selectedField;
    private String tableField;
    private String tableName;
    private String aggregateFun;


    public QueryFieldConfig(){}

    public QueryFieldConfig(String id, String searchId, String selectedField, String tableField, String tableName, String aggregateFun) {
        this.id = id;
        this.searchId = searchId;
        this.selectedField = selectedField;
        this.tableField = tableField;
        this.tableName = tableName;
        this.aggregateFun = aggregateFun;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getSelectedField() {
        return selectedField;
    }

    public void setSelectedField(String selectedField) {
        this.selectedField = selectedField;
    }

    public String getTableField() {
        return tableField;
    }

    public void setTableField(String tableField) {
        this.tableField = tableField;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAggregateFun() {
        return aggregateFun;
    }

    public void setAggregateFun(String aggregateFun) {
        this.aggregateFun = aggregateFun;
    }
}
