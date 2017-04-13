package com.company.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by xk on 12.04.17.
 */
public class Casino {
    public int play() {
        double expectedValue = 0;
        int getNumber = 50;
        double[] chances = new double[]{0.5, 0.25, 0.15, 0.07, 0.03};
        ArrayList<Container> boxes = new ArrayList<Container>(10);
        for (int i = 0; i < 10; i++) {
            boxes.add(new Container(100, chances));
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < getNumber; j++) {
                String ball = boxes.get(i).getBall((int) (Math.random() * 100));
                boxes.get(i + 1).putBall(ball);
            }
        }
        ArrayList<String> result = new ArrayList<String>(3);
        for (int i=0; i < 3; i++) {
            result.add(boxes.get(10).getBall((int) (Math.random() * 100)));
        }

        System.out.println("Your combination was\n" + String.join(", ", result)+ "\nand the winning combination was\n" + String.join(", ", getComb()));


        for (int i = 0; i < getComb().size(); i++) {
            String winBall = getComb().get(i);
            double currExp = 0;
            for (int j = 0; j < chances.length; j++) {
                if (Objects.equals(result.get(i), winBall)) {
                    expectedValue += getChances().get(j);
                }
            }
            expectedValue += currExp;
        }
        expectedValue /= getComb().size();
        System.out.println("Expected value: " + expectedValue);
        if (result.equals(getChances())) {
            System.out.println("You won!");
            return 1;
        }
        else {
            System.out.println("You lose!");
            return 0;
        }
    }
    public ArrayList<Double> getChances() {
        return (ArrayList<Double>) Arrays.asList(0.5, 0.25, 0.15, 0.07, 0.03);
    }

    public ArrayList<String> getComb() {
        return (ArrayList<String>) Arrays.asList("Blue", "Blue", "Blue");
    }
}
