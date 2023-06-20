package br.org.catolicasc.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class GreetServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private int chosenNumber;

    public void start(int port) throws IOException {
        // Initialize attributes
        serverSocket = new ServerSocket(port); // listen on the specified port
        clientSocket = serverSocket.accept(); // wait for connection
        // handler for writing data
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        // handler for reading data
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//        chosenNumber = numeroRandom();
//        System.out.println("Numero Escolhido: " + chosenNumber); // Print the chosen number

        clientHandler();
    }

    private void clientHandler() throws IOException {
        String jogada;
        //out.println("Escolhendo um número de 1 a 10");

        while (true) {
            chosenNumber = numeroRandom();
            System.out.println("Numero Escolhido: " + chosenNumber);
            jogada = in.readLine();
            if (jogada.equals("!quit")) {
                out.println("Saindo do jogo");
                break;
            }
//            out.println("Escolhendo um número de 1 a 10");

            int guessedNumber = Integer.parseInt(jogada);
            if (guessedNumber == chosenNumber) {
                out.println("Acertou!");
                break;
            } else {
                out.println("Errou!");
            }
        }

        System.out.println("Fechando conexão");
    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException ex) {
            System.out.println("Erro ao fechar a conexão!");
        }
    }

    private int numeroRandom() {
        Random random = new Random();
        return random.nextInt(10) + 1;
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