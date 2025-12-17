package model;

public class Product {
    private final int id;
    private String name;
    private String manufacturer;
    private double price;
    private int rating; // от 1 до 5

    public Product(int id, String name, String manufacturer, double price) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.rating = 0; // по умолчанию
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getManufacturer() { return manufacturer; }
    public double getPrice() { return price; }
    public int getRating() { return rating; }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("Рейтинг от 1 до 5");
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, %s, Производитель: %s, Цена: %.2f, Рейтинг: %d", id, name, manufacturer, price, rating);
    }
}
