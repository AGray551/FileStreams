import javax.swing.*;
import java.awt.event.ActionEvent;
public class RandProductMakerRunner extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RandProductMaker randProductMaker = new RandProductMaker();
                randProductMaker.setVisible(true);
            }
        });
    }

}

