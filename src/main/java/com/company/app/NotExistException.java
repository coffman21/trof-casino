package com.company.app;

/**
 * Created by xk on 03.04.17.
 * this exception will be thrown when it's not possible to get a ball of
 * particular color from Container, i.e. there is no such element in ArrayList list of this
 * Container. This covers both of situations where if there are no enough balls in list
 * to get, or there are no balls of particular color.
 */
public class NotExistException extends Exception {
    private String ball;
    private String container;

    NotExistException(String s, com.company.app.Container c) {
        ball = s;
        container = c.toString();
    }
    public String toString() { return "Error: ball [" + ball +"] wasn't " +
            "found in a container [" + container + "]";}
}
