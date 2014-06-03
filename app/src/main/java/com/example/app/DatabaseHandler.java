package com.example.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mark on 3-6-14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String
            KEY_ID = "_id" ,
            KEY_NAME = "name",
            KEY_WORD = "word",
            KEY_MISTAKES = "mistakes",
            KEY_LENGTH = "length";


    private static final int DATABASE_VERSION = 1;
    private static final String DATBASE_NAME = "HangmanDatabase", TABLE_SCORES = "highscores" , TABLE_DICTIONARY = "dictionary";

    public DatabaseHandler(Context context) {
        super(context, DATBASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE" + TABLE_SCORES + "(" + KEY_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + "TEXT" + KEY_WORD + "TEXT" + KEY_MISTAKES + "INTEGER" + KEY_LENGTH + "INTEGER");
        db.execSQL("CREATE TABLE" + TABLE_DICTIONARY + "(" + KEY_ID + "INTEGER PRIMARY KEY," + KEY_WORD + "VARCHAR" + KEY_LENGTH + "INTEGER");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL("DROP TABLE IF EXISTS" + TABLE_SCORES + TABLE_DICTIONARY);

        onCreate(db);
    }

    public SQLiteDatabase insertscore(String name, String word, int mistakes, int length) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, name);
        values.put(KEY_WORD, word);
        values.put(KEY_MISTAKES, mistakes);
        values.put(KEY_LENGTH, length);

        db.insert(TABLE_SCORES, null, values);
        return db;
    }

    public Cursor getScores(int id){
       SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_SCORES, new String[] {KEY_ID, KEY_NAME, KEY_WORD, KEY_MISTAKES}, KEY_LENGTH + "=?", new String[] {String.valueOf(id)}, null, null ,KEY_MISTAKES);

        return cursor;
    }

    public boolean viewScore(int mistakes, int length) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = getScores(length);

        int count = cursor.getCount();

        if (count != 0) {

            cursor.moveToLast();

            // find number of mistakes of last score in the high score table
            int foundMistakes = cursor.getInt(cursor.getColumnIndex(KEY_MISTAKES));
            int foundID = cursor.getInt(cursor.getColumnIndex(KEY_ID));

            // if last played game had less mistakes or high score table is not full
            if (mistakes <= foundMistakes || count < 5 ) {

                // if table is full, delete lowest score in the high score table
                if (count == 5) {
                    db.delete(TABLE_SCORES, KEY_ID + "=" + foundID, null);
                }

                return true;
            }
        }

        // if the high score table of certain length is empty
        else {
            return true;
        }

        return false;
    }
}
