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


    private ArrayList<Integer> distributedRandomGenerator(double[] chances) {
        for (double i : chances) {
             i*=100;
        }
        ArrayList<Integer> distribution = new ArrayList<Integer>(100);
        for (int i = 0; i < chances.length; i++) {
            for (int j = 0; j < (int) chances[i]; j++) {
                distribution.add(i);
            }
        }
        return distribution;
    }

    private void fill(int n, double[] chances) {
        ArrayList<Integer> distr = distributedRandomGenerator(chances);
        for(int i = 0; i < n; i++) {
            list.add(i, colors.get((int) (Math.random() * (distr.size()))));
        }
        // list sorting allows us to work with class methods more easily
        Collections.sort(list);
    }

    public ArrayList<String> getList() {
        return list;
    }

    // default constructor:
    Container() {
        // append 10 random items to list from colors
        fill(10, new double[] {0.2, 0.2, 0.2, 0.2, 0.2});
    }
    // init constructor:
    Container(int len, double[] chances, String... s) {
        for (String ball : s) {
                list.add(ball);
            Collections.sort(list);
        }
        if (s.length < len) {
            fill(len-s.length, chances);
        }
    }

    // copying in java by default means copying references of all
    // non-primitive fields of class, but not copying instances.
    // to avoid that, it's needed to override clone() method implemented
    // by Object class.
    // as we're using copy constructor, we will use assign() method
    // described down there to create deep copy of ArrrayList list.

    // copy constructor:
    Container(Container c) {
        assign(c);
    }

    // destructors in java are not allowed as there is garbage collector
    // which destroys unused instances automatically.
    // however, there is finalize() method which allows to do extra
    // actions before an instance will be deleted by GC.

    protected void finalize() {
        System.out.println("Instance of Container class: " + this.getList() +
                " is going to be deleted");
    }

    // operator overloading is not allowed in java as it may cause to
    // shooting in your leg. it's recommended to use class methods instead
    // of default operators.
    // also, we will override equals() method to make sure that it works
    // properly.

    // assignment:
    public void assign(Container c) {
        this.list = new ArrayList(c.getList());
    }

    // let's start with an assumption that greater container contains more
    // balls in it without reference to their colors. still, equation of
    // containers means that numbers of balls of each color should be the
    // same for both of the containers.

    // comparision:
    public boolean isGreater(Container c) {
        if (this.list.size() > c.list.size()) {
            return true;
        }
        else return false;
    }
    public boolean isLesser(Container c) {
        if (this.list.size() < c.list.size()) {
            return true;
        }
        else return false;
    }
    @Override
    public boolean equals(Object o) {
        if (this == null || o == null) return false;
        if (this == o) return true;
        Container copy = (Container) o;
        if (this.getList().size() != copy.getList().size()) return false;
        if (this.getList().containsAll(copy.getList()) && copy.getList().containsAll(getList())){
            return true;
        }
        return false;
    }

    // sum:
    public Container add(Container container) {
        Container added = new Container(this);
        added.list.addAll(container.list);
        Collections.sort(added.list);
        return added;
    }

    // subtract:

    // be aware of using sub() method without equation to an object.
    // if exception will be thrown, method will return a copy of instance
    // before changing it.
    // still, in main() [a] contains reference to [a] object, and as a result it will be
    // equated to [a] object as it was presented in sub() method.
    // as a result, [a] would contain changed version of itself.
    public Container sub(Container container) throws NotExistException {
        Container subbed = new Container(this);
        for (String ball : container.getList()) {
            if (subbed.list.contains(ball)) {
                subbed.list.remove(ball);
            }
            else {
                throw new NotExistException(ball, this);
            }
        }
        return subbed;
    }

    // contains:
    public boolean contains(Container container) {
        Container copy = new Container(container);
        if (this.getList().size() < container.getList().size()) return false;
        for (String ball : container.getList()) {
            if (!copy.getList().contains(ball)) {
                return false;
            }
            copy.list.remove(ball);
        }
        return true;
    }
    //clear:
    public void clear() {
        this.list = new ArrayList(0);
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
