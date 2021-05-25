package Demo.Stack;

public class StackData {

    private Object[] data;
    int top = -1;

    public StackData(int N) {
        data = new Object[N];
    }

    void push(Object o) {
        data[++top] = o;
    }

    Object pop() {
        return data[top--];
    }

    int N() {
        return data.length;
    }

    void clear() {
        top = -1;
    }

    Object get(int i) {
        return data[i];
    }
}
