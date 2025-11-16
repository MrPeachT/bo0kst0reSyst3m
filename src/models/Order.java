public class Order implements Comparable<Order> {

    private int orderId;
    private String customerName;
    private String shippingAddress;

    private Book[] books;        
    private int[] quantities;   
    private int size;           

    private String status;     

    public Order(int orderId, String customerName, String shippingAddress) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;

        this.books = new Book[20];
        this.quantities = new int[20];
        this.size = 0;

        this.status = "PENDING";
    }

    public void addItem(Book book, int qty) {
        if (size >= 20) return;
        books[size] = book;
        quantities[size] = qty;
        size++;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getStatus() {
        return status;
    }

    public void markProcessed() {
        this.status = "PROCESSED";
    }

    public int getItemCount() {
        return size;
    }

    public Book[] getBooks() {
        Book[] actual = new Book[size];
        System.arraycopy(books, 0, actual, 0, size);
        return actual;
    }

    public int[] getQuantities() {
        int[] actual = new int[size];
        System.arraycopy(quantities, 0, actual, 0, size);
        return actual;
    }

    @Override
    public int compareTo(Order other) {
        return Integer.compare(this.orderId, other.orderId);
    }

    @Override
    public String toString() {
        return "[Order " + orderId + "] " + customerName + " - " + status;
    }
}