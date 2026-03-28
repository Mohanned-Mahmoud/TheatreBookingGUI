
# 🎬 Theatre Booking GUI

A desktop application built with Java and Swing that simulates a visual theatre ticket booking system. The application allows users to navigate through different seating classes, visually select seats, calculate their total ticket cost in real-time, and manage their bookings.

---

## ✨ Features

* **Interactive Splash Screen (`WelcomePage`):** The application launches with a welcoming background image. Users simply click anywhere on the screen to enter the main booking interface.
* **Multiple Seating Classes:** Seats are categorized into three pricing tiers organized in a clean Tabbed Pane layout:
    * 🥇 **First Class:** 20 seats (4x5 grid) - $50 per seat
    * 🥈 **Second Class:** 40 seats (8x5 grid) - $15 per seat
    * 🥉 **Third Class:** 50 seats (10x5 grid) - $10 per seat
* **Visual Seat Selection:** Interactive seat icons. Unselected seats display a standard armchair graphic (`armchair.png`), which updates to a highlighted graphic (`selectedarmchair.png`) upon selection.
* **Real-time Cost Calculation:** As seats are toggled, the application dynamically updates the "Selected Seats" count and the "Total Price".
* **Manual Seat ID Entry:** Users can select or cancel specific seats without clicking by typing the Seat ID into a text field and pressing the respective action buttons.
* **🌙 Dark Mode:** A built-in toggle button allows users to switch the application's theme between Light and Dark modes instantly.
* **Booking Management:** * **Book:** Finalizes the transaction and displays a confirmation dialog with the booked seat IDs and total amount.
    * **Clear All Seats:** Instantly deselects all seats across all classes and resets the total price to $0.
    * **Cancel:** Deselect a seat using its ID.

---

## 📂 Project Structure

```text
TheatreBookingGUI/
├── src/
│   ├── TheatreBookingGUI.java    # Main application logic and user interface
│   ├── WelcomePage.java          # Splash screen and application entry point
│   ├── 8112707.jpg               # Background image for the Welcome Page
│   ├── armchair.png              # Icon for available/unselected seats
│   └── selectedarmchair.png      # Icon for selected seats
```

---

## 💻 Tech Stack

* **Language:** Java
* **GUI Framework:** Java Swing (`JFrame`, `JPanel`, `JButton`, `JTabbedPane`, etc.)
* **Graphics Handling:** `java.awt.Graphics2D` and `BufferedImage` for image scaling and rendering.

---

## 🚀 How to Run

### Prerequisites
* **Java Development Kit (JDK):** Ensure you have JDK 8 or higher installed on your system. 

### Execution Steps
1. **Clone the repository** to your local machine.
2. Open your terminal or command prompt.
3. **Navigate** to the `src` directory containing the `.java` and image files:
   ```bash
   cd path/to/TheatreBookingGUI/src
   ```
4. **Compile** the Java files:
   ```bash
   javac *.java
   ```
5. **Run** the application starting from the `WelcomePage`:
   ```bash
   java WelcomePage
   ```

*(Note: The application relies on `8112707.jpg`, `armchair.png`, and `selectedarmchair.png` being in the correct relative paths as specified in the source code. Running the program from within the `src` directory ensures `WelcomePage` can find `src/8112707.jpg` or load from the classpath.)*

---

## 🛠️ Usage Instructions

1. **Launch** the app and click the welcome screen to proceed.
2. Select your desired seating class from the top **Drop-down menu** to ensure the pricing updates correctly.
3. Click the specific tab (First, Second, or Third class) to view the seat layout.
4. **Click on the armchairs** to select/deselect them. 
5. Alternatively, type a seat number in the **"Enter Seat ID"** box and click **"Select Seat"**.
6. Toggle **"Dark Mode"** on the right side if preferred.
7. Click **"Book"** to complete your reservation.
