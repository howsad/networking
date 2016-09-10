package ru.sbt.networking;

/**
 * Created by user16 on 07.09.2016.
 */

public class CalculatorImpl implements Calculator {
    public double calculate(int a, int b) {
        return a + b - 410 / 12;
    }
}