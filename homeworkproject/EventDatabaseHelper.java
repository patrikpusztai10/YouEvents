package com.example.homeworkproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class EventDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "EventApps";
    private static final int DB_VERSION = 1;


    // Users table
    private static final String EVENTS_TABLE_NAME = "events";
    private static final String EVENTS_UID = "id";
    private static final String EVENTS_NAME = "name";
    private static final String EVENTS_USERS_NAME = "username";

    //0-attendee 1-event rep.
    private static final String EVENTS_LOCATION = "location";
    private static final String EVENTS_DATE = "date";

    private static final String EVENTS_DESCRIPTION = "description";
    private static final String EVENTS_IMAGE="image";
    private static final String EVENTS_NR_PEOPLE_INTERESTED="interest";


    public EventDatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = getWritableDatabase();
        ensureEventsTableExists(db);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + EVENTS_TABLE_NAME + " ("
                + EVENTS_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EVENTS_USERS_NAME + " TEXT,"
                + EVENTS_NAME + " TEXT,"
                + EVENTS_DATE + " DATE,"
                + EVENTS_LOCATION + " TEXT,"
                + EVENTS_DESCRIPTION + " TEXT,"
                + EVENTS_IMAGE + " TEXT,"
                + EVENTS_NR_PEOPLE_INTERESTED+" INTEGER DEFAULT 0);";
        db.execSQL(query);
    }
    public void deleteAllEvents() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EVENTS_TABLE_NAME, null, null); // Delete all rows
        db.close();
    }

    public void addEvent(String name, String username, String date, String location, String description, String image_path) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EVENTS_NAME,name);
        values.put(EVENTS_USERS_NAME,username);
        values.put(EVENTS_DATE, date);
        values.put(EVENTS_LOCATION, location);
        values.put(EVENTS_DESCRIPTION, description);
        values.put(EVENTS_IMAGE,image_path);
        values.put(EVENTS_NR_PEOPLE_INTERESTED, 0);
        db.insert(EVENTS_TABLE_NAME, null, values);
        db.close();
    }
    //The schema won't be created otherwise
    private void ensureEventsTableExists(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name=?",
                new String[]{EVENTS_TABLE_NAME}
        );
        boolean tableExists = cursor.moveToFirst();
        cursor.close();

        if (!tableExists) {
            String createTableQuery = "CREATE TABLE " + EVENTS_TABLE_NAME + " ("
                    + EVENTS_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + EVENTS_USERS_NAME + " TEXT,"
                    + EVENTS_NAME + " TEXT,"
                    + EVENTS_DATE + " DATE,"
                    + EVENTS_LOCATION + " TEXT,"
                    + EVENTS_DESCRIPTION + " TEXT,"
                    + EVENTS_IMAGE + " TEXT,"
                    + EVENTS_NR_PEOPLE_INTERESTED + " INTEGER DEFAULT 0);";
            db.execSQL(createTableQuery);
        }
    }

    public ArrayList<Event> readEventsforReps(String username)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT name,image, interest FROM "+EVENTS_TABLE_NAME+" WHERE username=?", new String[]{username});
        ArrayList<Event> data= new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do
            {
                String event_title = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                int interest= cursor.getInt(cursor.getColumnIndexOrThrow("interest"));
                data.add(new Event(event_title, image,interest));
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return data;
    }
    public void incrementInterest(String eventname)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("UPDATE "+EVENTS_TABLE_NAME+" SET interest=interest+1 WHERE name=?", new String[]{eventname});

    }
    public void decrementInterest(String eventname)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("UPDATE "+EVENTS_TABLE_NAME+" SET interest=interest-1 WHERE interest>0 AND name=?", new String[]{eventname});

    }
    public ArrayList<Event> readEventsforAtt()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT name,date,location,description,image FROM "+EVENTS_TABLE_NAME,null);
        ArrayList<Event> data= new ArrayList();
        if(cursor.moveToFirst())
        {
            do
            {
                String event_title = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String event_location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
                String event_date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String event_description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                data.add(new Event(event_title, event_date, event_location, event_description, image));
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return data;
    }
    public ArrayList<Event> readEventsSearchQuery(String query)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT name,date,location,description,image FROM "+EVENTS_TABLE_NAME+" WHERE name = ? or location = ?",new String[]{query,query});
        ArrayList<Event> data= new ArrayList();
        if(cursor.moveToFirst())
        {
            do
            {
                String event_title = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String event_location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
                String event_date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String event_description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                data.add(new Event(event_title, event_date, event_location, event_description, image));
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return data;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EVENTS_TABLE_NAME);
        onCreate(db);
    }
}