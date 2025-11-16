public class Stack<T> {

    private Object[] elements;
    private int top;

    public Stack(int capacity) {
        elements = new Object[capacity];
        top = 0;
    }

    public void push(T value) {
        if (top >= elements.length) return;
        elements[top] = value;
        top++;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (top == 0) return null;
        top--;
        return (T) elements[top];
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (top == 0) return null;
        return (T) elements[top - 1];
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public int size() {
        return top;
    }
}