package com.company.app;

import com.sun.org.apache.bcel.internal.generic.GOTO;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xk on 12.04.17.
 */
public class Main {
    public static void main(String[] args) {
        int numOfIter = Integer.parseInt(args[0]);
        if (args.length == 0) numOfIter = 10000;

        XmlDomObj xml = new XmlDomObj("src/main/resources/params.xml");
        Long totalPrize = 0L;
        Double sigma = 0D;
        ArrayList<Integer>prizes = new ArrayList<>(numOfIter/2);
        boolean toPrint = false;
        for (int i = 0; i < numOfIter; i++) {
            Casino c = new Casino(xml);
            ArrayList<String[]> combs = c.getComb(xml);
            String[] comb;
            String prize = "";
            String[] res = c.play();
            boolean won = false;
            for (String[] comb1 : combs) {
                comb = Arrays.copyOfRange(comb1, 0, 3);
                if (i%10 == 0) {
                    if (Arrays.equals(comb, res)) {
                        won = true;
                        prize = comb1[3];
                    }
                }
            }

            if (won) {
                prizes.add(Integer.parseInt(prize));
                totalPrize += Integer.parseInt(prize);
                if (toPrint) {
                    System.out.println(Integer.toString(i) + " You won " + prize + "! Your result is " + String.join(", ", res));
                }
            else {
                    if (toPrint) {
                        System.out.println(Integer.toString(i) + " Sorry but");
                    }
                }
            }


        }
        long expectedValue = totalPrize/numOfIter;
        for (int i : prizes) {
            sigma += (i*i - expectedValue*expectedValue);
        }
        sigma = Math.sqrt(sigma/numOfIter);
        System.out.println("Expected Value: " + Long.toString(expectedValue));
        System.out.println("Risk: " + Double.toString(sigma));
        //крашится
        xml.addNode(new String[]{"Distribution"}, "Expected Value="+Long.toString(expectedValue));
        xml.addNode(new String[]{"Params"}, "Risk="+(Double.toString(sigma)));
    }
}
