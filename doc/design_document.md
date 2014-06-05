Hangman – Design Document

List of database tables and fields
==================================

Database containing two tables:

Dictionary:

- id: INT PRIMARY KEY
- word: VARCHAR , contains all the different words the game will be able to pick from
- length: INT, defines the length of all the words

Highscores:

- id: INT PRIMARY KEY
- name: TEXT, contains the name of the user when obtaining a highscore
- word: TEXT, contains the word on wich the user obtained his highscore
- length: INT, defines the length of the word
- mistakes: INT, defines the number of mistakes the user made before guessing the correct word.


List of classes and methods
===========================

MainActivity: Controls the home page.

- public void newgame(View view): Called when the Start button in the view is
clicked. Starts an intent to the gameActivity thus starting a new game.

- public void settings(View view): Called when the Settings button in the view
is clicked. Starts an intent to the settingsActivity where to user can choose the number of turns and the length of the word


DatabaseHandler: sets up and controls all actions that are Database related


- public void createDB() throws IOException : Creates an empty database and overwrites it with the one in the assets folder, after checking if it already exsists.

- private void copyDataBase() throws IOException: function used for copying the database, CreateDB calls upon this function to copy.

- public void onCreate: not used, argument needed for SQL database

- public void onUpgrade: not used, argument needed for SQL database

- public String pickWord: picks a random word from the dictionary

- public SQLiteDatabase insertscore: inserts a highscore in the database



settingsActivity: Controls the settings menu.

- Public void saveSettings(View view): Called when the user presses the save settigns button, thus saving the settings for the next hangman game and starting the game.

gameActivity: Controls the Hangman game

- field variables

    o	String word    

    o	Int turns

    o	String correctLetters

    o	String wrongLetters

- Methods(working)
	- pick a random word from the dictionary
	- show number of turns
	- show picked word    

- Methods(to be implemented)

	- guess a letter and show correct/incorrect
	- show word in underscores and correct when playing
	- end game after word is guess or turns are 0, forward to highscore page
	
