package com.example.braintrainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import lombok.Getter;

@Getter
public class GameNumbers {
    private Integer totalQuestionsPlayed;  // Total number of question played in the game
    private Integer correctAnswers;        // Number of correct answers given by the player
    private List<Integer> operands;    // List to hold the 2 operands for question
    private List<Integer> options;     // List to hold the 4 options for the addition of operands
    private GameNumbersHelper gameNumbersHelper; // Helper class object

    public GameNumbers() {
        operands = new ArrayList<>();
        options = new ArrayList<>();
        gameNumbersHelper = new GameNumbersHelper();

        resetGame();
    }

    public void resetGame() {
        totalQuestionsPlayed = 0;
        correctAnswers = 0;
    }

    public void resetAndGenerateData() {
        operands.clear();
        options.clear();

        Random random = new Random();
        for (int numberOfOperands = 1; numberOfOperands<=2; numberOfOperands++) {
            operands.add(random.nextInt(20) + 1);
        }

        options.add(gameNumbersHelper.getSumOfListElements(operands)); // Adding the correct option at first position for now
        for (int optionNumber=1; optionNumber<=3; optionNumber++) {
            Integer tempOption = random.nextInt(40) + 1;
            while (!gameNumbersHelper.isUnique(options, tempOption)) {
                tempOption = random.nextInt(40) + 1;
            }
            options.add(tempOption);
        }
        Collections.shuffle(options); // Shuffling the options
    }

    public boolean checkAnswer(Integer variableToBeCheckedWith) {
        totalQuestionsPlayed++;
        if (variableToBeCheckedWith.equals(gameNumbersHelper.getSumOfListElements(operands))) {
            correctAnswers++;
            return true;
        }
        return false;
    }
}
