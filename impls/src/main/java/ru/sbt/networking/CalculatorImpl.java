package ru.sbt.networking;

/**
 * Created by user16 on 07.09.2016.
 */

public class CalculatorImpl implements Calculator {
    @Override
    public double calculate(Integer a, Integer b) {
        return a + b - 410 / 12;
    }

    @Override
    public void exceptionThrower() {
        throw new ArithmeticException();
    }
}