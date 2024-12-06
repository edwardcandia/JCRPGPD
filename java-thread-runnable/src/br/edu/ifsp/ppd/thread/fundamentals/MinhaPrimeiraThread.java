package br.edu.ifsp.ppd.thread.fundamentals;

class MinhaPrimeiraThread implements Runnable {

    private Integer sleepInSeconds = 1;
    private String name;

    public MinhaPrimeiraThread(String name, Integer sleepInSeconds) {
        this.name = name;
        this.sleepInSeconds = sleepInSeconds;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Rodando a thread " + this.name);
            this.sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(this.sleepInSeconds);
        } catch (InterruptedException e) {

        }
    }
}