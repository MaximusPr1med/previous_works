package cop2805;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame {
    private JLabel lineNumberInputLabel; // Response prompt label
    private JLabel lineNumberLabel; // Displaying numbers label
    private JTextField textField; // Input from user
    private DefaultListModel<String> listModel; // Search results
    private JList<String> resultList; // Display results

    public Client() {
        setTitle("Line Number");
        setSize(750, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Add label for line number input
        lineNumberInputLabel = new JLabel("Response:");
        panel.add(lineNumberInputLabel, BorderLayout.WEST);

        textField = new JTextField();
        panel.add(textField, BorderLayout.NORTH);

        // Add label for line number display
        lineNumberLabel = new JLabel();
        panel.add(lineNumberLabel, BorderLayout.EAST);

        listModel = new DefaultListModel<>();
        resultList = new JList<>(listModel);
        panel.add(new JScrollPane(resultList), BorderLayout.CENTER);

        JButton transmitButton = new JButton("Connect"); // Button for user query
        // Action listener for button press from user
        transmitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                transmitRequest();
            }
        });
        panel.add(transmitButton, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
    // Method to transmit user query to the server and display results
    private void transmitRequest() {
        listModel.clear(); // Clear previous results
        String searchQuery = textField.getText();

        try {
            Socket socket = new Socket("127.0.0.1", 1236); // Connect to server
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.println(searchQuery);
            
            // Read responses from the server and add them to the list until there are no more responses
            String response;
            while ((response = reader.readLine()) != null) {
                listModel.addElement(response); // Add results to the list
            }

            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    // Main method to start the client application
    public static void main(String[] args) {
        new Client();
    }
}
