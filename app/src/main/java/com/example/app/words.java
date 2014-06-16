package com.example.app;

import android.content.SharedPreferences;

/**
 * Created by Mark on 10-6-14.
 */
public class words {


    String word;
    DatabaseHandler db;
    int length;

    public static final String PREFS_NAME = "hangmanFile";
    SharedPreferences settings;

    public words(SharedPreferences a, DatabaseHandler b){

        this.settings = a;
        this.db = b;

        length = settings.getInt("word_length", 5);
        word = playedWord(length);


        SharedPreferences.Editor editor = settings.edit();

        editor.putString("word", word);

        editor.commit();

    }


    public String playedWord(int length) {

        // pick a random word with certain length from the database
        String randomWord =  db.pickWord(length).toLowerCase();

        return randomWord;
    }

}
