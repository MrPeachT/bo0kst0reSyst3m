public class BinarySearch {

    /**
     * Generic binary search for any Comparable type.
     * Array must be sorted in ascending order.
     *
     * @param arr    sorted array
     * @param target value to search for
     * @param <T>    type that implements Comparable
     * @return index of target or -1 if not found
     */
    public static <T extends Comparable<T>> int binarySearch(T[] arr, T target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int cmp = target.compareTo(arr[mid]);

            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }

    public static int binarySearchOrderById(Order[] arr, int orderId) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int midId = arr[mid].getOrderId();

            if (orderId == midId) {
                return mid;
            } else if (orderId < midId) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }
}