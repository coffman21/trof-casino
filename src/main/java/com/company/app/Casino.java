package com.company.app;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by xk on 12.04.17.
 */
public class Casino {
    XmlDomObj xml;
    Casino(XmlDomObj xml) {this.xml = xml;}

    public String[] play() {
        int getNumber = 50;
        ArrayList chances = getChances(xml);
        ArrayList<Container> boxes = new ArrayList<Container>(10);

        for (int i = 0; i < 10; i++) {
            boxes.add(new Container(100, chances));
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < getNumber; j++) {
                String ball = boxes.get(i).getBall((int) (Math.random() * (100-j)));
                boxes.get(i + 1).putBall(ball);
            }
        }
        String[] result = new String[3];
        for (int i=0; i < 3; i++) {
            result[i] = (boxes.get(9).getBall((int) (Math.random() * (100-i))));
        }
        return result;

//         System.out.println("Your combination was\n" + String.join(", ", result));
//

//        for (int i = 0; i < getComb().size(); i++) {
//            String winBall = getComb().get(i);
//            double currExp = 0;
//            for (int j = 0; j < chances.length; j++) {
//                if (Objects.equals(result.get(i), winBall)) {
//                    expectedValue += getChances().get(j);
//                }
//            }
//            expectedValue += currExp;
//        }
//        expectedValue /= getComb().size();
//        System.out.println("Expected value: " + expectedValue);

//        if (result.equals(getChances())) {
//            System.out.println("You won!");
//            return 1;
//        }
//        else {
//            System.out.println("You lose!");
//            return 0;
//        }
    }
    public ArrayList<Double> getChances(XmlDomObj xml) {
        return xml.getDistr();
    }

    public ArrayList<String[]> getComb(XmlDomObj xml) {
        return xml.getCombs();
    }
}
