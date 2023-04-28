import java.awt.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.IOException;
import javax.swing.*;

public class RandProductMaker extends JFrame {
    private static final String FILE_NAME = "products.dat";
    private static final int NAME_LENGTH = 35;
    private static final int DESCRIPTION_LENGTH = 75;
    private static final int ID_LENGTH = 6;
    private static final int RECORD_LENGTH = NAME_LENGTH + DESCRIPTION_LENGTH + ID_LENGTH + 8; // 8 bytes for cost

    private RandomAccessFile file;
    private JTextField nameField, descField, idField, costField, countField;

    public RandProductMaker() {
        try {
            file = new RandomAccessFile(FILE_NAME, "rw");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error creating file.");
        }
        createGui();
    }

    private void createGui() {
        JPanel panel = new JPanel(new GridLayout(0, 2)); // 0 rows for unlimited, 2 columns for label and field
        nameField = new JTextField(NAME_LENGTH);
        descField = new JTextField(DESCRIPTION_LENGTH);
        idField = new JTextField(ID_LENGTH);
        costField = new JTextField();
        countField = new JTextField();
        countField.setEditable(false);
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addRecord());
        panel.add(new JLabel("Name: "));
        panel.add(nameField);
        panel.add(new JLabel("Description: "));
        panel.add(descField);
        panel.add(new JLabel("ID: "));
        panel.add(idField);
        panel.add(new JLabel("Cost: "));
        panel.add(costField);
        panel.add(addButton);
        panel.add(new JLabel()); // add a blank label to occupy the next cell
        panel.add(new JLabel("Record Count: "));
        panel.add(countField); // add the countField to the next cell

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setTitle("Random Product Maker");
        setLocationRelativeTo(null);
        setLocation(500, 200);
        setSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }




    private void addRecord() {
        String name = padString(nameField.getText(), NAME_LENGTH);
        String desc = padString(descField.getText(), DESCRIPTION_LENGTH);
        String id = padString(idField.getText(), ID_LENGTH);
        double cost = Double.parseDouble(costField.getText());
        Product product = new Product(name, desc, id, cost);
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            writer.println(product.toString() + String.format("%.2f", cost));
            countField.setText(String.valueOf((int) (file.length() / RECORD_LENGTH)));
            nameField.setText("");
            descField.setText("");
            idField.setText("");
            costField.setText("");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to file.");
        }
    }


    private String padString(String s, int length) {
        if (s.length() >= length) {
            return s.substring(0, length);
        }
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < length) {
            sb.append(" ");
        }
        return sb.toString();
    }

}
