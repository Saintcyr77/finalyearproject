package Jacob.Sorts;

public class HeapSort implements Runnable {
    private Integer[] toBeSorted;
    private VisualizerFrame frame;

    public HeapSort(Integer[] toBeSorted, VisualizerFrame frame) {
        this.toBeSorted = toBeSorted;
        this.frame = frame;
    }

    public void run() {
        sort();
        SortingVisualizer.isSorting = false;
    }

    public void sort() {
        int n = toBeSorted.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(toBeSorted, n, i);
            frame.reDrawArray(toBeSorted, i);
            sleep();
        }

        // Extract elements from the heap one by one
        for (int i = n - 1; i > 0; i--) {
            // Swap the root (max element) with the last element
            int temp = toBeSorted[0];
            toBeSorted[0] = toBeSorted[i];
            toBeSorted[i] = temp;
            frame.reDrawArray(toBeSorted, i);

            // Heapify the reduced heap
            heapify(toBeSorted, i, 0);
            sleep();
        }

        // The array is now sorted
        frame.reDrawArray(toBeSorted);
    }

    private void heapify(Integer[] arr, int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // Left child
        int right = 2 * i + 2; // Right child

        // If left child is larger than root
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // If largest is not the root
        if (largest != i) {
            // Swap the elements
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    private void sleep() {
        try {
            Thread.sleep(SortingVisualizer.sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
