package br.edu.ifsp.ppd.thread.philosopherdinner;

import java.util.Random;

public class Philosopher implements Runnable {

    private final int id;
    private final Fork leftFork;
    private final Fork rightFork;
    private int numberOfThoughts = 0;
    private int numberOfMeals = 0;
    private String state = "Getting up";

    public Philosopher(int id, Fork leftFork, Fork rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    public String getName() {
        return "P" + this.id;
    }

    public int getId() {
        return this.id;
    }

    public String getState() {
        return this.state;
    }

    public int getNumberOfThoughts() {
        return this.numberOfThoughts;
    }

    public int getNumberOfMeals() {
        return this.numberOfMeals;
    }

    private void think() {
        this.state = "Thinking";
        this.numberOfThoughts++;
        try {
            Thread.sleep(new Random().nextInt(800) + 200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void eat() {
        this.state = "Eating";
        this.numberOfMeals++;
        try {
            Thread.sleep(new Random().nextInt(1000) + 500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        while (this.numberOfThoughts < 10) {
            think();

            Fork firstFork = (id % 2 == 0) ? leftFork : rightFork;
            Fork secondFork = (id % 2 == 0) ? rightFork : leftFork;

            synchronized (firstFork) {
                synchronized (secondFork) {
                    eat();
                }
            }
        }
        this.state = "DONE";
    }
}
