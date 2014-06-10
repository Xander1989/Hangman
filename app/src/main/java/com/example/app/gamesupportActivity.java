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
        turns = settings.getInt("turnsleft", 10);
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

        // create an editor for the SharedPreferences object
        SharedPreferences.Editor editor = settings.edit();

        // prevent chosen button to be clicked again
        button.setEnabled(false);

        // if the guessed letter is correct
        if (checkLetter(guessedLetter)) {

            // set button color to green
            button.setBackgroundColor(Color.GREEN);

            shownWord = shownWord(guessedLetter);

            // save guessed letter as correct guessed letter
            correctLetters += guessedLetter;
            editor.putString("correctLetter", correctLetters);
            editor.putString("showWord", shownWord);

        }

        // if the guessed letter is wrong
        else {

            // set button color to red
            button.setBackgroundColor(Color.RED);

            // save guessed letter as wrong guessed letter
            wrongLetters += guessedLetter;
            editor.putString("wrongLetter", wrongLetters);

            // Show new number of turns left
            turns--;
        }



        // save new value of turns left in prefs
        editor.putInt("turns", turns);

        // commit the preferences
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

        // Replace underscore with correct guessed letter.
        int index = word.indexOf(guessedLetter);
        while (index >= 0) {
            sb.setCharAt(index, guessedLetter);
            index = word.indexOf(guessedLetter, index + 1);
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

}
