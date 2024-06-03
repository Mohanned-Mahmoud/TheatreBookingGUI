import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TheatreBookingGUI extends JFrame {
    private JComboBox<String> ClassSelect;
    private JLabel countLabel;
    private JLabel totalLabel;
    private List<JButton> firstClassSeatButtons;
    private List<JButton> secondClassSeatButtons;
    private List<JButton> thirdClassSeatButtons;
    private JButton bookButton;
    private JButton selectButton;
    private JButton cancelButton;
    private JButton clearButton;
    private JButton exitButton;
    private JToggleButton darkModeToggle;
    private int total = 0;
    private int count = 0;
    private BufferedImage selectedSeatImage;
    private BufferedImage seatImage;
    private JTextField seatIdTextField;

    public TheatreBookingGUI() {
        setTitle("Theatre Booking");
        setSize(1100, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel moviePanel = new JPanel();
        moviePanel.setLayout(new FlowLayout());
        moviePanel.add(new JLabel("Select Class:"));
        ClassSelect = new JComboBox<>();
        ClassSelect.addItem("First Class ($50)");
        ClassSelect.addItem("Second Class ($15)");
        ClassSelect.addItem("Third Class ($10)");
        moviePanel.add(ClassSelect);
        add(moviePanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);

        firstClassSeatButtons = new ArrayList<>();
        secondClassSeatButtons = new ArrayList<>();
        thirdClassSeatButtons = new ArrayList<>();

        JPanel firstClassPanel = createClassPanel("First Class", 4, 5, firstClassSeatButtons);
        tabbedPane.addTab("First Class", firstClassPanel);

        JPanel secondClassPanel = createClassPanel("Second Class", 8, 5, secondClassSeatButtons);
        tabbedPane.addTab("Second Class", secondClassPanel);

        JPanel thirdClassPanel = createClassPanel("Third Class", 10, 5, thirdClassSeatButtons);
        tabbedPane.addTab("Third Class", thirdClassPanel);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout());
        countLabel = new JLabel("Selected Seats: 0");
        totalLabel = new JLabel("Total: $0");
        infoPanel.add(countLabel);
        infoPanel.add(totalLabel);
        add(infoPanel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        bookButton = new JButton("Book");
        bookButton.addActionListener(e -> {
            bookSeats();
            resetSeats();
        });
        buttonPanel.add(bookButton);

        selectButton = new JButton("Select Seat");
        selectButton.addActionListener(e -> selectSeatById());
        buttonPanel.add(selectButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> cancelSeatWithID());
        buttonPanel.add(cancelButton);

        clearButton = new JButton("Clear All Seats");
        clearButton.addActionListener(e -> {
            clearAllSeats(firstClassSeatButtons);
            clearAllSeats(secondClassSeatButtons);
            clearAllSeats(thirdClassSeatButtons);
            count = 0;
            total = 0;
            updateSelectedCount();
        });
        buttonPanel.add(clearButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitButton);

        JPanel seatIdPanel = new JPanel();
        seatIdPanel.setLayout(new FlowLayout());
        seatIdPanel.add(new JLabel("Enter Seat ID:"));
        seatIdTextField = new JTextField(5);
        seatIdPanel.add(seatIdTextField);
        buttonPanel.add(seatIdPanel);

        darkModeToggle = new JToggleButton("Dark Mode");
        darkModeToggle.addActionListener(e -> toggleDarkMode());
        buttonPanel.add(darkModeToggle);

        add(buttonPanel, BorderLayout.EAST);

        loadImages();
    }

    private void loadImages() {
        try {
            selectedSeatImage = loadImage("selectedarmchair.png");
            seatImage = loadImage("armchair.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage loadImage(String path) throws IOException {
        URL imageUrl = getClass().getResource(path);
        if (imageUrl != null) {
            BufferedImage image = ImageIO.read(imageUrl);
            return resizeImage(image, 50, 50);
        } else {
            System.err.println("Image not found: " + path);
            return null;
        }
    }

    private JPanel createClassPanel(String className, int rows, int cols, List<JButton> seatButtonsList) {
        JPanel classPanel = new JPanel(new BorderLayout());
        JPanel seatPanel = new JPanel(new GridLayout(rows, cols));
        for (int i = 0; i < rows * cols; i++) {
            JButton seat = createSeatButton();
            seatButtonsList.add(seat);
            seatPanel.add(seat);
        }
        classPanel.add(seatPanel, BorderLayout.CENTER);
        return classPanel;
    }

    public JButton createSeatButton() {
        JButton seatButton = new JButton();
        seatButton.setPreferredSize(new Dimension(50, 50));
        seatButton.setContentAreaFilled(false);
        seatButton.setBorderPainted(false);
        if (seatImage != null) {
            seatButton.setIcon(new ImageIcon(seatImage, "unselected"));
        }
        return seatButton;
    }

    private void selectSeatById() {
        String seatId = seatIdTextField.getText();
        if (!seatId.isEmpty()) {
            try {
                int seatIndex = Integer.parseInt(seatId) - 1;
                List<JButton> seatButtonsList = getCurrentSeatButtonsList();
                if (seatIndex >= 0 && seatIndex < seatButtonsList.size()) {
                    if ("selected".equals(((ImageIcon) seatButtonsList.get(seatIndex).getIcon()).getDescription())) {
                        JOptionPane.showMessageDialog(this, "Seat is already selected", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    JButton selectedSeat = seatButtonsList.get(seatIndex);
                    toggleSeat(selectedSeat);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid seat ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid seat ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter seat ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void toggleSeat(JButton seat) {
        ImageIcon icon = (ImageIcon) seat.getIcon();
        if ("selected".equals(icon.getDescription())) {
            seat.setIcon(new ImageIcon(seatImage, "unselected"));
            if (count > 0) {
                count--;
                total -= getPrice(ClassSelect.getSelectedIndex());
            } else {
                count = 0;
                total = 0;
            }
        } else {
            seat.setIcon(new ImageIcon(selectedSeatImage, "selected"));
            count++;
            total += getPrice(ClassSelect.getSelectedIndex());
        }
        updateSelectedCount();
    }

    private List<JButton> getCurrentSeatButtonsList() {
        switch (ClassSelect.getSelectedIndex()) {
            case 0:
                return firstClassSeatButtons;
            case 1:
                return secondClassSeatButtons;
            case 2:
                return thirdClassSeatButtons;
            default:
                return new ArrayList<>();
        }
    }

    private int getPrice(int classIndex) {
        switch (classIndex) {
            case 0:
                return 50;
            case 1:
                return 15;
            case 2:
                return 10;
            default:
                return 0;
        }
    }

    private void updateSelectedCount() {
        countLabel.setText("Selected Seats: " + count);
        totalLabel.setText("Total: $" + total);
    }

    private void resetSeats() {
        count = 0;
        total = 0;
        updateSelectedCount();
    }

    private void clearAllSeats(List<JButton> seatButtonsList) {
        for (JButton seat : seatButtonsList) {
            seat.setIcon(new ImageIcon(seatImage, "unselected"));
        }
        count = 0;
        total = 0;
        updateSelectedCount();
    }

    private void bookSeats() {
        StringBuilder selectedSeats = new StringBuilder();
        for (List<JButton> seatList : List.of(firstClassSeatButtons, secondClassSeatButtons, thirdClassSeatButtons)) {
            for (JButton seat : seatList) {
                if ("selected".equals(((ImageIcon) seat.getIcon()).getDescription())) {
                    selectedSeats.append(seatList.indexOf(seat) + 1).append(", ");
                }
            }
        }
        if (selectedSeats.length() > 0) {
            selectedSeats.setLength(selectedSeats.length() - 2);
            JOptionPane.showMessageDialog(this, "Booking Successful! Selected seats: " + selectedSeats + ", the total amount is $" + total, "Booking Confirmation", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No seats selected", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelSeatWithID() {
        String seatId = seatIdTextField.getText();
        if (!seatId.isEmpty()) {
            try {
                int seatIndex = Integer.parseInt(seatId) - 1;
                List<JButton> seatButtonsList = getCurrentSeatButtonsList();
                if (seatIndex >= 0 && seatIndex < seatButtonsList.size()) {
                    JButton selectedSeat = seatButtonsList.get(seatIndex);
                    if ("selected".equals(((ImageIcon) selectedSeat.getIcon()).getDescription())) {
                        toggleSeat(selectedSeat);
                    } else {
                        JOptionPane.showMessageDialog(this, "Seat is not selected", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid seat ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid seat ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter seat ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setSeatsVisible() {
        for (JButton seat : firstClassSeatButtons) {
            seat.setIcon(new ImageIcon(seatImage, "unselected"));
        }
        for (JButton seat : secondClassSeatButtons) {
            seat.setIcon(new ImageIcon(seatImage, "unselected"));
        }
        for (JButton seat : thirdClassSeatButtons) {
            seat.setIcon(new ImageIcon(seatImage, "unselected"));
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(resultingImage, 0, 0, null);
        g2d.dispose();
        return outputImage;
    }

    private void toggleDarkMode() {
        boolean isDarkMode = darkModeToggle.isSelected();
        Color backgroundColor;
        Color textColor;
        if (isDarkMode) {
             backgroundColor = Color.DARK_GRAY;
             textColor = Color.WHITE;
        } else {
            backgroundColor = Color.LIGHT_GRAY;
            textColor = Color.BLACK;
        }

        getContentPane().setBackground(backgroundColor);
        for (Component component : getContentPane().getComponents()) {
            setComponentColors(component, backgroundColor, textColor);
        }
    }

    private void setComponentColors(Component component, Color backgroundColor, Color textColor) {
        if (component instanceof JPanel || component instanceof JTabbedPane) {
            component.setBackground(backgroundColor);
            for (Component child : ((Container) component).getComponents()) {
                setComponentColors(child, backgroundColor, textColor);
            }
        } else if (component instanceof JLabel) {
            component.setForeground(textColor);
        } else if (component instanceof JButton || component instanceof JTextField) {
            component.setBackground(backgroundColor);
            component.setForeground(textColor);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomePage welcomePage = new WelcomePage();
            welcomePage.show();
        });
    }
}
