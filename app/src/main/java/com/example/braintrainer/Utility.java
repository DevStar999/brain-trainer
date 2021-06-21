package com.example.braintrainer;

import java.util.concurrent.TimeUnit;

public class Utility {
    public String convertMilliToTimerDisplay(long milliseconds) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)%60L; // If total minutes go over 60 minutes
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)%60L; // If total seconds go over 60 seconds
        return String.format("%01d:%02d", minutes, seconds);
    }
}
