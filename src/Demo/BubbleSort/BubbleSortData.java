package Demo.BubbleSort;

import Demo.SortData;

public class BubbleSortData extends SortData {

    int orderedIndex = numbers.length; //[orderedIndex, numbers.length)
    int curIndex = -1;

    public BubbleSortData(int N, int randomBound) {
        super(N, randomBound);
    }
}
