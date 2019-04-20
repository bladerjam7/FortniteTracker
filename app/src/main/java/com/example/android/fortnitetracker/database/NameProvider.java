package com.example.android.fortnitetracker.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.android.fortnitetracker.R;

import java.util.HashMap;

public class NameProvider extends ContentProvider {

    static  final String PROVIDE_NAME = "com.example.android.fortnitetracker.database.NameProvider";
    static  final String URL = "content://" + PROVIDE_NAME + "/ftusers";

    public static final Uri CONTENT_URL = Uri.parse(URL);

    // Columns values
    static final String id = "id";
    public static final String name = "name";
    static final int uriCode = 1;

    // Key Value pairs
    private static HashMap<String, String> values;

    // Match our unique uri with our content provider
    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDE_NAME, "ftusers", uriCode);
    }

    private SQLiteDatabase sqlDB;
    static final String DATABASE_NAME = "Usernames";
    static final String TABLE_NAME = "name";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE = "CREATE TABLE " + TABLE_NAME +
            " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " name TEXT NOT NULL);";

    @Override
    public boolean onCreate() {

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        sqlDB = dbHelper.getWritableDatabase();

        if(sqlDB != null){
            return true;
        }
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // Query the table we want to query from
        queryBuilder.setTables(TABLE_NAME);

        switch (uriMatcher.match(uri)){

            case uriCode:
                queryBuilder.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException(getContext().getString(R.string.toast_unknown) + uri);
        }

        Cursor cursor = queryBuilder.query(sqlDB, projection, selection,
                selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    // Handles the type of data in our uri
    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)){

            case uriCode:
                return "vnd.android.cursor.dir/ftusers";
            default:
                throw new IllegalArgumentException(getContext().getString(R.string.toast_unsupported_uri) + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long rowID = sqlDB.insert(TABLE_NAME, null, values);

        if(rowID > 0){
            Uri _uri = ContentUris.withAppendedId(CONTENT_URL, rowID);

            getContext().getContentResolver().notifyChange(_uri, null);

            return _uri;
        } else {

            Toast.makeText(getContext(), getContext().getString(R.string.toast_row_insert_failed), Toast.LENGTH_SHORT).show();
            return null;

        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int rowsDeleted = 0;

        switch (uriMatcher.match(uri)){

            case uriCode:
                rowsDeleted = sqlDB.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException(getContext().getString(R.string.toast_unsupported_uri) + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int rowsUpdated = 0;

        switch (uriMatcher.match(uri)){

            case uriCode:
                rowsUpdated = sqlDB.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException(getContext().getString(R.string.toast_unsupported_uri) + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }



    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super (context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqlDB) {
            sqlDB.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqlDB, int oldVersion, int newVersion) {
            sqlDB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqlDB);
        }
    }
}
