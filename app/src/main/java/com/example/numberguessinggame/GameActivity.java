package com.example.numberguessinggame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private TextView textViewLast, textViewRight, textViewHint;
    private EditText editTextGuess;
    private Button buttonConfirm;
    boolean twoDigits, threeDigits, fourDigits;
    Random r = new Random();
    int random;
    int remainRight = 10;
    ArrayList<Integer> guessesList = new ArrayList<>();
    int userAttempts = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textViewLast = findViewById(R.id.textViewLast);
        textViewRight =  findViewById(R.id.textViewRight);
        textViewHint = findViewById(R.id.textViewHint);
        editTextGuess = findViewById(R.id.editTextGuess);
        buttonConfirm = findViewById(R.id.buttonConfirm);

        twoDigits = getIntent().getBooleanExtra("two", false);
        threeDigits = getIntent().getBooleanExtra("three", false);
        fourDigits = getIntent().getBooleanExtra("four", false);



        if(twoDigits)
        {
            random = r.nextInt(90) + 10;
        }
        else if(threeDigits)
        {
            random = r.nextInt(900) + 100;
        }
        else if(fourDigits)
        {
            random = r.nextInt(9000) + 1000;
        }

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guess = editTextGuess.getText().toString();
                if(guess.equals(""))
                {
                    Toast.makeText(GameActivity.this, "Please enter a guess", Toast.LENGTH_LONG).show();
                }
                else
                {
                    textViewLast.setVisibility(View.VISIBLE);
                    textViewRight.setVisibility(View.VISIBLE);
                    textViewHint.setVisibility(View.VISIBLE);

                    userAttempts++;
                    remainRight--;
                    int userGuess = Integer.parseInt(guess);
                    guessesList.add(userGuess);

                    textViewLast.setText("Your previous guess was:" + guess);
                    textViewRight.setText("Your remaining guesses are " + remainRight);

                    if(random == userGuess)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Number Guessing Game");
                        builder.setCancelable(false);
                        builder.setMessage("Congratulations. My guess was " + random
                                + "\n\n You guessed my number is " + userAttempts
                                + "attempts \n\n Your guess: " + guessesList
                                + "\n\n Would you like to play again?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();
                    }
                    if(random < userGuess)
                    {
                        textViewHint.setText("Decrease your guess.");
                    }
                    if(random > userGuess)
                    {
                        textViewHint.setText("Increase your guess.");
                    }
                    if(remainRight == 0)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle("Number Guessing Game");
                        builder.setCancelable(false);
                        builder.setMessage("Aww I am sorry, you are out of guesses. My number was " + random
                                + "attempts \n\n Your guess: " + guessesList
                                + "\n\n Would you like to play again?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();
                    }
                    editTextGuess.setText("");
                }
            }
        });
    }
}