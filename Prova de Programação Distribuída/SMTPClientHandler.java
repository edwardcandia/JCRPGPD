import java.io.*;
import java.net.*;
import java.util.regex.*;

class SMTPClienteHandler implements Runnable {
    private static final String RESPOSTA_HELLO = "220 SimpleSMTPServer Simple Mail Transfer Service Ready";
    
    private Socket socketCliente;
    
    public SMTPClienteHandler(Socket socket) {
        this.socketCliente = socket;
    }
    
    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
             PrintWriter out = new PrintWriter(socketCliente.getOutputStream(), true)) {
            
            out.println(RESPOSTA_HELLO);
            
            String entradaCliente;
            String emailRemetente = null;
            String emailDestinatario = null;
            
            while ((entradaCliente = in.readLine()) != null) {
                System.out.println("Comando recebido: " + entradaCliente);
                
                if (entradaCliente.startsWith("MAIL FROM:")) {
                    emailRemetente = entradaCliente.substring(10).trim();
                    if (isEmailValido(emailRemetente)) {
                        out.println("250 Remetente " + emailRemetente + " OK");
                    } else {
                        out.println("500 Erro de sintaxe, comando não reconhecido");
                    }
                } else if (entradaCliente.startsWith("RCPT TO:")) {
                    emailDestinatario = entradaCliente.substring(8).trim();
                    if (isEmailValido(emailDestinatario)) {
                        out.println("250 Destinatário " + emailDestinatario + " OK");
                    } else {
                        out.println("500 Erro de sintaxe, comando não reconhecido");
                    }
                } else if ("DATA".equals(entradaCliente)) {
                    out.println("354 Finalize a mensagem com <CR><LF>.<CR><LF>");
                    StringBuilder mensagem = new StringBuilder();
                    
                    while ((entradaCliente = in.readLine()) != null) {
                        if (".".equals(entradaCliente)) {
                            break;
                        }
                        mensagem.append(entradaCliente).append("\n");
                    }
                    
                    out.println("250 Mensagem aceita para entrega");
                } else if ("QUIT".equals(entradaCliente)) {
                    out.println("221 SimpleSMTPServer Serviço encerrando o canal de transmissão");
                    break;
                } else {
                    out.println("500 Erro de sintaxe, comando não reconhecido");
                }
            }
        } catch (IOException e) {
            System.err.println("Erro na comunicação com o cliente: " + e.getMessage());
        }
    }
    
    private boolean isEmailValido(String email) {
        String regexEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(regexEmail);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
