package Demo.MergeSort;

import Demo.SortData;

public class MergeSortData extends SortData {

    public int l, r;
    public int mergeIndex;

    public MergeSortData(int N, int randomBound) {
        super(N, randomBound);
        l = -1;
        r = -1;
        mergeIndex = -1;
    }
}
