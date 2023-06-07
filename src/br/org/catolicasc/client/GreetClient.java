package br.org.catolicasc.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GreetClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        // handler para escrita de dados
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        // handler para leitura de dados
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    }

    public void stop(){
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException ex) {
            System.out.println("Erro ao fechar a conexão");
        }
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg); // manda msg para o socket
        return in.readLine(); // retorna mensagem recebida
    }

    public static void main(String[] args) {
        GreetClient client = new GreetClient();
        try {
            client.start("127.0.0.1", 12345);
            String response = client.sendMessage("Hello server");
            System.out.println("Resposta do Servidor: " + response);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            client.stop();
        }
    }
}

