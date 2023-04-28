import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RandProductSearch extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private final JTextField partialNameField;
    private final JTextArea resultArea;

    private final RandomAccessFile raf;

    public RandProductSearch(RandomAccessFile raf) {
        super("Random Product Search");
        this.raf = raf;

        // Create UI components
        JLabel partialNameLabel = new JLabel("Partial Product Name:");
        partialNameField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        resultArea = new JTextArea(20, 80);
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        // Layout components
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(partialNameLabel);
        searchPanel.add(partialNameField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);
        add(resultScrollPane, BorderLayout.CENTER);

        // Configure window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String partialName = partialNameField.getText().trim();
        resultArea.setText("");

        try {
            // Iterate through all records in the random access file
            raf.seek(0);
            int recordCount = raf.readInt();
            for (int i = 0; i < recordCount; i++) {
                String name = readString(raf).trim();
                String description = readString(raf).trim();
                double cost = raf.readDouble();

                // Check if the name contains the partial name
                if (name.contains(partialName)) {
                    resultArea.append(String.format("Name: %s\nDescription: %s\nCost: %.2f\n\n",
                            name, description, cost));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            resultArea.setText("Error searching for products: " + ex.getMessage());
        }
    }

    // Utility method to read a fixed-length string from a random access file
    public static String readString(RandomAccessFile file) throws IOException {
        StringBuilder sb = new StringBuilder();
        char c = 0;
        long pos = file.getFilePointer();
        while (c != '\n' && pos < file.length()) {
            c = file.readChar();
            sb.append(c);
            pos = file.getFilePointer();
        }
        return sb.toString().trim();
    }
    public static void main(String[] args) {
        try {
            RandomAccessFile raf = new RandomAccessFile("products.dat", "r");
            new RandProductSearch(raf);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Error opening products file: " + ex.getMessage());
        }
    }

}
