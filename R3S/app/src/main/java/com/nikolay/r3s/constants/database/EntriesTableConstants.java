package com.nikolay.r3s.constants.database;

public class EntriesTableConstants {
    public final static String TABLE_NAME = "EntriesTable";
    public final static String COLUMN_NAME_ID = "id";
    public final static String COLUMN_NAME_TITLE = "title";
    public final static String COLUMN_NAME_CREATED_AT = "crated_at";
    public final static String COLUMN_NAME_CONTENT = "content";
    public final static String COLUMN_NAME_SUBSCRIPTION_ID = "subscriptionId";
    public final static String COLUMN_NAME_LINK = "link";

    public final static String CREATE_TABLE = DbConstants.CREATE_TABLE + TABLE_NAME + " ("
            + COLUMN_NAME_ID + DbConstants.INTEGER_TYPE + DbConstants.PRIMARY_KEY + DbConstants.COMMA_SEP
            + COLUMN_NAME_TITLE + DbConstants.TEXT_TYPE + DbConstants.COMMA_SEP
            + COLUMN_NAME_CREATED_AT + DbConstants.TEXT_TYPE + DbConstants.COMMA_SEP
            + COLUMN_NAME_CONTENT + DbConstants.TEXT_TYPE + DbConstants.COMMA_SEP
            + COLUMN_NAME_SUBSCRIPTION_ID + DbConstants.INTEGER_TYPE + DbConstants.COMMA_SEP
            + COLUMN_NAME_LINK + DbConstants.TEXT_TYPE
            + " )";

    public final static String DROP_TABLE = DbConstants.DROP_TABLE + TABLE_NAME;
    public final static String GET_DATA = DbConstants.SELECT_ALL_QUERY + TABLE_NAME;
    public final static String WHERE_CLAUSE_ID = DbConstants.WHERE_CLAUSE + COLUMN_NAME_ID + DbConstants.EQUAL_EXPRESION;
    public final static String WHERE_CLAUSE_SUBSCRIPTION_ID =  DbConstants.WHERE_CLAUSE + COLUMN_NAME_SUBSCRIPTION_ID + DbConstants.EQUAL_EXPRESION;
}
