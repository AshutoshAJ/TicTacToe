package com.ashutoshjena.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int[][] winStates = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    int[] currentState;

    TextView player1, player2, infoText;

    CardView player1Card, player2Card;

    // 0 -> P1, 1 -> P2
    int currentPlayer = 0;

    StringBuilder winner;

    static final String player1String = "P1";
    static final String player2String = "P2";

    boolean gameWon = false;

    int totalCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1Card = findViewById(R.id.player1Card);
        player2Card = findViewById(R.id.player2Card);

        player1 = findViewById(R.id.textView_player1);
        player2 = findViewById(R.id.textView_player2);

        infoText = findViewById(R.id.textView_info);

        newGame();
    }

    public void checkState(View view) {

        Button button = (Button) view;

        int buttonChecked = Integer.parseInt(view.getTag().toString());

        // Avoids Over-Writing
        if (currentState[buttonChecked] == -1) {

            if(gameWon == false) {

                totalCounter++;

                infoText.setVisibility(View.GONE);

                player1Card.setCardElevation(0);
                player2Card.setCardElevation(0);

                // Writes current choice to currentState
                currentState[buttonChecked] = currentPlayer;

                // Checks win
                for (int[] winState : winStates) {
                    if (currentState[winState[0]] == currentState[winState[1]]
                            && currentState[winState[1]] == currentState[winState[2]]
                            && currentState[winState[0]] != -1) {

                        if (currentPlayer == 0) {
                            winner = new StringBuilder(player1String);
                        } else {
                            winner = new StringBuilder(player2String);
                        }

                        gameWon = true;
                        winner.append(" is the Winner!");
                    }
                }

                if (currentPlayer == 1) {
                    // Next player
                    currentPlayer = 0;
                    // Shows player choice
                    button.setText(player2String);
                    // Player Hint
                    player1Card.setCardElevation(15);
                } else if (currentPlayer == 0) {
                    currentPlayer = 1;
                    button.setText(player1String);
                    player2Card.setCardElevation(15);
                }

                if (gameWon) {
                    infoText.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Click winner for new game!", Toast.LENGTH_SHORT).show();
                    infoText.setText(winner);
                }

                // Match Draw
                if (totalCounter == 9 && gameWon == false) {
                    winner = new StringBuilder("Match Draw!");
                    infoText.setText(winner);
                    infoText.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Click draw for new game!", Toast.LENGTH_SHORT).show();
                    // Sets environment for new game
                    gameWon = true;
                }
            } else {
                Toast.makeText(this, "Click winner for new game!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Cannot Overwrite a tile!", Toast.LENGTH_SHORT).show();
        }

    }

    public void newGame() {

        currentState = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};

        // Random First Play Turn
        Random random = new Random();
        int firstTurn = random.nextInt(2);

        if (firstTurn == 1) {
            currentPlayer = 1;
        }

        if (currentPlayer == 1) {
            player2Card.setCardElevation(15);
        } else if (currentPlayer == 0){
            player1Card.setCardElevation(15);
        }
    }

    public void infoClicked(View view) {
        if (gameWon) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}