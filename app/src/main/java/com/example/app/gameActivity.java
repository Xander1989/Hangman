package com.example.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Mark on 3-6-14.
 */
public class gameActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "hangmanFile";

      gamesupportActivity newgame;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_game);

        DatabaseHandler db = new DatabaseHandler(this);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        newgame = new gamesupportActivity(settings, db);

        String word = newgame.getShownWord();
        TextView textView1 = (TextView) findViewById(R.id.textView);
        textView1.setText(word.replaceAll(".(?!$)", "$0 "));

        int numberOfTurns = newgame.getTurns();
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText("Turns: " + String.valueOf(numberOfTurns));


    }

    public void checkLetter(View view) {

        // find clicked button
        Button button = (Button) findViewById(view.getId());

        newgame.guessLetter(button);

        // update show word in the view
        String showWord = newgame.getShownWord();
        final TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(showWord.replaceAll(".(?!$)", "$0 "));

        // update turns left in the view
        int numberOfTurns = newgame.getTurns();
        final TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText("Turns: " + String.valueOf(numberOfTurns));
    }
}
