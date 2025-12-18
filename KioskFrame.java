import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class KioskFrame extends JFrame {
    // 1. Компонентууд
    private JPanel menuPanel;      // Цэсний товчлуурууд байх хэсэг
    private JTextArea orderList;   // Сагс (сонгосон зүйлс харагдах)
    private JLabel totalLabel;     // Нийт үнэ
    private JButton orderButton;   // Захиалах товч
    private JButton clearButton;   // Сагс хоослох
    private JButton historyButton; // Түүх харах товч

    // 2. Өгөгдөл
    private ArrayList<MenuItem> cart; // Сагсанд хийсэн бараанууд
    private int totalPrice = 0;

    // Өнөөдрийн бүх захиалгыг хадгалах жагсаалт
    private ArrayList<String> orderHistory = new ArrayList<>();
    private int orderCount = 0; // хэд дэх захиалга вэ гэдгийг тоолно

    public KioskFrame() {
        setTitle("Cafe Kiosk System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cart = new ArrayList<>();

        // --- ДЭЭД ХЭСЭГ: Цэсний товчлуурууд ---
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(2, 2, 10, 10)); // 2 мөр, 2 багана
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Цэс үүсгэх
        addMenuButton("Americano", 2500);
        addMenuButton("Latte", 3500);
        addMenuButton("Espresso", 2000);
        addMenuButton("Cheesecake", 4500);

        add(menuPanel, BorderLayout.CENTER);

        // --- БАРУУН ХЭСЭГ: Сагс ба Тооцоо ---
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(250, 0));

        orderList = new JTextArea();
        orderList.setEditable(false);
        orderList.setText("--- 주문 내역 ---\n");
        rightPanel.add(new JScrollPane(orderList), BorderLayout.CENTER);

        // Доод хэсэг: Нийт дүн ба Товчлуур
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(4, 1)); // 4 мөр: Нийт дүн + 3 товч

        totalLabel = new JLabel("총액: 0₮", SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        orderButton = new JButton("주문하기");
        clearButton = new JButton("비우기");
        historyButton = new JButton("오늘 주문 내역");

        // "Захиалах" товчны үйлдэл
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cart.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "장바구니가 비어 있습니다!");
                } else {
                    // Эхлээд энэ захиалгыг түүхэнд хадгална
                    saveCurrentOrderToHistory();

                    // Дараа нь хэрэглэгчид мессэж харуулна
                    JOptionPane.showMessageDialog(
                            null,
                            "주문이 완료되었습니다!\n총액: " + totalPrice + "₮"
                    );

                    // Сагсыг цэвэрлэнэ
                    clearCart();
                }
            }
        });

        // "Цэвэрлэх" товчны үйлдэл
        clearButton.addActionListener(e -> clearCart());

        // "Түүх харах" товчны үйлдэл
        historyButton.addActionListener(e -> showOrderHistory());

        bottomPanel.add(totalLabel);
        bottomPanel.add(orderButton);
        bottomPanel.add(clearButton);
        bottomPanel.add(historyButton);

        rightPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.EAST);
    }

    // Цэсний товчлуур нэмэх функц
    private void addMenuButton(String name, int price) {
        JButton btn = new JButton(name + " (" + price + "₮)");
        MenuItem item = new MenuItem(name, price);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart(item);
            }
        });

        menuPanel.add(btn);
    }

    // Сагсанд бараа нэмэх логик
    private void addToCart(MenuItem item) {
        cart.add(item);
        totalPrice += item.getPrice();
        updateDisplay();
    }

    // Сагс цэвэрлэх логик
    private void clearCart() {
        cart.clear();
        totalPrice = 0;
        updateDisplay();
    }

    // Дэлгэц шинэчлэх
    private void updateDisplay() {
        orderList.setText("--- 주문 내역 ---\n");
        for (MenuItem item : cart) {
            orderList.append(item.getName() + " - " + item.getPrice() + "₮\n");
        }
        totalLabel.setText("총액: " + totalPrice + "₮");
    }

    // Одоогийн сагсны мэдээллийг түүхэнд хадгалах
    private void saveCurrentOrderToHistory() {
        orderCount++; // хэд дэх захиалга вэ

        StringBuilder sb = new StringBuilder();
        sb.append(orderCount).append("번째 주문: ");

        for (MenuItem item : cart) {
            sb.append(item.getName())
                    .append("(")
                    .append(item.getPrice())
                    .append("₮), ");
        }

        sb.append(" 총액: ").append(totalPrice).append("₮");

        orderHistory.add(sb.toString());
    }

    // Өнөөдрийн бүх захиалгыг харуулах
    private void showOrderHistory() {
        if (orderHistory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "오늘 등록된 주문이 없습니다.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("--- 오늘 주문 내역 ---\n\n");
        for (String line : orderHistory) {
            sb.append(line).append("\n");
        }

        JOptionPane.showMessageDialog(this, sb.toString());
    }
}
