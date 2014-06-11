package com.example.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import static android.database.sqlite.SQLiteDatabase.openDatabase;

/**
 * Created by Mark on 3-6-14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public static final String
            KEY_ID = "_id" ,
            KEY_NAME = "name",
            KEY_WORD = "word",
            KEY_MISTAKES = "mistakes",
            KEY_LENGTH = "length";


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "HangmanDatabase.db", DATABASE_PATH = "/data/data/com.example.app/databases/" ,TABLE_SCORES = "highScores" , TABLE_DICTIONARY = "dictionary";

    Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }



    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDB() throws IOException{

        boolean dbExist = checkDB();

        if(dbExist) {
            //do nothing - database already exist
        }
        else {

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     */
    private boolean checkDB(){

        // try to open the database
        try {

            openDB();

        } catch(SQLiteException e){

            // database doesn't exist yet.
            return false;
        }

        // database does exist
        return true;
    }


    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {

        // Path to the just created empty db
        String outFileName = DATABASE_PATH + DATABASE_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);

        // Transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    @Override
    public synchronized void close() {

        if(db != null)
            db.close();

        super.close();

    }

    public void openDB() throws SQLException {

        // Open the database
        String myPath = DATABASE_PATH + DATABASE_NAME;
        db = openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    public String pickWord(int length) {

        openDB();
        Cursor cursor = db.query(TABLE_DICTIONARY, new String[] { KEY_ID, KEY_WORD }, KEY_LENGTH+"=?", new String[] { String.valueOf(length) }, null, null, null);

        int count = cursor.getCount();

        int index = new Random().nextInt(count);
        cursor.move(index + 1);

        String Word = cursor.getString(cursor.getColumnIndex(KEY_WORD));

        close();

        return Word;

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


            int foundMistakes = cursor.getInt(cursor.getColumnIndex(KEY_MISTAKES));
            int foundID = cursor.getInt(cursor.getColumnIndex(KEY_ID));

            if (mistakes <= foundMistakes || count < 5 ) {

                if (count == 5) {
                    db.delete(TABLE_SCORES, KEY_ID + "=" + foundID, null);
                }
                return true;
            }
        }

        else {
            return true;
        }

        return false;

    }

}
