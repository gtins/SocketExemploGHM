package br.org.catolicasc.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GreetClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        // handler para escrever os dados
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        // handler para ler os dados
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException ex) {
            System.out.println("Erro ao fechar a conexão 1.");
        }
    }

    public void sendMessage(String msg) throws IOException {
        out.println(msg); // envia mensagem para o Socket
    }

    public String receiveMessage() throws IOException {
        return in.readLine(); // retorna a mensagem recebida do Socket
    }

    public static void main(String[] args) {
        GreetClient client = new GreetClient();
        try {
            client.start("127.0.0.1", 12345);

            Scanner scanner = new Scanner(System.in);
            String jogada;
            String response;

            // Game logic
            do {
                System.out.print("Escolha um número de 1 a 10: ");
                jogada = scanner.nextLine();
                client.sendMessage(jogada);
                response = client.receiveMessage();
                System.out.println("Resposta: " + response);
            } while (!"!quit".equals(jogada) && !"Acertou!".equals(response));

            System.out.println("Fechando o jogo...");

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            client.stop();
        }
    }
}