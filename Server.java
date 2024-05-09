package cop2805;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        LineSearcher lineSearcher = new LineSearcher("hamlet.txt"); // Provide the file name

        try {
            ServerSocket serverSocket = new ServerSocket(1236); // Port number

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Checking lines");

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

                String input = reader.readLine();
                int lineNumber = Integer.parseInt(input);

                List<String> result = lineSearcher.searchLines(lineNumber);

                for (String line : result) {
                    writer.println(line);
                }

                clientSocket.close();
                System.out.println("Lines found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
