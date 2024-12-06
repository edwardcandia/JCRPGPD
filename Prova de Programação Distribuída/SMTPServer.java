import java.io.*;
import java.net.*;

public class SMTPServer {
    private static final int PORTA = 2525;
    private static final String NOME_SERVIDOR = "SimpleSMTPServer";
    
    public static void main(String[] args) {
        try (ServerSocket servidorSocket = new ServerSocket(PORTA)) {
            System.out.println("Servidor SMTP iniciado na porta " + PORTA);
            
            while (true) {
                Socket socketCliente = servidorSocket.accept();
                System.out.println("Nova conexão: " + socketCliente.getInetAddress());
                
                new Thread(new SMTPClienteHandler(socketCliente)).start();
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }
}
