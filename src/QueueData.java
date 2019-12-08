public class QueueData {

    private Object[] data;

    int front = 0;
    int rear = 0;

    QueueData(int N) {
        data = new Object[N];
    }

    void enqueue(Object o) {
        data[rear++] = o;
    }

    Object dequeue() {
        return data[front++];
    }

    int N() {
        return data.length;
    }

    Object get(int i) {
        return data[i];
    }
}
