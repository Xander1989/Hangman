package com.example.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Mark on 3-6-14.
 */
public class settingsActivity extends ActionBarActivity {

    public final String PREFS_NAME = "hangmanFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        final SharedPreferences.Editor editor = settings.edit();

        Button btn = (Button)findViewById(R.id.button_settings);
        final SeekBar turns = (SeekBar)findViewById(R.id.seekBar_turns);
        final SeekBar word_length = (SeekBar)findViewById(R.id.seekBar_words);
        final TextView text_turns = (TextView)findViewById(R.id.textView);
        final TextView text_length = (TextView)findViewById(R.id.textView2);

        int progress1 = settings.getInt("MaximumTurns", 10);
        text_turns.setText("Number of Turns: " + progress1);
        turns.setProgress(progress1);

        int progress2 = settings.getInt("word_length", 5);
        text_length.setText("Length of word: " + progress2);
        word_length.setProgress(progress2);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("MaximumTurns", turns.getProgress());
                editor.putInt("word_length", word_length.getProgress());

                editor.commit();

                Intent intent = new Intent(getApplicationContext(), gameActivity.class);
                startActivity(intent);

            }
        });

    }




}
