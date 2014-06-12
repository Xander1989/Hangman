package com.example.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
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

        int length = word.length();
        populateListViewFromDB(length);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name", settings.getString("name", "unknown"));
        editor.commit();

    }

    public void populateListViewFromDB(int length){

        DatabaseHandler db = new DatabaseHandler(this);

        Cursor cursor = db.getScores(length);

        String[] fromFieldIDs = new String[]
                {db.KEY_ID, db.KEY_NAME, db.KEY_WORD, db.KEY_MISTAKES};
        int[] toViewIDs = new int[]
                {R.id.IdView , R.id.NameView, R.id.WordView , R.id.MistakesView};


        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_layout, cursor, fromFieldIDs, toViewIDs);

        ListView idlist = (ListView) findViewById(R.id.listView);
        idlist.setAdapter(myCursorAdapter);



    }
}
