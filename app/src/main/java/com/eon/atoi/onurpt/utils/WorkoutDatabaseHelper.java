package com.eon.atoi.onurpt.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.eon.atoi.onurpt.POJOs.Event;
import com.eon.atoi.onurpt.POJOs.Workout;

import java.util.ArrayList;

/**
 * Created by Atoi on 7.12.2017.
 */

public class WorkoutDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "workoutdb";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_WORKOUT = "workout";

    private static final String KEY_WORKOUT_ID = "id";
    private static final String KEY_WORKOUT_NAME = "workoutName";
    private static final String KEY_WORKOUT_PICTURE = "workoutPicture";
    private static final String KEY_WORKOUT_DESCRIPTION = "workoutDescription";
    private static final String KEY_WORKOUT_SET = "workoutSet";
    private static final String KEY_WORKOUT_REPS = "workoutReps";

    private static final String TABLE_EVENT = "event";

    private static final String KEY_EVENT_ID = "id";
    private static final String KEY_EVENT_NAME = "eventName";
    private static final String KEY_EVENT_TIME = "eventTime";

    private static WorkoutDatabaseHelper sInstance;

    public static synchronized WorkoutDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new WorkoutDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private WorkoutDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORKOUT_TABLE = "CREATE TABLE " + TABLE_WORKOUT +
                "(" +
                KEY_WORKOUT_ID + " INTEGER PRIMARY KEY," +
                KEY_WORKOUT_NAME + " TEXT unique," +
                KEY_WORKOUT_DESCRIPTION + " TEXT," +
                KEY_WORKOUT_SET + " TEXT," +
                KEY_WORKOUT_REPS + " TEXT," +
                KEY_WORKOUT_PICTURE + " TEXT" +
                ")";


        String CREATE_EVENT_TABLE = "CREATE TABLE " + TABLE_EVENT +
                "(" +
                KEY_EVENT_ID + " INTEGER PRIMARY KEY," +
                KEY_EVENT_NAME + " TEXT," +
                KEY_EVENT_TIME + " TEXT" +
                ")";

        db.execSQL(CREATE_WORKOUT_TABLE);
        db.execSQL(CREATE_EVENT_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT);
            onCreate(db);
        }
    }

    public void addWorkout(Workout workout) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_WORKOUT_NAME, workout.getWorkoutName());
            values.put(KEY_WORKOUT_DESCRIPTION, workout.getDescription());
            values.put(KEY_WORKOUT_SET, workout.getSet()+"");
            values.put(KEY_WORKOUT_REPS, workout.getReps()+"");
            values.put(KEY_WORKOUT_PICTURE, workout.getThumbnail()+"");

            db.insertOrThrow(TABLE_WORKOUT, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("DATABASE_ADD", "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<Workout> getAllWorkouts() {
        ArrayList<Workout> workoutList = new ArrayList<>();

        String WORKOUT_SELECT_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_WORKOUT);


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(WORKOUT_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Workout workout = new Workout();
                    workout.setWorkoutName(cursor.getString(cursor.getColumnIndex(KEY_WORKOUT_NAME)));
                    workout.setDescription(cursor.getString(cursor.getColumnIndex(KEY_WORKOUT_DESCRIPTION)));
                    workout.setSet((cursor.getString(cursor.getColumnIndex(KEY_WORKOUT_SET))));
                    workout.setReps((cursor.getString(cursor.getColumnIndex(KEY_WORKOUT_REPS))));
                    workout.setThumbnail((cursor.getString(cursor.getColumnIndex(KEY_WORKOUT_PICTURE))));

                    workoutList.add(workout);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("GETWORKOUTS", "Error while trying to get workouts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return workoutList;
    }

    public void deleteWorkout(String workoutName)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_WORKOUT, KEY_WORKOUT_NAME + " = '" + workoutName + "'", null);
        db.close();
    }

    public void addEvent(Event event)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_EVENT_NAME, event.getmName());
            values.put(KEY_EVENT_TIME, event.getmStartTime());
            Log.d("string", event.getmStartTime());

            db.insertOrThrow(TABLE_EVENT, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("DATABASE_ADD", "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> eventList = new ArrayList<>();

        String EVENT_SELECT_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_EVENT);


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(EVENT_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Event event = new Event();
                    event.setmName(cursor.getString(cursor.getColumnIndex(KEY_EVENT_NAME)));
                    event.setmStartTime(cursor.getString(cursor.getColumnIndex(KEY_EVENT_TIME)));
                    eventList.add(event);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("GETWORKOUTS", "Error while trying to get workouts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return eventList;
    }
}
