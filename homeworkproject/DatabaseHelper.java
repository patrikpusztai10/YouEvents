package com.example.homeworkproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "EventApps";
    private static final int DB_VERSION = 1;
    private int ROLE_REPRESENTATIVE=1;


    // Users table
    private static final String USERS_TABLE_NAME = "users";
    private static final String USERS_ID = "id";
    private static final String USERS_ROLE = "role";
    //0-attendee 1-event rep.
    private static final String USERS_UNAME = "username";
    private static final String USERS_EMAIL = "email";

    private static final String USERS_PASSWORD = "password";
    private static final String USERS_ORGANIZATION="organization";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + USERS_TABLE_NAME + " ("
                + USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERS_ROLE + " INTEGER NOT NULL CHECK(" + USERS_ROLE + " IN (0, 1)), "
                + USERS_EMAIL + " TEXT,"
                + USERS_UNAME + " TEXT,"
                + USERS_PASSWORD + " TEXT,"
                + USERS_ORGANIZATION + " TEXT DEFAULT NULL)";
        db.execSQL(query);
    }
    public void register(String username, String email, String password, int role, String organization) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(String.valueOf(USERS_ROLE), role);
        values.put(USERS_EMAIL, email);
        values.put(USERS_UNAME, username);
        values.put(USERS_PASSWORD, password);
        if (role == ROLE_REPRESENTATIVE) {
            values.put(USERS_ORGANIZATION, organization);
        }
        db.insert(USERS_TABLE_NAME, null, values);
        db.close();
    }
    public int login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT role FROM users WHERE username = ? AND password = ?",
                new String[]{username, password}
        );

        if (cursor.moveToFirst()) {
            int role = cursor.getInt(0); // Assuming 0 = attendee, 1 = representative
            cursor.close();
            return role;
        } else {
            cursor.close();
            return -1; // Unsuccessful login
        }
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        if (cursor.getCount() > 0)
        {
            return true;
        }
        return false;
    }
    public void updatePassword(String username, String new_password) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + USERS_TABLE_NAME + " SET password=? WHERE username=? AND password!=?",
                new String[]{new_password, username, new_password});
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        onCreate(db);
    }
}