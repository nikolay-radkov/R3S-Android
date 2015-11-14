package com.nikolay.r3s.constants.database;

public class SubscriptionsTableConstants {
    public final static String TABLE_NAME = "SubscriptionsTable";
    public final static String COLUMN_NAME_ID = "id";
    public final static String COLUMN_NAME_FAVICON = "favicon";
    public final static String COLUMN_NAME_NAME = "name";
    public final static String COLUMN_NAME_URL = "url";
    public final static String COLUMN_NAME_UPDATED_AT = "updated_at";
    public final static String COLUMN_NAME_DESCRIPTION = "description";
    public final static String COLUMN_NAME_RSS = "rss";


    public final static String CREATE_TABLE = DbConstants.CREATE_TABLE + TABLE_NAME + " ("
            + COLUMN_NAME_ID + DbConstants.INTEGER_TYPE + DbConstants.PRIMARY_KEY + DbConstants.COMMA_SEP
            + COLUMN_NAME_FAVICON + DbConstants.TEXT_TYPE + DbConstants.COMMA_SEP
            + COLUMN_NAME_NAME + DbConstants.TEXT_TYPE + DbConstants.COMMA_SEP
            + COLUMN_NAME_URL + DbConstants.TEXT_TYPE + DbConstants.COMMA_SEP
            + COLUMN_NAME_UPDATED_AT + DbConstants.TEXT_TYPE + DbConstants.COMMA_SEP
            + COLUMN_NAME_RSS + DbConstants.TEXT_TYPE + DbConstants.COMMA_SEP
            + COLUMN_NAME_DESCRIPTION + DbConstants.TEXT_TYPE
            + ")";

    public final static String DROP_TABLE = DbConstants.DROP_TABLE + TABLE_NAME;
    public final static String GET_DATA = DbConstants.SELECT_ALL_QUERY + TABLE_NAME;
}