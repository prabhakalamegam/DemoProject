package com.example.demo.domain;

public class QueryDataSourceConfig {

    private String id;
    private String searchId;
    private String joinType;
    private String joinStr;
    private String groupByFields;

    public QueryDataSourceConfig(){

    }

    public QueryDataSourceConfig(String id, String searchId, String joinType, String joinStr, String groupByFields) {
        this.id = id;
        this.searchId = searchId;
        this.joinType = joinType;
        this.joinStr = joinStr;
        this.groupByFields = groupByFields;
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

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    public String getJoinStr() {
        return joinStr;
    }

    public void setJoinStr(String joinStr) {
        this.joinStr = joinStr;
    }

    public String getGroupByFields() {
        return groupByFields;
    }

    public void setGroupByFields(String groupByFields) {
        this.groupByFields = groupByFields;
    }
}
