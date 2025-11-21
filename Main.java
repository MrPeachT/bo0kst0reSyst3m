import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static Book[] catalog;
    private static Queue<Order> pendingOrders = new Queue<>(100);
    private static Stack<Order> processedOrders = new Stack<>(100);
    private static BinaryTree<Order> processedOrdersTree = new BinaryTree<>();

    private static int nextOrderId = 1;

    public static void main(String[] args) {
        catalog = createSampleBooks();
        Sort.mergeSort(catalog);

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    listBooks();
                    break;
                case 2:
                    placeNewOrder();
                    break;
                case 3:
                    processNextOrder();
                    break;
                case 4:
                    trackOrderById();
                    break;
                case 5:
                    showProcessedOrdersLifo();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting system.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }

            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("======================================");
        System.out.println("           ONLINE BOOKSTORE");
        System.out.println("======================================");
        System.out.println("1. List all books");
        System.out.println("2. Place a new order");
        System.out.println("3. Process next pending order");
        System.out.println("4. Track an order by ID");
        System.out.println("5. Show processed orders");
        System.out.println("0. Exit");
        System.out.println("======================================");
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("Enter a valid number: ");
        }
        int value = scanner.nextInt();
        scanner.nextLine(); 
        return value;
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static Book[] createSampleBooks() {
        return new Book[] {
            new Book(1, "Dune", "Frank Herbert", 9.99),
            new Book(2, "Neuromancer", "William Gibson", 8.49),
            new Book(3, "Foundation", "Isaac Asimov", 7.99),
            new Book(4, "Hyperion", "Dan Simmons", 10.50),
            new Book(5, "Snow Crash", "Neal Stephenson", 8.99),
            new Book(6, "The Martian", "Andy Weir", 6.99),
            new Book(7, "Star Trek", "Grek Cox", 9.99),
        };
    }

    private static void listBooks() {
        System.out.println("Available books:");
        for (Book b : catalog) {
            System.out.println("  " + b);
        }
    }

    private static Book findBookById(int id) {
        for (Book b : catalog) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    private static void showProcessedOrdersLifo() {
        if (processedOrders.size() == 0) {
            System.out.println("No processed orders.");
            return;
        }

        int n = processedOrders.size();
        Order[] arr = new Order[n];

        int index = 0;
        while (!processedOrders.isEmpty()) {
            Order o = processedOrders.pop();
            arr[index] = o;      
            index++;
        }

        System.out.println("Processed orders:");
    
        for (int i = 0; i < n; i++) {
            System.out.println(arr[i]);
        }

        for (int i = n - 1; i >= 0; i--) {
            processedOrders.push(arr[i]);
        }
    }

    private static void placeNewOrder() {
        System.out.println("Placing new order...");

        String name = readLine("Customer name: ");
        String address = readLine("Shipping address: ");

        Order order = new Order(nextOrderId++, name, address);

        boolean adding = true;
        while (adding) {
            listBooks();
            int bookId = readInt("Enter book ID to add (0 to finish): ");

            if (bookId == 0) {
                adding = false;
                break;
            }

            Book book = findBookById(bookId);
            if (book == null) {
                System.out.println("Book not found.");
                continue;
            }

            int qty = readInt("Quantity: ");
            if (qty <= 0) {
                System.out.println("Quantity must be positive.");
                continue;
            }

            order.addItem(book, qty);
            System.out.println("Added to order.");
        }

        if (order.getItemCount() == 0) {
            System.out.println("No items in order. Order discarded.");
            return;
        }

        pendingOrders.enqueue(order);
        System.out.println("Order " + order.getOrderId() + " added to pending queue (FIFO).");
    }

    private static void processNextOrder() {
        if (pendingOrders.isEmpty()) {
            System.out.println("No pending orders in queue.");
            return;
        }

        Order order = pendingOrders.dequeue();
        order.markProcessed();

        processedOrders.push(order);
        processedOrdersTree.insert(order);

        System.out.println("Processed order " + order.getOrderId() + ":");
        printOrderDetails(order, true); 
    }

    private static void trackOrderById() {
        if (processedOrders.size() == 0) {
            System.out.println("There are no processed orders to search.");
            return;
        }

        int id = readInt("Enter order ID to search: ");

        int n = processedOrders.size();
        Order[] arr = new Order[n];

        int index = n - 1;
        while (!processedOrders.isEmpty()) {
            Order o = processedOrders.pop();
            arr[index] = o;
            index--;
        }

        for (int i = 0; i < n; i++) {
            processedOrders.push(arr[i]);
        }

        Sort.mergeSort(arr);

        int pos = BinarySearch.binarySearchOrderById(arr, id);

        if (pos == -1) {
            System.out.println("Order " + id + " not found among processed orders.");
        } else {
            Order found = arr[pos];
            System.out.println("Order found:");
            printOrderDetails(found, true);
        }
    }

    private static void showProcessedOrdersSorted() {
        System.out.println("Processed orders:");

        processedOrdersTree.inOrder(order -> {
            System.out.println(order);
        });
    }

    private static void printOrderDetails(Order order, boolean sortItems) {
        System.out.println(order);
        System.out.println("Customer: " + order.getCustomerName());
        System.out.println("Address:  " + order.getShippingAddress());

        Book[] books = order.getBooks();
        int[] quantities = order.getQuantities();

        if (sortItems && books.length > 1) {
            sortBooksWithQuantities(books, quantities);
        }

        System.out.println("Items:");
        double total = 0.0;
        for (int i = 0; i < books.length; i++) {
            Book b = books[i];
            int q = quantities[i];
            double line = b.getPrice() * q;
            total += line;
            System.out.printf("  %dx %s (%.2f) = %.2f%n",
                q, b.getTitle(), b.getPrice(), line);
        }
        System.out.printf("Total: %.2f%n", total);
    }

    private static void sortBooksWithQuantities(Book[] books, int[] quantities) {
        int n = books.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (books[j].compareTo(books[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Book tmpB = books[i];
                books[i] = books[minIndex];
                books[minIndex] = tmpB;

                int tmpQ = quantities[i];
                quantities[i] = quantities[minIndex];
                quantities[minIndex] = tmpQ;
            }
        }
    }
}