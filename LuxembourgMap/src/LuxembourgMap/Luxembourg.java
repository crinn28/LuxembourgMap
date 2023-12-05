package Tema7;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Luxembourg {

    private static void initUI() {
        JFrame f = new JFrame("Algoritmica Grafurilor");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new Graf());
        f.setSize(760 + 50, 760 + 50);
        f.setVisible(true);
    }

    public static void main(String[] args) {


        SwingUtilities.invokeLater(new Runnable()
        {
            public void run() {
                initUI();
            }
        });
    }

}