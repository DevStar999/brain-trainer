package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView infoContentTextView;

    private void initialise() {
        infoContentTextView = findViewById(R.id.info_content_text_view);

        String textOfContentTextView =  "Solve as many questions as you\n" +
                                        "can by choosing the answer of\n"+
                                        "the question among it's four\n" +
                                        "options in a time of 30 seconds,\n" +
                                        "for all the questions, to score\n" +
                                        "as high as possible. All the Best!";
        infoContentTextView.setText(textOfContentTextView);
    }

    public void startGame(View view) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();
    }
}