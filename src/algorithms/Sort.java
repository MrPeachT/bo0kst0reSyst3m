public class Sort {

    public static <T extends Comparable<T>> void selectionSort(T[] arr) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (arr[j].compareTo(arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            T temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    public static <T extends Comparable<T>> void insertionSort(T[] arr) {
        int n = arr.length;

        for (int i = 1; i < n; i++) {
            T key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j].compareTo(key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }

    public static <T extends Comparable<T>> void mergeSort(T[] arr) {
        if (arr.length <= 1) return;
        mergeSortRecursive(arr, 0, arr.length - 1);
    }

    private static <T extends Comparable<T>> void mergeSortRecursive(T[] arr, int left, int right) {
        if (left >= right) return;

        int mid = (left + right) / 2;

        mergeSortRecursive(arr, left, mid);
        mergeSortRecursive(arr, mid + 1, right);

        merge(arr, left, mid, right);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> void merge(T[] arr, int left, int mid, int right) {
        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        Object[] leftArr = new Object[leftSize];
        Object[] rightArr = new Object[rightSize];

        for (int i = 0; i < leftSize; i++) leftArr[i] = arr[left + i];
        for (int i = 0; i < rightSize; i++) rightArr[i] = arr[mid + 1 + i];

        int i = 0, j = 0, k = left;

        while (i < leftSize && j < rightSize) {
            T leftVal = (T) leftArr[i];
            T rightVal = (T) rightArr[j];

            if (leftVal.compareTo(rightVal) <= 0) {
                arr[k] = leftVal;
                i++;
            } else {
                arr[k] = rightVal;
                j++;
            }
            k++;
        }

        while (i < leftSize) {
            arr[k] = (T) leftArr[i];
            i++;
            k++;
        }

        while (j < rightSize) {
            arr[k] = (T) rightArr[j];
            j++;
            k++;
        }
    }
}