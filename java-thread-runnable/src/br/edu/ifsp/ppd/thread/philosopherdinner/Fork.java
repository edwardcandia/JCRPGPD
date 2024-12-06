package br.edu.ifsp.ppd.thread.philosopherdinner;

public class Fork {

    private final int id;

    public Fork(int id) {
        this.id = id;
    }

    public String getName() {
        return "F" + this.id;
    }
}
