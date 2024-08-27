package com.example.demo.out;

import com.example.demo.constant.SortDirection;
import com.example.demo.domain.*;
import com.example.demo.port.out.QueryDataPortOut;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.stream.Collectors;
//import com.google.*


public class QueryDataRepository implements QueryDataPortOut {
    private final Logger logger = LogManager.getLogManager().getLogger(String.valueOf(QueryDataRepository.class));
    private EntityManager entityManager;
    
    @Override
    public EnquiryResult<Object> queryData(DynamicRequest dynamicRequest, int rowPerPage, int pageNo, String sortBy, SortDirection sortDirection) {
     JsonObject criteria = JsonParser.parseString(dynamicRequest.getCriterias()).getAsJsonObject();

        return prepareEnquiryResult(dynamicRequest ,criteria,rowPerPage,pageNo,sortBy,sortDirection);
    }

    private EnquiryResult<Object> prepareEnquiryResult(DynamicRequest dynamicRequest, JsonObject criteria, int rowPerPage, int pageNo, String sortBy, SortDirection sortDirection) {

        String searchId = dynamicRequest.getSearchId();
        List<String> selectedFields = dynamicRequest.getSelectedFields();

        boolean convertBigDecimalToString = dynamicRequest.isConvertBigDecimalToString();
        String[][] aggregations = dynamicRequest.getAggregation();
        boolean onlyGettingCOuntForFirstPage = dynamicRequest.isOnlyGettingCountForFirstPage();

        EnquiryResult<Object> enquiryRequest = new EnquiryResult<>();

        if(selectedFields == null || selectedFields.isEmpty()){
            return enquiryRequest;
        }

        Map<String, QueryFieldConfig> queryFieldConfigMap = new HashMap();
        QueryFieldConfig queryFieldConfig = new QueryFieldConfig();
        queryFieldConfig.setId("371");
        queryFieldConfig.setSearchId("search-member-type");
        queryFieldConfig.setTableField("draft_count");
        queryFieldConfig.setSelectedField("draftCount");
        queryFieldConfig.setAggregateFun(null);
        queryFieldConfig.setTableName("v_member_type");

        QueryFieldConfig queryFieldConfig1 = new QueryFieldConfig();
        queryFieldConfig1.setId("3129");
        queryFieldConfig1.setSearchId("search-member-type");
        queryFieldConfig1.setTableField("code");
        queryFieldConfig1.setSelectedField("code");
        queryFieldConfig1.setAggregateFun(null);
        queryFieldConfig1.setTableName("v_member_type");

        QueryFieldConfig queryFieldConfig2 = new QueryFieldConfig();
        queryFieldConfig2.setId("3130");
        queryFieldConfig2.setSearchId("search-member-type");
        queryFieldConfig2.setTableField("description");
        queryFieldConfig2.setSelectedField("description");
        queryFieldConfig2.setAggregateFun(null);
        queryFieldConfig2.setTableName("v_member_type");

        QueryFieldConfig queryFieldConfig3 = new QueryFieldConfig();
        queryFieldConfig3.setId("3131");
        queryFieldConfig3.setSearchId("search-member-type");
        queryFieldConfig3.setTableField("id");
        queryFieldConfig3.setSelectedField("id");
        queryFieldConfig3.setAggregateFun(null);
        queryFieldConfig3.setTableName("v_member_type");

        queryFieldConfigMap.put("draft_count",queryFieldConfig);
        queryFieldConfigMap.put("code",queryFieldConfig1);
        queryFieldConfigMap.put("description",queryFieldConfig2);
        queryFieldConfigMap.put("id",queryFieldConfig3);

        String selectClause = this.prepareSelectClause(selectedFields,(Map) queryFieldConfigMap);

        List<QueryDataSourceConfig> queryDatasourceConfigs = new ArrayList<>();
        QueryDataSourceConfig queryDataSourceConfig = new QueryDataSourceConfig();
        queryDataSourceConfig.setId("5233");
        queryDataSourceConfig.setSearchId("search-member-type");
        queryDataSourceConfig.setJoinType("from");
        queryDataSourceConfig.setJoinStr("v_member_type");
        queryDataSourceConfig.setGroupByFields(null);
        queryDatasourceConfigs.add(queryDataSourceConfig);

        FromClauseResult fromClauseResult = this.prepareFromClause(queryDatasourceConfigs,false);


        Map<String,Object> paraMap = new HashMap<>();
        AtomicInteger secondaryColumnCount = new AtomicInteger();

        String whereClause = this.prepareWhereClause(criteria,paraMap,(Map) queryFieldConfigMap,secondaryColumnCount,fromClauseResult.primaryTable);
        AtomicInteger secondaryColumnCountFromGroup = new AtomicInteger();

        String groupByClause = this.preparGroupByClause(queryDatasourceConfigs,fromClauseResult.primaryTable,secondaryColumnCountFromGroup);
        String orderByClause = this.preparOrderByClause(sortBy,sortDirection,(Map) queryFieldConfigMap);

        String resultSql = selectClause + fromClauseResult.fromClause + whereClause + groupByClause + orderByClause;

        List<Object> dataList = this.getEnquiryResults(resultSql,paraMap,rowPerPage,pageNo);

        if(convertBigDecimalToString){
            this.convertDecimalToString(dataList);
        }


        enquiryRequest.setDataList(dataList);

        if(pageNo >0 && onlyGettingCOuntForFirstPage)
            return enquiryRequest;

        Map<String, BigDecimal> aggregationResult = new HashMap<>();
        String fromClause = getFromClause(secondaryColumnCount,secondaryColumnCountFromGroup,fromClauseResult,queryDatasourceConfigs);

        Long totalCount = this.getTotalCount(fromClause,whereClause,paraMap,groupByClause,aggregations,aggregationResult,queryFieldConfigMap);

        enquiryRequest.setTotalCount(totalCount);
        enquiryRequest.setTotalPageCount((int) this.caculatePageCount(totalCount,rowPerPage));
        enquiryRequest.setAggregationResults(aggregationResult);
        return enquiryRequest;
    }

    private Object caculatePageCount(Long totalCount, int rowPerPage) {
        long pageCount = 0;
        if(rowPerPage >0){
            if(totalCount % rowPerPage !=0){
                pageCount = ((totalCount / rowPerPage) + 1);
            }else{
                pageCount = totalCount / rowPerPage;
            }
        }else{
            if(totalCount > 0){
                pageCount =1;
            }
        }
        return pageCount;
    }

    private Long getTotalCount(String fromClause, String whereClause, Map<String, Object> paraMap, String groupByClause, String[][] aggregations, Map<String, BigDecimal> aggregationResult, Map<String, QueryFieldConfig> queryFieldConfigMap) {

        String countSql = "select count(1)";

        String [] aggregationSelectFields = null;

        if(aggregations !=null){
            aggregationSelectFields = new String [aggregations.length];

            int i=0;
            for (String[] aggreagtion : aggregations){
                aggregationSelectFields[i] = aggreagtion[1];

                QueryFieldConfig config = queryFieldConfigMap.get(aggreagtion[1]);
                aggreagtion[1] = config.getTableName() + "." + config.getTableField();
                i++;
            }

            countSql += ", ";
            String aggregationFields = "";
            for (String[] aggregation : aggregations){
                if(!aggregationFields.isEmpty()){
                    aggregationFields += ", ";
                    aggregationFields += aggregation[0] + "(" + aggregation[1] + ")";
                }
            }
            countSql +=aggregationFields;
        }

        countSql += fromClause + whereClause + groupByClause;

        if(!groupByClause.isEmpty()){
            countSql = "select count(1) from (" + countSql + ") as groupTable";
        }

        Query query = this.prepareQuery(countSql,paraMap);
        List<Object> resultList = query.getResultList();

        if(resultList.get(0) instanceof BigInteger){
            return ((BigInteger) resultList.get(0)).longValue();
        }else{
            Object[] columnValues = (Object[]) resultList.get(0);
            for(int i =1 ; i< columnValues.length ; i++){
                aggregationResult.put(aggregationSelectFields[i-1],(BigDecimal) columnValues[i]);
            }

            return ( (BigInteger) columnValues[0]).longValue();
        }
    }

    private String getFromClause(AtomicInteger secondaryColumnCount, AtomicInteger secondaryColumnCountFromGroup, FromClauseResult fromClauseResult, List<QueryDataSourceConfig> queryDataSourceConfig) {

        String fromClause = secondaryColumnCount.get() > 0 || secondaryColumnCountFromGroup.get() > 0 ?
                fromClauseResult.fromClause : this.prepareFromClause(queryDataSourceConfig,true).fromClause;
        return fromClause;
    }

    private void convertDecimalToString(List<Object> dataList) {

        for (int i = 0; i < dataList.size(); i++) {
            Object[] cells = (Object[]) dataList.get(i);
            for (int j = 0; j < cells.length; j++) {
             if(cells[i] instanceof BigDecimal){
                 cells[i] = cells[i].toString();
             }
            }
        }
    }

    private List<Object> getEnquiryResults(String resultSql, Map<String, Object> paraMap, int rowPerPage, int pageNo) {

        Query query = this.prepareQuery(resultSql,paraMap);

        if(rowPerPage > 0){
            return query.setFirstResult(this.getStartIndex(pageNo,rowPerPage)).setMaxResults(rowPerPage).getResultList();
        }
        else return query.getResultList();
    }


    int getStartIndex(int pageNo,int rowPerPage){
        return pageNo * rowPerPage;
    }
    private Query prepareQuery(String nativeQueryString, Map<String, Object> paraMap) {
     
        Query query = this.entityManager.createNativeQuery(nativeQueryString);
        for(Map.Entry<String,Object> entry: paraMap.entrySet()){
            query.setParameter(entry.getKey(),entry.getValue());
        }
        
        return query;
    }

    private String preparOrderByClause(String sortByList, SortDirection sortDirection, Map queryFieldConfigMap) {

        StringBuilder sql = new StringBuilder();

        if(sortByList !=null && !sortByList.isEmpty() && sortDirection != null){
            List<String> sortList = Arrays.asList(sortByList.split(","));

            for(int i =0;  i < sortList.size() ; i++){
                String sortBy = (String) sortList.get(i);

                QueryFieldConfig config =(QueryFieldConfig)  queryFieldConfigMap.get(sortBy);

                if(config == null){
                    return  sql.toString();
                }

                if(i == 0){
                    sql.append(" order by ");
                }else{
                    sql.append(" , ");
                }

                sql.append(config.getTableName());
                if(config.getAggregateFun() != null && !config.getAggregateFun().isEmpty()){
                    sql.append("__").append(config.getSelectedField());
                }else{
                    sql.append(".").append(config.getTableField());
                }

                if(sortDirection == SortDirection.asc){
                    sql.append(" asc");
                }else{
                    sql.append(" desc");
                }
            }
        }
        return sql.toString();
    }

    private String preparGroupByClause(List<QueryDataSourceConfig> queryDatasourceConfigs, String primaryTable, AtomicInteger secondaryColumnCountFromGroup) {

        StringBuilder groupBy = new StringBuilder("");

        Iterator var5 = queryDatasourceConfigs.iterator();

        while(var5.hasNext()){
            QueryDataSourceConfig queryDataSourceConfig = (QueryDataSourceConfig) var5.next();

            if(queryDataSourceConfig.getGroupByFields() != null && !queryDataSourceConfig.getGroupByFields().isEmpty()){

                if(groupBy.length() !=0){

                    groupBy.append(", ");
                }

                groupBy.append(queryDataSourceConfig.getGroupByFields());
                if(queryDataSourceConfig.getGroupByFields().startsWith(primaryTable)){
                    secondaryColumnCountFromGroup.incrementAndGet();
                }
            }
        }
        return groupBy.length() != 0 ? " group by " + groupBy.toString() : "";
    }

    private String prepareWhereClause(JsonObject whereJson, Map<String, Object> paraMap, Map queryFieldConfigMap, AtomicInteger secondaryColumnCount, String primaryTable) {

        StringBuilder sql = new StringBuilder();
        if (whereJson.keySet().size() > 0 ){
            this.prepareGroup(sql,whereJson,paraMap,queryFieldConfigMap,new AtomicInteger(1),secondaryColumnCount,primaryTable);
        }
        return null;
    }

    private void prepareGroup(StringBuilder sql, JsonObject groupOperator, Map<String, Object> paraMap, Map queryFieldConfigMap, AtomicInteger paramCount, AtomicInteger secondaryColumnCount, String primaryTable) {
        Objects.requireNonNull(this);
        if(!groupOperator.has("and")){
            Objects.requireNonNull(this);
            if(!groupOperator.has("or")){
                return;
            }
        }

        String valueFieldName = "value";
        String valuesFieldName = "values";
        String operator = (String) groupOperator.keySet().iterator().next();
        JsonArray array = groupOperator.get(operator).getAsJsonArray();

        if(array.size() > 0){
            sql.append("(");

            for(int i =0; i< array.size(); ++i){
                JsonObject item = array.get(i).getAsJsonObject();
                Objects.requireNonNull(this);

                if(!item.has("and")){
                    Objects.requireNonNull(this);
                    if(!item.has("or")){
                        QueryFieldConfig config = (QueryFieldConfig)  queryFieldConfigMap.get(item.get("field").getAsString());

                        if(config ==null){
                            this.logger.warning("Query field config is null for " + item.get("field").getAsString());
                        }
                        else{
                            if(!config.getTableName().equalsIgnoreCase(primaryTable)){
                                secondaryColumnCount.incrementAndGet();
                            }
                            if(i > 0){
                                sql.append(operator).append(" ");
                            }

                            String conditionalOperator = ConditionalOperator.fromName(item.get("conditionalOperator").getAsString()).getValue();
                            sql.append(config.getTableName()).append(".").append(config.getTableField()).append(" ").append(conditionalOperator);
                            sql = this.preparePlaceHolder(paraMap,paramCount,item,valueFieldName,valuesFieldName,sql);
                        }
                    }
                    continue;
                }
            }
        }
    }

    private StringBuilder preparePlaceHolder(Map<String, Object> paraMap, AtomicInteger paramCount, JsonObject item, String valueFieldName, String valuesFieldName, StringBuilder sql) {
        String placeHolder = "cond" + paramCount.incrementAndGet();

        if(paraMap.containsKey(placeHolder)){
            placeHolder = "cond" + paramCount.incrementAndGet();
        }

        Object val;
        String placeHoldeStr = "";

        if( null != item.get(valueFieldName) &&  !item.get(valueFieldName).isJsonNull() &&
        !item.get(valueFieldName).getAsString().isEmpty() ){
            val = item.get(valueFieldName).getAsString();
            placeHoldeStr  = " :" + placeHoldeStr;
        }else{

            if(null == item.get(valuesFieldName) || item.get(valuesFieldName).isJsonNull() &&
            item.get(valuesFieldName).getAsJsonObject().size() <=0 ){
             throw new RuntimeException("Invalid criteria");
            }


            JsonArray valueArray = item.getAsJsonArray(valuesFieldName).getAsJsonArray();
            List<String> stringList = new ArrayList<>();

            for(int ii =0 ; ii < valueArray.size();++ii){
                stringList.add(valueArray.get(ii).getAsString());
            }
            val = stringList;
            placeHolder = " (:" + placeHolder + ")";
        }

        paraMap.put(placeHolder,val);
        sql.append(placeHoldeStr).append(" ");
        return sql;

    }

    private FromClauseResult prepareFromClause(List<QueryDataSourceConfig> queryDatasourceConfigs, boolean b) {
        FromClauseResult result = new FromClauseResult();

        List<QueryDataSourceConfig> filteredQueryDateSourceConfigs = b ? (List) queryDatasourceConfigs.stream().filter((config) ->{
            return config.getJoinType().equalsIgnoreCase("from");
        }).collect(Collectors.toList()) : queryDatasourceConfigs;

        StringBuilder sql = new StringBuilder();
        Iterator var6 = filteredQueryDateSourceConfigs.iterator();

        while(var6.hasNext()){
            QueryDataSourceConfig config = (QueryDataSourceConfig) var6.next();

            sql.append(config.getJoinType()).append(" ").append(config.getJoinStr()).append(" ");
            if(config.getJoinType().equalsIgnoreCase("from")){
                result.primaryTable = config.getJoinStr();
            }
        }

        result.fromClause = sql.toString();

        return result;
    }

    private static final class FromClauseResult{
        private String fromClause;
        private String primaryTable;

        public FromClauseResult() {
        }
    }

    private String prepareSelectClause(List<String> selectedFields, Map queryFieldConfigMap) {

        StringBuilder sql = new StringBuilder();

        for(int i =0; i< selectedFields.size() ; ++i){
            String selectedField = (String) selectedFields.get(i);
            QueryFieldConfig config = (QueryFieldConfig) queryFieldConfigMap.get(selectedField);

            if(i < 1)
            {
                sql.append("select");

            }

            if(i > 0){
                sql.append(",");
            }

            if(config.getAggregateFun() != null && !config.getAggregateFun().isEmpty()){
                sql.append(config.getAggregateFun()).append(".").append(config.getTableField());
            }else{
                sql.append(config.getTableName()).append(".").append(config.getTableField());
            }

            sql.append(" as ").append(config.getTableName()).append("__").append(config.getSelectedField());
        }

        sql.append(" ");
        return sql.toString();
    }
}
