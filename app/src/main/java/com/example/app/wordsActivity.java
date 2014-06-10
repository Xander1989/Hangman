package com.example.app;

import android.content.SharedPreferences;

/**
 * Created by Mark on 10-6-14.
 */
public class wordsActivity {

    String word;
    String shownWord;
    int length;

    SharedPreferences settings;
    DatabaseHandler db;

    public static final String PREFS_NAME = "hangmanFile";

    public wordsActivity(){

        length = settings.getInt("word_length", 5);
        word = playedWord(length);
        shownWord = shownWord();

        SharedPreferences.Editor editor = settings.edit();

        editor.putString("word", word);
        editor.putString("shownWord", shownWord);

        editor.commit();

    }


    public String playedWord(int length) {

        // pick a random word with certain length from the database
        String randomWord =  db.pickWord(length).toLowerCase();

        return randomWord;
    }

    public String shownWord() {

        StringBuilder sb = new StringBuilder();

        //  Set letters in the word to underscores.
        for (int i = 0, l = word.length(); i < l; i++) {
            sb.append('_');
        }

        return sb.toString();
    }
}
