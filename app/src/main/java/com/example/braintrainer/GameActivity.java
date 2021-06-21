package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {
    // Views
    private TextView timerTextView;
    private TextView questionTextView;
    private TextView scoreTextView;
    private TextView verdictTextView;
    private Button replayButton;

    // Variables
    private boolean isGameOngoing;
    private long defaultGameDuration;
    private GameNumbers gameNumbers;

    private void initialise() {
        timerTextView = findViewById(R.id.timerTextView);
        questionTextView = findViewById(R.id.questionTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        verdictTextView = findViewById(R.id.verdictTextView);
        replayButton = findViewById(R.id.replayButton);

        defaultGameDuration = 30*1000;
        gameNumbers = new GameNumbers();
    }

    private void setGameText() {
        // Refresh GameNumbers
        gameNumbers.resetAndGenerateData();

        // Setting Score Text
        String scoreTextViewDisplay = gameNumbers.getCorrectAnswers().toString() + "/" +
                                      gameNumbers.getTotalQuestionsPlayed().toString();
        Log.i("Info", "scoreTextViewDisplay = " + scoreTextViewDisplay);
        scoreTextView.setText(scoreTextViewDisplay);

        // Setting Question Text
        String questionTextViewDisplay = gameNumbers.getOperands().get(0) + " + " +
                                         gameNumbers.getOperands().get(1);
        Log.i("Info", "questionTextViewDisplay = " + questionTextViewDisplay);
        questionTextView.setText(questionTextViewDisplay);

        // Setting OptionTextViews
        Log.i("Info", "Inside setGameText(), the ids of optionTextViews are as follows -");
        for (Integer optionNumber=0; optionNumber<4; optionNumber++) {
            Integer optionResourceId = this.getResources().getIdentifier(
                    "optionTextView" + optionNumber.toString(),
                    "id",
                    this.getPackageName()
            );
            Log.i("Info", "optionResourceId" + optionNumber + " = " + optionResourceId);

            TextView optionTextView = findViewById(optionResourceId);
            optionTextView.setText(String.format("%02d", gameNumbers.getOptions().get(optionNumber)));
        }
    }

    public String convertMilliToTimerDisplay(long milliseconds) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)%60L; // If total minutes go over 60 minutes
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)%60L; // If total seconds go over 60 seconds
        return String.format("%01d:%02d", minutes, seconds);
    }

    private void resetGame() {
        setGameText();
        verdictTextView.setText("");
        replayButton.setVisibility(View.INVISIBLE);
        gameNumbers.resetGame();
    }

    private void playGame() {
        // Blank out the play button and set isGameOngoing to true
        replayButton.setVisibility(View.INVISIBLE);
        isGameOngoing = true;

        // Set the game text for the first time
        setGameText();

        // Start game timer
        new CountDownTimer(defaultGameDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(convertMilliToTimerDisplay(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                verdictTextView.setText("Game Over");
                replayButton.setVisibility(View.VISIBLE);
                isGameOngoing = false;
            }
        }.start();
    }

    public void clickOption(View view) {
        if (isGameOngoing) {
            if (gameNumbers.checkAnswer(Integer.parseInt(((TextView) view).getText().toString()))) {
                verdictTextView.setText("Correct!");
            }
            else {
                verdictTextView.setText("Wrong :(");
            }
            setGameText();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initialise();

        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                playGame();
            }
        });

        playGame();
    }
}