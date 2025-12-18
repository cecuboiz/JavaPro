// Main.java
public class Main {
    public static void main(String[] args) {
        // GUI-г Thread аюулгүй байдлаар эхлүүлэх
        javax.swing.SwingUtilities.invokeLater(() -> {
            KioskFrame frame = new KioskFrame();
            frame.setVisible(true);
        });
    }
}