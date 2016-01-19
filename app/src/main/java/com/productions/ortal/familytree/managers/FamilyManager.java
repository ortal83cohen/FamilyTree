package com.productions.ortal.familytree.managers;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.productions.ortal.familytree.models.Person;
import com.productions.ortal.familytree.models.Relation;
import com.productions.ortal.familytree.provider.DbContract;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 1/18/2016.
 */
public class FamilyManager {
    Context mContext;
    private Object persons;

    public FamilyManager(Context mContext) {
        this.mContext = mContext;
    }

    public void addFamilyMember(Person person,String relative,int referenceToPerson) {
        ContentValues values = new ContentValues();
        values.put(DbContract.PersonColumns.FIRST_NAME, person.getFirstName());
        values.put(DbContract.PersonColumns.LAST_NAME, person.getLastName());
        Uri uri = mContext.getContentResolver().insert(DbContract.Person.CONTENT_URI, values);
        long id = ContentUris.parseId(uri);
        if(referenceToPerson !=0) {//if this is not the first person
             values = new ContentValues();
            values.put(DbContract.RelationsColumns.ID1, referenceToPerson);
            values.put(DbContract.RelationsColumns.ID2, id);
            values.put(DbContract.RelationsColumns.RELATIVE, relative);
           mContext.getContentResolver().insert(DbContract.Relations.CONTENT_URI, values);
        }
    }

    public  HashMap<String,Person> getPersons() {
        HashMap<String,Person> persons = new HashMap<>();
        Cursor cursor = mContext.getContentResolver().query(DbContract.Person.CONTENT_URI.buildUpon().build(), null, null, null, null);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                persons.put(cursor.getString(cursor.getColumnIndex(DbContract.PersonColumns.KEY_ID)), new Person(cursor.getString(cursor.getColumnIndex(DbContract.PersonColumns.FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndex(DbContract.PersonColumns.LAST_NAME)), cursor.getInt(cursor.getColumnIndex(DbContract.PersonColumns.KEY_ID))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return persons;
    }

    public HashMap<String,Relation> getRelations() {
        HashMap<String,Relation> relations = new HashMap();
        Cursor cursor = mContext.getContentResolver().query(DbContract.Relations.CONTENT_URI.buildUpon().build(), null, null, null, null);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                relations.put(cursor.getString(cursor.getColumnIndex(DbContract.RelationsColumns.ID2)),new Relation(cursor.getString(cursor.getColumnIndex(DbContract.RelationsColumns.ID1)),
                        cursor.getString(cursor.getColumnIndex(DbContract.RelationsColumns.ID2)), cursor.getString(cursor.getColumnIndex(DbContract.RelationsColumns.RELATIVE))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return relations;
    }


}
