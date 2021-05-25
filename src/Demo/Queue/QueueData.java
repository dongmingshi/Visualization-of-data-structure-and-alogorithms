package Demo.Queue;

public class QueueData {

    private Object[] data;

    int front = 0;
    int rear = 0;
    int total = 0;

    QueueData(int N) {
        data = new Object[N];
    }

    void enqueue(Object o) {
        if(rear == data.length)
            rear = 0;
        data[rear++] = o;
        total++;
    }

    Object dequeue() {
        if(front == data.length)
            front = 0;
        total--;
        return data[front++];
    }

    int N() {
        return data.length;
    }

    Object get(int i) {
        return data[i];
    }
}
