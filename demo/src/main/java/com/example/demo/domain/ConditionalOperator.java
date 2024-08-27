package com.example.demo.domain;

public enum ConditionalOperator {

    EQUAL("eq","="),
    NOT_EQUAL("ne","!="),
    GREATER_THAN("gt",">"),
    LESSER_THAN("lt","<"),
    GREATER_THAN_OR_EQUAL("gte",">="),
    LESSER_THAN_OR_EQUAL("lte","<="),
    IN("in","IN"),
    LIKE("like","LIKE");


    private final String name;
    private final String value;

    ConditionalOperator(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public  static ConditionalOperator fromName(String name){

        ConditionalOperator[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0;var3 < var2;++var3){
            ConditionalOperator op = var1[var3];

            if(op.getName().equalsIgnoreCase(name)){
                return op;
            }
        }

        throw new RuntimeException("Invalid Conditional operator");
    }
}
