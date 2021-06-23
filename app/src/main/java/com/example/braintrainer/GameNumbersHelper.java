package com.example.braintrainer;

import java.util.List;

public class GameNumbersHelper {
    public Integer getSumOfListElements(List<Integer> list) {
        Integer sum = 0;
        for (Integer element: list) { sum += element; }
        return sum;
    }

    public boolean isUnique(List<Integer> list, Integer checkValue) {
        for (Integer element: list) {
            if (element.equals(checkValue)) { return false; }
        }
        return true;
    }
}
