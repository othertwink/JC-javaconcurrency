import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {
    private Queue<T> queue;
    private int max;

    public BlockingQueue(int size) {
        this.queue = new LinkedList<>();
        this.max = size;
    }

    public synchronized void enqueue(T item) throws InterruptedException {
        if (queue.size() == max) {
            wait();
        }
        queue.add(item);
        notifyAll();
    }


    public synchronized void dequeue() throws InterruptedException {
        if (queue.size() != max) {
            wait();
        }
        T item = queue.poll();
        queue.add(item);
        notifyAll();
    }

    public synchronized int size() {
        return queue.size();
    }

}
