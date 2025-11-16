public class Queue<T> {

    private Object[] elements;
    private int head;   
    private int tail;   
    private int size;

    public Queue(int capacity) {
        elements = new Object[capacity];
        head = 0;
        tail = 0;
        size = 0;
    }

    public void enqueue(T value) {
        if (size >= elements.length) return;
        elements[tail] = value;
        tail = (tail + 1) % elements.length;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (size == 0) return null;
        T item = (T) elements[head];
        head = (head + 1) % elements.length;
        size--;
        return item;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (size == 0) return null;
        return (T) elements[head];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}