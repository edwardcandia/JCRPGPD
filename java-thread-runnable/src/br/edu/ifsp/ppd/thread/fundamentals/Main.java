package br.edu.ifsp.ppd.thread.fundamentals;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    
    public static void main(String ... args) {

        Runnable r1 = new MinhaPrimeiraThread("t1", 1);
        Runnable r2 = new MinhaPrimeiraThread("t2", 3);

        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 2, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        pool.execute(r1);
        pool.execute(r2);
    }
}
