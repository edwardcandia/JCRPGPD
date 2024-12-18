package src;

import java.io.*;
import java.net.*;
import java.util.*;

public class PhilosopherClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5000;
    private int id;
    private String name;

    public static void main(String[] args) {
        PhilosopherClient client = new PhilosopherClient();
        try {
            client.start();
        } catch (InterruptedException e) {
            System.err.println("Erro ao executar o cliente: " + e.getMessage());
        }
    }

    public void start() throws InterruptedException {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            out.println("HELLO");
            String response = in.readLine();
            id = Integer.parseInt(response.split(" ")[1]);
            System.out.println("Conectado como filósofo ID: " + id);

            System.out.print("Digite seu nome: ");
            name = scanner.nextLine();

            out.println("LOGIN: " + name);

            Random random = new Random();

            while (true) {
                think(out, random);
                requestForks(out, in);
                eat(out, random);
                releaseForks(out);
            }

        } catch (IOException e) {
            System.err.println("Erro ao conectar com o servidor: " + e.getMessage());
        }
    }

    private void think(PrintWriter out, Random random) throws InterruptedException {
        out.println("THINK");
        int thinkingTime = Math.max(0, (int) (random.nextGaussian() * 2000 + 5000));
        System.out.println("Filósofo " + name + " está pensando por " + thinkingTime + " ms.");
        Thread.sleep(thinkingTime);
    }

    private void requestForks(PrintWriter out, BufferedReader in) throws IOException, InterruptedException {
        while (true) {
            out.println("REQUEST_FORKS");
            String response = in.readLine();
            if ("FORKS_GRANTED".equals(response)) {
                System.out.println("Filósofo " + name + " pegou os garfos.");
                break;
            } else {
                System.out.println("Filósofo " + name + " está esperando pelos garfos...");
                Thread.sleep(500);
            }
        }
    }

    private void eat(PrintWriter out, Random random) throws InterruptedException {
        out.println("EAT");
        int eatingTime = 2000;
        System.out.println("Filósofo " + name + " está comendo por " + eatingTime + " ms.");
        Thread.sleep(eatingTime);
    }

    private void releaseForks(PrintWriter out) {
        out.println("RELEASE_FORKS");
        System.out.println("Filósofo " + name + " liberou os garfos.");
        try {
            Thread.sleep(300);
        } catch (InterruptedException ignored) {
        }
    }
}