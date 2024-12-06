import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;

public class ServidorHttp {
    private static final Map<Integer, String> alunos = new HashMap<>();
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(8080);
        System.out.println("Servidor rodando na porta 8080");

        while (true) {
            Socket cliente = servidor.accept();
            new Thread(() -> {
                try {
                    processarRequisicao(cliente);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void processarRequisicao(Socket cliente) throws IOException {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);

        String linha = entrada.readLine();
        if (linha == null) return;

        String[] partes = linha.split(" ");
        String metodo = partes[0];
        String caminho = partes[1];

        if (metodo.equals("GET")) {
            processarGet(caminho, saida);
        } else if (metodo.equals("DELETE")) {
            processarDelete(caminho, saida);
        } else if (metodo.equals("POST")) {
            processarPost(saida);
        } else {
            responderErro(saida, 405, "Método não permitido");
        }

        entrada.close();
        saida.close();
        cliente.close();
    }

    private static void processarGet(String caminho, PrintWriter saida) {
        String[] partes = caminho.split("/");
        if (partes.length != 3) {
            responderErro(saida, 404, "Página não encontrada");
            return;
        }

        int id = Integer.parseInt(partes[2]);
        if (alunos.containsKey(id)) {
            saida.println("HTTP/1.1 200 OK");
            saida.println("Content-Type: text/html");
            saida.println();
            saida.println("<html><body>");
            saida.println("<h1>Aluno ID " + id + "</h1>");
            saida.println("<p>" + alunos.get(id) + "</p>");
            saida.println("</body></html>");
        } else {
            responderErro(saida, 404, "Aluno não encontrado");
        }
    }

    private static void processarDelete(String caminho, PrintWriter saida) {
        String[] partes = caminho.split("/");
        if (partes.length != 3) {
            responderErro(saida, 404, "Página não encontrada");
            return;
        }

        int id = Integer.parseInt(partes[2]);
        lock.lock();
        try {
            if (alunos.containsKey(id)) {
                alunos.remove(id);
                saida.println("HTTP/1.1 200 OK");
                saida.println("Content-Type: text/html");
                saida.println();
                saida.println("<html><body>");
                saida.println("<h1>Aluno ID " + id + " excluído com sucesso</h1>");
                saida.println("</body></html>");
            } else {
                responderErro(saida, 404, "Aluno não encontrado");
            }
        } finally {
            lock.unlock();
        }
    }

    private static void processarPost(PrintWriter saida) {
        lock.lock();
        try {
            int idNovo = gerarIdUnico();
            alunos.put(idNovo, "Aluno " + idNovo);
            saida.println("HTTP/1.1 201 Created");
            saida.println("Content-Type: text/html");
            saida.println();
            saida.println("<html><body>");
            saida.println("<h1>Aluno criado com ID " + idNovo + "</h1>");
            saida.println("</body></html>");
        } finally {
            lock.unlock();
        }
    }

    private static void responderErro(PrintWriter saida, int codigo, String mensagem) {
        saida.println("HTTP/1.1 " + codigo + " " + mensagem);
        saida.println("Content-Type: text/html");
        saida.println();
        saida.println("<html><body>");
        saida.println("<h1>Erro " + codigo + ": " + mensagem + "</h1>");
        saida.println("</body></html>");
    }

    private static int gerarIdUnico() {
        int id = new Random().nextInt(1000) + 1;
        while (alunos.containsKey(id)) {
            id = new Random().nextInt(1000) + 1;
        }
        return id;
    }
}
