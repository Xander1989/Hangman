package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Mark on 3-6-14.
 */
public class gameActivity extends ActionBarActivity {


      DatabaseHandler db = new DatabaseHandler(this);
      int length = 5;
      int turns = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_game);

        Intent intent = getIntent();
        length = intent.getIntExtra("length", 5);
        turns = intent.getIntExtra("turns", 10);


        String word = db.pickWord(length).toLowerCase();
        TextView textView1 = (TextView) findViewById(R.id.textView);
        textView1.setText(word.replaceAll(".(?!$)", "$0 "));

        String numberOfTurns = "Turns: " + turns ;
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText(numberOfTurns);


    }


}
