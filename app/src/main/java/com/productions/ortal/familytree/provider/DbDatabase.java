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
    private static final int DATABASE_VERSION = 109;
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
                DbContract.PersonColumns.KEY_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT," +
                DbContract.PersonColumns.FIRST_NAME + " STRING , " +
                DbContract.PersonColumns.LAST_NAME + " STRING ) " ;

        String CREATE_RELATIONS_TABLE = "CREATE TABLE IF NOT EXISTS " + DbContract.Tables.TABLE_RELATIONS + " ( " +
                DbContract.RelationsColumns.ID1 + " STRING ," +
                DbContract.RelationsColumns.ID2 + " STRING ," +
                DbContract.RelationsColumns.RELATIVE + " STRING )";


        // create Hotels table
        db.execSQL(CREATE_PERSON_TABLE);
        db.execSQL(CREATE_RELATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + DbContract.Tables.TABLE_PERSON);
            db.execSQL("DROP TABLE IF EXISTS " + DbContract.Tables.TABLE_RELATIONS);

        }
        this.onCreate(db);
    }

}