package com.example.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
        textView1.setText(word);

        int numberOfTurns = newgame.getTurns();
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText("Turns: " + String.valueOf(numberOfTurns));


    }

    public void GameTurns(View view) {


        Button button = (Button) findViewById(view.getId());

        newgame.guessLetter(button);

        String shownWord = newgame.getShownWord();
        final TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(shownWord.replaceAll(".(?!$)", "$0 "));

        int numberOfTurns = newgame.getTurns();
        final TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText("Turns: " + String.valueOf(numberOfTurns));

        if(numberOfTurns <= 0){
            Intent intentLoose = new Intent(getApplicationContext(), highscoreActivity.class);
            intentLoose.putExtra("game", "Try harder, you lost!" );
            startActivity(intentLoose);
            finish();

        }

        else if(!shownWord.contains("_"))  {
            Intent intentWin = new Intent(getApplicationContext(), highscoreActivity.class);

            DatabaseHandler db = new DatabaseHandler(this);

            int word_length = newgame.getWord().length();
            int numberOfMistakes = newgame.getWrongLetters().length();

                if (db.viewScore(numberOfMistakes, word_length)){
                    Highscore();
                }

                else {
                    intentWin.putExtra("game", "Good job, you won!");
                    startActivity(intentWin);
                    finish();
                }
            }

    }

    public void Highscore(){

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("HighScore");
        alert.setMessage("Congratulations you got a new HighScore");

        final EditText input = new EditText(this);
        alert.setView(input);
        final String name = settings.getString("name", "Blank");
        input.setText(name);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String user_name = input.getText().toString();

                SharedPreferences.Editor editor = settings.edit();
                editor.putString("name", user_name);
                editor.commit();

                newgame.insertScore(user_name);

                Intent intentWin = new Intent(getApplicationContext(), highscoreActivity.class);
                intentWin.putExtra("game", "Good job, you won!");
                startActivity(intentWin);
                finish();

            }
        });

        alert.show();
    }
}
