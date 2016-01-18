package com.productions.ortal.familytree.provider;

/**
 * @author user
 * @date 2015-07-12
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbDatabase extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 105;
    // Database Name
    private static final String DATABASE_NAME = "familyTreeDB";

    public DbDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Hotel table
        String CREATE_PERSON_TABLE = "CREATE TABLE IF NOT EXISTS " + DbContract.Tables.TABLE_PERSON + " ( " +
                DbContract.PersonColumns.KEY_ID + " INTEGER ," +
                DbContract.PersonColumns.FIRST_NAME + " STRING , " +
                DbContract.PersonColumns.LAST_NAME + " STRING , " +
                " PRIMARY KEY (" + DbContract.PersonColumns.KEY_ID + ") ) ";

        String CREATE_FAMILY_TABLE = "CREATE TABLE IF NOT EXISTS " + DbContract.Tables.TABLE_FAMILY + " ( " +
                DbContract.FamilyColumns.ID1 + " STRING ," +
                DbContract.FamilyColumns.ID2 + " STRING ," +
                DbContract.FamilyColumns.RELATIONS + " STRING )";


        // create Hotels table
        db.execSQL(CREATE_PERSON_TABLE);
        db.execSQL(CREATE_FAMILY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + DbContract.Tables.TABLE_PERSON);
            db.execSQL("DROP TABLE IF EXISTS " + DbContract.Tables.TABLE_FAMILY);

        }
        this.onCreate(db);
    }

}