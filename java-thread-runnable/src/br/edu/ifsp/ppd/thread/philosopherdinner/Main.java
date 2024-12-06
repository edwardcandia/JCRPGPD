package br.edu.ifsp.ppd.thread.philosopherdinner;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        final int NUMBER_OF_PHILOSOPHERS = 5;

        List<Fork> forks = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
            forks.add(new Fork(i + 1));
        }

        List<Philosopher> philosophers = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
            Fork leftFork = forks.get(i);
            Fork rightFork = forks.get((i + 1) % NUMBER_OF_PHILOSOPHERS);

            Philosopher philosopher = new Philosopher(i + 1, leftFork, rightFork);
            philosophers.add(philosopher);

            Thread thread = new Thread(philosopher);
            threads.add(thread);
            thread.start();
        }

        boolean threadsStillRunning;
        do {
            threadsStillRunning = threads.stream().anyMatch(Thread::isAlive);

            for (Philosopher p : philosophers) {
                System.out.println(p.getId() + ": " + p.getState() +
                        " | Thoughts = " + p.getNumberOfThoughts() +
                        " | Meals = " + p.getNumberOfMeals());
            }

            System.out.println("============================================");

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        } while (threadsStillRunning);

        System.out.println("Jantar finalizado. Todos os filósofos concluíram suas tarefas.");
    }
}
