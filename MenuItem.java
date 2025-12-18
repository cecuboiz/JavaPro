// MenuItem.java
public class MenuItem {
    private String name; // Барааны нэр
    private int price;   // Үнэ

    public MenuItem(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " (" + price + "₮)";
    }
}