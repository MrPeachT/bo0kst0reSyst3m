import java.util.function.Consumer;

public class BinaryTree<T extends Comparable<T>> {

    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        Node(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node<T> root;

    public BinaryTree() {
        root = null;
    }

    public void insert(T value) {
        root = insertRecursive(root, value);
    }

    private Node<T> insertRecursive(Node<T> node, T value) {
        if (node == null) return new Node<>(value);

        int cmp = value.compareTo(node.value);

        if (cmp < 0) {
            node.left = insertRecursive(node.left, value);
        } else if (cmp > 0) {
            node.right = insertRecursive(node.right, value);
        }
        return node;
    }

    public boolean contains(T value) {
        return containsRecursive(root, value);
    }

    private boolean containsRecursive(Node<T> node, T value) {
        if (node == null) return false;

        int cmp = value.compareTo(node.value);

        if (cmp == 0) return true;
        if (cmp < 0) return containsRecursive(node.left, value);
        return containsRecursive(node.right, value);
    }

    public void inOrder(Consumer<T> action) {
        inOrderRecursive(root, action);
    }

    private void inOrderRecursive(Node<T> node, Consumer<T> action) {
        if (node == null) return;

        inOrderRecursive(node.left, action);
        action.accept(node.value);
        inOrderRecursive(node.right, action);
    }
}