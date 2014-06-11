package com.example.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by Mark on 11-6-14.
 */
public class highscoreActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "hangmanFile";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_highscores);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        Intent intentLoose = getIntent();
        String game = intentLoose.getStringExtra("game");
        final TextView textView = (TextView) findViewById(R.id.GameStatus);
        textView.setText(game);

        String word = settings.getString("word", "Null");
        final TextView textView2 = (TextView) findViewById(R.id.CorrectWord);
        textView2.setText("The correct word: " + word);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name", settings.getString("name", "unknown"));
        editor.commit();

    }
}
