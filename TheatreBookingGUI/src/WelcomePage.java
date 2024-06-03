import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class WelcomePage {
    private JFrame frame;
    private BufferedImage backgroundImage;

    public WelcomePage() {

        try {
            BufferedImage originalImage = ImageIO.read(new File("src/8112707.jpg"));
            frame = new JFrame("Welcome to Theater booking system");
            frame.setSize(1290, 778);
            backgroundImage = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = backgroundImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(originalImage, 0, 0, frame.getWidth(), frame.getHeight(), null);
            g2d.dispose();
        } catch (IOException e) {
            e.printStackTrace();
            frame = new JFrame("Welcome to Theater booking system");
            frame.setSize(1280, 768);
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        backgroundPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                TheatreBookingGUI movieBookingGUI = new TheatreBookingGUI();
                movieBookingGUI.setVisible(true);
                movieBookingGUI.setSeatsVisible();
            }
        });

        frame.add(backgroundPanel);
        frame.setLocationRelativeTo(null);
    }

    public void show() {
        frame.setVisible(true);
    }

    private class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        WelcomePage welcomePage = new WelcomePage();
        welcomePage.show();
    }
}
