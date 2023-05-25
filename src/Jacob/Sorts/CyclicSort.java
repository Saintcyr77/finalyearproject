package Jacob.Sorts;

public class CyclicSort implements Runnable {
    private Integer[] toBeSorted;
    private VisualizerFrame frame;
    private boolean fast;

    public CyclicSort(Integer[] toBeSorted, VisualizerFrame frame, boolean fast) {
        this.toBeSorted = toBeSorted;
        this.frame = frame;
        this.fast = fast;
    }

    public void run() {
        if (fast) {
            sortFast();
        } else {
            sortSlow();
        }
        SortingVisualizer.isSorting = false;
    }

    public void sortFast() {
        int n = toBeSorted.length;
        for (int i = 0; i < n - 1; i++) {
            int item = toBeSorted[i];
            int j = i;
            for (int k = i + 1; k < n; k++) {
                if (toBeSorted[k] < item) {
                    j++;
                }
            }
            if (j != i) {
                while (item == toBeSorted[j]) {
                    j++;
                }
                int temp = toBeSorted[j];
                toBeSorted[j] = item;
                item = temp;
                frame.reDrawArray(toBeSorted, i);
                try {
                    Thread.sleep(SortingVisualizer.sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (j != i) {
                j = i;
                for (int k = i + 1; k < n; k++) {
                    if (toBeSorted[k] < item) {
                        j++;
                    }
                }
                while (item == toBeSorted[j]) {
                    j++;
                }
                int temp = toBeSorted[j];
                toBeSorted[j] = item;
                item = temp;
                frame.reDrawArray(toBeSorted, i);
                try {
                    Thread.sleep(SortingVisualizer.sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sortSlow() {
        int n = toBeSorted.length;
        for (int i = 0; i < n - 1; i++) {
            int item = toBeSorted[i];
            int j = i;
            for (int k = i + 1; k < n; k++) {
                if (toBeSorted[k] < item) {
                    j++;
                }
            }
            if (j != i) {
                while (item == toBeSorted[j]) {
                    j++;
                }
                int temp = toBeSorted[j];
                toBeSorted[j] = item;
                item = temp;
                frame.reDrawArray(toBeSorted, i, j);
                try {
                    Thread.sleep(SortingVisualizer.sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (j != i) {
                j = i;
                for (int k = i + 1; k < n; k++) {
                    if (toBeSorted[k] < item) {
                        j++;
                    }
                }
                while (item == toBeSorted[j]) {
                    j++;
                }
                int temp = toBeSorted[j];
                toBeSorted[j] = item;
                item = temp;
                frame.reDrawArray(toBeSorted, i, j);
                try {
                    Thread.sleep(SortingVisualizer.sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        frame.reDrawArray(toBeSorted);
    }

}

