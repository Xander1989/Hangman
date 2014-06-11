package com.example.app;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.Button;

/**
 * Created by Mark on 10-6-14.
 */
public class gamesupportActivity {


    public static final String PREFS_NAME = "hangmanFile";

        String word;
        int length;
        int turns;
        String shownWord;
        String correctLetters= "";
        String wrongLetters= "";

        SharedPreferences settings;
        DatabaseHandler db;

    public gamesupportActivity(SharedPreferences a, DatabaseHandler b){

        settings = a;
        db = b;

        length = settings.getInt("word_length", 5);
        turns = settings.getInt("MaximumTurns", 10);
        word = playedWord(length);
        shownWord = shownWord();

        SharedPreferences.Editor editor = settings.edit();

        editor.putString("word", word);
        editor.putString("shownWord", shownWord);
        editor.putInt("turns", turns);
        editor.putString("correctLetter", "");
        editor.putString("wrongLetter", "");
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

    public void guessLetter(Button button) {


        char guessedLetter = button.getText().toString().charAt(0);


        SharedPreferences.Editor editor = settings.edit();

        button.setEnabled(false);

        if (checkLetter(guessedLetter)) {

            button.setBackgroundColor(Color.GREEN);

            shownWord = shownWord(guessedLetter);

            correctLetters += guessedLetter;
            editor.putString("correctLetter", correctLetters);
            editor.putString("showWord", shownWord);

        }


        else {

            button.setBackgroundColor(Color.RED);

            wrongLetters += guessedLetter;
            editor.putString("wrongLetter", wrongLetters);

            turns--;
        }




        editor.putInt("turns", turns);
        editor.commit();

    }

    public boolean checkLetter(char guessedLetter) {

        if (word.indexOf(guessedLetter) >= 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public String shownWord(char guessedLetter) {

        StringBuilder sb = new StringBuilder();

        sb.append(shownWord);

        int wordcount = word.indexOf(guessedLetter);
        while (wordcount >= 0) {
            sb.setCharAt(wordcount, guessedLetter);
            wordcount = word.indexOf(guessedLetter, wordcount + 1);
        }

        return sb.toString();
    }

    public String getWord() {
        return word;
    }

    public String getShownWord(){
        return shownWord;
    }

    public int getTurns() {
        return turns;
    }

    public String getCorrectLetters() {
        return correctLetters;
    }

    public String getWrongLetters() {
        return wrongLetters;
    }

    public void insertScore(String name) {

        int mistakes = wrongLetters.length();

        // save the new high score in the database
        db.insertscore(name, word, mistakes, length);
    }

}
