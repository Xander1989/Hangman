package com.example.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.content.Intent;
import android.widget.TextView;

/**
 * Created by Mark on 3-6-14.
 */
public class settingsActivity extends ActionBarActivity {

    EditText text1;
    EditText text2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);

        text1= (EditText) findViewById(R.id.turns);
        text2= (EditText) findViewById(R.id.wordlength);


    }

    public void saveSettings(View v){

       Intent intent = new Intent(this, gameActivity.class);
        intent.putExtra("turns", Integer.parseInt(text1.getText().toString()));
        intent.putExtra("length", Integer.parseInt(text2.getText().toString()));
       startActivity(intent);

    }


}
