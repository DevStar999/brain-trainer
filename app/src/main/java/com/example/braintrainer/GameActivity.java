package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private Utility utility;

    private void initialise() {
        timerTextView = findViewById(R.id.timer_text_view);
        questionTextView = findViewById(R.id.question_text_view);
        scoreTextView = findViewById(R.id.score_text_view);
        verdictTextView = findViewById(R.id.verdict_text_view);
        replayButton = findViewById(R.id.replay_button);

        defaultGameDuration = 30*1000; // 30 seconds
        gameNumbers = new GameNumbers();
        utility = new Utility();
    }

    private void setGameText() {
        // Refresh GameNumbers
        gameNumbers.resetAndGenerateData();

        // Setting Score Text
        scoreTextView.setText(gameNumbers.getCorrectAnswers().toString() + "/" +
                              gameNumbers.getTotalQuestionsPlayed().toString());

        // Setting Question Text
        if (isGameOngoing) {
            questionTextView.setText(gameNumbers.getOperands().get(0) + " + " +
                    gameNumbers.getOperands().get(1));
        }
        else {
            questionTextView.setText("Your Score = ");
        }


        // Setting OptionTextViews
        for (Integer optionNumber=0; optionNumber<4; optionNumber++) {
            Integer optionResourceId = this.getResources().getIdentifier("option_text_view_" +
                    optionNumber.toString(), "id", this.getPackageName());
            TextView optionTextView = findViewById(optionResourceId);
            if (isGameOngoing) {
                optionTextView.setText(String.format("%02d", gameNumbers.getOptions().get(optionNumber)));
            }
            else {
                optionTextView.setText("");
            }
        }
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
                timerTextView.setText(utility.convertMilliToTimerDisplay(millisUntilFinished));
            }
            @Override
            public void onFinish() {
                verdictTextView.setText("Game Over");
                replayButton.setVisibility(View.VISIBLE);
                isGameOngoing = false;
                setGameText();
            }
        }.start();
    }

    public void clickOption(View view) {
        if (isGameOngoing) {
            final boolean isCorrect =
                    gameNumbers.checkAnswer(Integer.parseInt(((TextView) view).getText().toString()));
            new CountDownTimer(500,1) {
                @Override
                public void onTick(long millisUntilFinished) {
                    verdictTextView.setText((isCorrect) ? "Correct!" : "Wrong :(");
                }
                @Override
                public void onFinish() {
                    verdictTextView.setText("");
                    setGameText();
                }
            }.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initialise();

        replayButton.setOnClickListener(v -> { resetGame(); playGame(); });

        playGame();
    }
}