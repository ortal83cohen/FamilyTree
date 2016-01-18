package com.productions.ortal.familytree.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author user
 * @date 2015-07-13
 */
public class DbProvider extends ContentProvider {
    private static final int PERSON = 100;
    private static final int FAMILY = 200;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DbDatabase mOpenHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DbContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, "person", PERSON);
        matcher.addURI(authority, "family", FAMILY);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DbDatabase(getContext());
        return true;
    }

    private void deleteDatabase() {
        mOpenHelper.close();
        Context context = getContext();
        DbDatabase.deleteDatabase(context);
        mOpenHelper = new DbDatabase(getContext());
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PERSON:
                return DbContract.Person.CONTENT_TYPE;
            case FAMILY:
                return DbContract.Family.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);
        final DbSelectionBuilder builder = new DbSelectionBuilder();
        switch (match) {
            case PERSON:
                builder.table(DbContract.Tables.TABLE_PERSON);
                if (uri.getQueryParameter("group by") != null) {
                    return builder.groupBy(uri.getQueryParameter("group by")).query(db, false, getFavoritesGroupByColumns(), "", "");
                } else if (uri.getQueryParameter("where") != null) {
                    return builder.where(uri.getQueryParameter("where")).query(db, false, projection, "", "");
                } else {
                    return builder.query(db, false, projection, "", "");
                }

            case FAMILY:
                final String hotelId = DbContract.Person.getHotelId(uri);
                builder.table(DbContract.Tables.TABLE_FAMILY);
                return builder.query(db, false, projection, "", "");
            default: {
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
            }
        }


    }

    private String[] getSearchHistoryColumns() {
        String[] columns = new String[2];
        columns[0] = "rowid _id";
        columns[1] = "*";

        return columns;
    }

    private String[] getFavoritesGroupByColumns() {
        String[] columns = new String[2];
        columns[0] = "count(*) as count";
        columns[1] = "*";

        return columns;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PERSON:
                db.insert(DbContract.Tables.TABLE_PERSON, null, values);
                return DbContract.Person.buildPersonUri(values.getAsString(DbContract.PersonColumns.FIRST_NAME),
                        values.getAsString(DbContract.PersonColumns.LAST_NAME));
            case FAMILY:
                db.insert(DbContract.Tables.TABLE_FAMILY, null, values);
                return DbContract.Family.buildSearchHistoryUri(values.getAsString(DbContract.FamilyColumns.ID));
            default: {
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
            }
        }
    }

//    @Override
//    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
//        final DbSelectionBuilder builder = new DbSelectionBuilder();
//        final int match = sUriMatcher.match(uri);
//        return builder.where(selection, selectionArgs).update(db, values);
//    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        if (uri.equals(DbContract.BASE_CONTENT_URI)) {
            deleteDatabase();
            return 1;
        }
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final DbSelectionBuilder builder = new DbSelectionBuilder();
        final int match = sUriMatcher.match(uri);
        if (match == PERSON) {
            builder.table(DbContract.Tables.TABLE_PERSON).where(selection);
        }
        if (match == FAMILY) {
            builder.table(DbContract.Tables.TABLE_FAMILY).where(selection);
        }
        return builder.where(selection, selectionArgs).delete(db);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            final int numOperations = operations.size();
            final ContentProviderResult[] results = new ContentProviderResult[numOperations];
            for (int i = 0; i < numOperations; i++) {
                results[i] = operations.get(i).apply(this, results, i);
            }
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }


}
