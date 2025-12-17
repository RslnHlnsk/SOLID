import model.*;
import repository.*;
import service.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int SHOW_PRODUCTS = 1;
    private static final int FILTER_PRODUCTS = 2;
    private static final int ADD_PRODUCT_TO_CART = 3;
    private static final int VIEW_CART = 4;
    private static final int CREATE_ORDER = 5;
    private static final int SHOW_ORDERS = 6;
    private static final int RATE_PRODUCT = 7;
    private static final int SHOW_RECOMMENDATIONS = 8;
    private static final int PAY_ORDER = 9;
    private static final int TRACK_DELIVERY = 10;
    private static final int INITIATE_RETURN = 11;
    private static final int REPEAT_ORDER = 12;
    private static final int EXIT_MENU = 0;

    private static final int FILTER_BY_KEYWORD = 1;
    private static final int FILTER_BY_PRICE = 2;
    private static final int FILTER_BY_MANUFACTURER = 3;

    private static final ProductRepository productRepository = new InMemoryProductRepository();
    private static final FilteringService filteringService = new FilteringService();
    private static final RatingService ratingService = new RatingService();
    private static final RecommendationService recommendationService = new RecommendationService(productRepository);
    private static final OrderService orderService = new OrderService();

    // Новые сервисы
    private static final OrderPaymentService orderPaymentService = new OrderPaymentServiceImpl(orderService);
    private static final DeliveryTrackingService deliveryTrackingService = new DeliveryTrackingServiceImpl(orderService);
    private static final ReturnService returnService = new ReturnServiceImpl(orderService);
    private static final ReorderService reorderService = new ReorderServiceImpl(orderService);

    private static final Scanner scanner = new Scanner(System.in);
    private static final Cart cart = new Cart();

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в магазин!");

        boolean exit = false;
        while (!exit) {
            printMenu();
            int choice = readInt("Выберите пункт меню: ");
            switch (choice) {
                case SHOW_PRODUCTS:
                    showAllProducts();
                    break;
                case FILTER_PRODUCTS:
                    filterProducts();
                    break;
                case ADD_PRODUCT_TO_CART:
                    addProductToCart();
                    break;
                case VIEW_CART:
                    viewCart();
                    break;
                case CREATE_ORDER:
                    createOrder();
                    break;
                case SHOW_ORDERS:
                    showOrders();
                    break;
                case RATE_PRODUCT:
                    rateProduct();
                    break;
                case SHOW_RECOMMENDATIONS:
                    showRecommendations();
                    break;
                // Новые пункты
                case PAY_ORDER:
                    payOrder();
                    break;
                case TRACK_DELIVERY:
                    trackDelivery();
                    break;
                case INITIATE_RETURN:
                    initiateReturn();
                    break;
                case REPEAT_ORDER:
                    repeatOrder();
                    break;
                case EXIT_MENU:
                    exit = true;
                    System.out.println("Выход. До свидания и ждем вас снова!");
                    break;
                default:
                    System.out.println("Некорректный выбор");
            }
        }
    }

    private static void printMenu() {
        String menu = "\nМеню:\n" +
                "1. Посмотреть все товары\n" +
                "2. Фильтровать товары\n" +
                "3. Добавить товар в корзину\n" +
                "4. Посмотреть корзину\n" +
                "5. Оформить заказ\n" +
                "6. Посмотреть заказы\n" +
                "7. Оценить товар\n" +
                "8. Посмотреть рекомендации\n" +
                "9. Оплатить заказ\n" +
                "10. Отследить доставку\n" +
                "11. Оформить возврат\n" +
                "12. Повторить заказ\n" +
                "0. Выход\n";
        System.out.print(menu);
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
            case FILTER_BY_KEYWORD:
                String keyword = readString("Введите ключевое слово: ");
                products = filteringService.filterByKeyword(products, keyword);
                break;
            case FILTER_BY_PRICE:
                double minPrice = readDouble("Мин. цена: ");
                double maxPrice = readDouble("Макс. цена: ");
                products = filteringService.filterByPriceRange(products, minPrice, maxPrice);
                break;
            case FILTER_BY_MANUFACTURER:
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
            Order order = orderService.createOrder(cart.getProducts());
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

    private static void payOrder() {
        int orderId = readInt("Введите номер заказа для оплаты: ");
        boolean success = orderPaymentService.payOrder(orderId);
        if (success) {
            System.out.println("Заказ №" + orderId + " оплачен и передан в доставку.");
        } else {
            System.out.println("Заказ с таким номером не найден или уже оплачен.");
        }
    }

    private static void trackDelivery() {
        int orderId = readInt("Введите номер заказа для отслеживания: ");
        OrderStatus newStatus = deliveryTrackingService.getDeliveryStatus(orderId);
        if (newStatus != null) {
            System.out.println("Статус заказа №" + orderId + ": " + newStatus);
        } else {
            System.out.println("Заказ с таким номером не найден или не оплачен.");
        }
    }

    private static void initiateReturn() {
        int orderId = readInt("Введите номер заказа для оформления возврата: ");
        boolean success = returnService.initiateReturn(orderId);
        if (success) {
            System.out.println("Возврат по заказу №" + orderId + " оформлен.");
        } else {
            System.out.println("Заказ с таким номером не найден или не оплачен.");
        }
    }

    private static void repeatOrder() {
        int oldOrderId = readInt("Введите номер заказа для повторения: ");
        Order newOrder = reorderService.repeatOrder(oldOrderId);
        if (newOrder != null) {
            System.out.println("Создан повторный заказ №" + newOrder.getId());
        } else {
            System.out.println("Заказ с таким номером не найден или не может быть повторен.");
        }
    }
}