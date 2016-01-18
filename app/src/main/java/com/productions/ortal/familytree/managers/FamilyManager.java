package com.productions.ortal.familytree.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.productions.ortal.familytree.models.Person;
import com.productions.ortal.familytree.provider.DbContract;

import java.util.ArrayList;

/**
 * Created by user on 1/18/2016.
 */
public class FamilyManager {
    Context mContext;
    private Object persons;

    public FamilyManager(Context mContext) {
        this.mContext = mContext;
    }

    public void addFamilyMember(Person person) {
        ContentValues values = new ContentValues();
        values.put(DbContract.PersonColumns.FIRST_NAME, person.getFirstName());
        values.put(DbContract.PersonColumns.LAST_NAME, person.getLastName());
        mContext.getContentResolver().insert(DbContract.Person.CONTENT_URI, values);
    }

    public ArrayList<Person> getPersons() {
        ArrayList<Person> persons = new ArrayList();
        Cursor cursor = mContext.getContentResolver().query(DbContract.Person.CONTENT_URI.buildUpon().build(), null, null, null, null);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                persons.add(new Person(cursor.getString(cursor.getColumnIndex(DbContract.PersonColumns.FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndex(DbContract.PersonColumns.LAST_NAME))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return persons;
    }
}
