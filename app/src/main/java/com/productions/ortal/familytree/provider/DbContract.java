package com.productions.ortal.familytree.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import java.util.List;

/**
 * @author user
 * @date 2015-07-13
 */
public class DbContract {
    public static final String CONTENT_AUTHORITY = "com.productions.ortal.familytree.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    private static final String PATH_PERSON = "person";
    private static final String PATH_RELATIONS = "relations";

    interface Tables {
        String TABLE_PERSON = "tbl_person";
        String TABLE_RELATIONS = "tbl_relations";
    }

    public interface PersonColumns {
        String KEY_ID = "_id";
        String FIRST_NAME = "first_name";
        String LAST_NAME = "last_name";
    }

    public interface RelationsColumns {
        String ID1 = "id1";
        String ID2 = "id2";
        String RELATIVE = "relative";

    }

    public static class Person implements PersonColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PERSON).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/int";

        public static Uri buildPersonUri(String id,String firstName, String lastNAme) {
            return CONTENT_URI.buildUpon().appendPath(id).appendPath(firstName).appendPath(lastNAme).build();
        }

        public static String getHotelId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class Relations implements RelationsColumns, BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RELATIONS).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/int";

        public static Uri buildSearchHistoryUri(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static List<String> getSearchHistory(Uri uri) {
            return uri.getPathSegments();
        }
    }


}
