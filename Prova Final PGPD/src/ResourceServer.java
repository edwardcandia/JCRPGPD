package src;

class ResourceServer {
    private static final int PORT = 5000;
    private static final int NUMBER_FORKS = 5;
    private static final int NUMBER_PHILOSOPHERS = 5;

    private final Map<Integer, PhilosopherData> philosophers = new HashMap<>();
    private final boolean[] forks = new boolean[NUMBER_FORKS];
    private final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        ResourceServer server = new ResourceServer();
        server.start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado na porta " + PORT);

            int id = 1;
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket, id++)).start();
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }

    private class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final int philosopherId;

        public ClientHandler(Socket clientSocket, int philosopherId) {
            this.clientSocket = clientSocket;
            this.philosopherId = philosopherId;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String message;
                while ((message = in.readLine()) != null) {
                    String response = handleRequest(message);
                    if (response != null) {
                        out.println(response);
                    }
                }
            } catch (IOException e) {
                System.err.println("Conexão perdida com o filósofo ID " + philosopherId);
            } finally {
                cleanup();
            }
        }

        private void cleanup() {
            lock.lock();
            try {
                philosophers.remove(philosopherId);
                System.out.println("Filósofo ID " + philosopherId + " desconectado.");
            } finally {
                lock.unlock();
            }
        }

        private String handleRequest(String message) {
            lock.lock();
            try {
                switch (message) {
                    case "HELLO":
                        return "HI " + philosopherId;

                    case "THINK":
                        philosophers.computeIfAbsent(philosopherId, PhilosopherData::new).incrementThinking();
                        return "ACK";

                    case "REQUEST_FORKS":
                        return tryAllocateForks(philosopherId) ? "FORKS_GRANTED" : "FORKS_DENIED";

                    case "RELEASE_FORKS":
                        releaseForks(philosopherId);
                        return "ACK";

                    case "EAT":
                        philosophers.get(philosopherId).incrementEating();
                        return "ACK";

                    default:
                        return "UNKNOWN_COMMAND";
                }
            } finally {
                lock.unlock();
            }
        }

        private boolean tryAllocateForks(int id) {
            int leftFork = id - 1;
            int rightFork = id % forks.length;

            if (!forks[leftFork] && !forks[rightFork]) {
                forks[leftFork] = true;
                forks[rightFork] = true;
                return true;
            }
            return false;
        }

        private void releaseForks(int id) {
            int leftFork = id - 1;
            int rightFork = id % forks.length;
            forks[leftFork] = false;
            forks[rightFork] = false;
        }
    }

    private static class PhilosopherData {
        private int thinkingCount = 0;
        private int eatingCount = 0;

        public void incrementThinking() {
            thinkingCount++;
            System.out.println("Pensando... Total: " + thinkingCount);
        }

        public void incrementEating() {
            eatingCount++;
            System.out.println("Comendo... Total: " + eatingCount);
        }
    }
}
