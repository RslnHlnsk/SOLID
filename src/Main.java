import model.*;
import repository.*;
import service.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final ProductRepository productRepository = new InMemoryProductRepository();
    private static final FilteringService filteringService = new FilteringService();
    private static final RatingService ratingService = new RatingService();
    private static final RecommendationService recommendationService = new RecommendationService(productRepository);
    private static final OrderService orderService = new OrderService();

    private static final Scanner scanner = new Scanner(System.in);
    private static final Cart cart = new Cart();

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в магазин!");

        boolean exit = false;
        while (!exit) {
            printMenu();
            int choice = readInt("Выберите пункт меню: ");
            switch (choice) {
                case 1:
                    showAllProducts();
                    break;
                case 2:
                    filterProducts();
                    break;
                case 3:
                    addProductToCart();
                    break;
                case 4:
                    viewCart();
                    break;
                case 5:
                    createOrder();
                    break;
                case 6:
                    showOrders();
                    break;
                case 7:
                    rateProduct();
                    break;
                case 8:
                    showRecommendations();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Выход. До свидания!");
                    break;
                default:
                    System.out.println("Некорректный выбор");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nМеню:");
        System.out.println("1. Показать все товары");
        System.out.println("2. Фильтрация товаров");
        System.out.println("3. Добавить товар в корзину");
        System.out.println("4. Посмотреть корзину");
        System.out.println("5. Оформить заказ");
        System.out.println("6. Посмотреть заказы");
        System.out.println("7. Оценить товар");
        System.out.println("8. Посмотреть рекомендации");
        System.out.println("0. Выход");
    }

    private static void showAllProducts() {
        List<Product> products = productRepository.getAllProducts();
        System.out.println("Доступные товары:");
        for (Product p : products) {
            System.out.println(p);
        }
    }

    private static void filterProducts() {
        System.out.println("Фильтр по: 1. ключевому слову, 2. цене, 3. производителю");
        int filterChoice = readInt("Выберите фильтр: ");
        List<Product> products = productRepository.getAllProducts();

        switch (filterChoice) {
            case 1:
                String keyword = readString("Введите ключевое слово: ");
                products = filteringService.filterByKeyword(products, keyword);
                break;
            case 2:
                double minPrice = readDouble("Мин. цена: ");
                double maxPrice = readDouble("Макс. цена: ");
                products = filteringService.filterByPriceRange(products, minPrice, maxPrice);
                break;
            case 3:
                String manufacturer = readString("Введите производителя: ");
                products = filteringService.filterByManufacturer(products, manufacturer);
                break;
            default:
                System.out.println("Некорректный выбор");
                return;
        }
        System.out.println("Результат фильтрации:");
        for (Product p : products) {
            System.out.println(p);
        }
    }

    private static void addProductToCart() {
        int id = readInt("Введите ID товара для добавления: ");
        Product product = productRepository.getProductById(id);
        if (product != null) {
            cart.addProduct(product);
            System.out.println("Товар добавлен в корзину");
        } else {
            System.out.println("Товар не найден");
        }
    }

    private static void viewCart() {
        System.out.println("Корзина:");
        for (Product p : cart.getProducts()) {
            System.out.println(p);
        }
        System.out.printf("Общая сумма: %.2f\n", cart.getTotalPrice());
    }

    private static void createOrder() {
        try {
            Order order = orderService.createOrder(cart);
            System.out.println("Создан заказ: " + order);
            cart.getProducts().clear(); // очистка корзины
        } catch (IllegalStateException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void showOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("Нет заказов");
        } else {
            for (Order o : orders) {
                System.out.println(o);
            }
        }
    }

    private static void rateProduct() {
        int id = readInt("Введите ID товара для оценки: ");
        Product product = productRepository.getProductById(id);
        if (product != null) {
            int rating = readInt("Введите рейтинг (1-5): ");
            try {
                ratingService.rateProduct(product, rating);
                System.out.println("Рейтинг обновлён");
            } catch (IllegalArgumentException e) {

                System.out.println("Ошибка: " + e.getMessage());
            }
        } else {
            System.out.println("Товар не найден");
        }
    }

    private static void showRecommendations() {
        int id = readInt("Введите ID товара для рекомендаций: ");
        Product product = productRepository.getProductById(id);
        if (product != null) {
            List<Product> recs = recommendationService.getRecommendations(product);
            System.out.println("Рекомендуемые товары:");
            for (Product p : recs) {
                System.out.println(p);
            }
        } else {
            System.out.println("Товар не найден");
        }
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Введите число: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Введите число: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }
}