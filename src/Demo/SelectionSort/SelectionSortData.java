package Demo.SelectionSort;

import Demo.SortData;

public class SelectionSortData extends SortData {

    int orderedIndex = 0;
    int curMinIndex = -1;
    int curCompareIndex = -1;

    SelectionSortData(int N, int randomBound) {
        super(N, randomBound);
    }

}
