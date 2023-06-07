package br.org.catolicasc.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ClientInfoStatus;

public class GreetServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
        //inicializar atributos
        serverSocket = new ServerSocket(port); // escuta na porta port
        clientSocket = serverSocket.accept();  // espera a conexão
        // handler para escrita de dados
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        // handler para leitura de dados
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        clientHandler();
    }

    private void clientHandler() throws IOException {
        String greeting = in.readLine();
        if("Hello server".equals(greeting)){
            out.println("Hello client");
        } else {
            out.println("Mensagem Incorreta.");
        }
    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException ex) {
            System.out.println("Erro ao fechar a conexão");
        }
    }

    public static void main(String[] args) {
        GreetServer server = new GreetServer();
        try {
            server.start(12345);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }

    }
}
