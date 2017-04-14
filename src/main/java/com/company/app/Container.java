package com.company.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by xk on 03.04.17.
 */
public class Container {

    private ArrayList<String> list = new ArrayList<String>();
    private static final List<String> colors =
            Arrays.asList("White", "Red", "Blue", "Green", "Blvck");

    private ArrayList<Integer> distributedRandomGenerator(ArrayList<Double> chances) {
        ArrayList<Integer> distribution = new ArrayList<Integer>(100);
        for (int i = 0; i < chances.size(); i++) {
            for (int j = 0; j < Math.round(chances.get(i)*100); j++) {
                distribution.add(i);
            }
        }
        return distribution;
    }

    private void fill(int n, ArrayList chances) {
        ArrayList<Integer> distr = distributedRandomGenerator(chances);
        for(int i = 0; i < n; i++) {
            list.add(i, colors.get(distr.get((int)(Math.random()* (distr.size())))));
        }
        Collections.sort(list);
    }

    Container(int len, ArrayList<Double> chances, String... s) {
        for (String ball : s) {
                list.add(ball);
            Collections.sort(list);
        }
        if (s.length < len) {
            fill(len-s.length, chances);
        }
    }

    public String getBall (int i) {
        String ball = list.get(i);
        list.remove(ball);
        return ball;
    }
    public void putBall (String ball) {
        list.add(ball);
    }
}
