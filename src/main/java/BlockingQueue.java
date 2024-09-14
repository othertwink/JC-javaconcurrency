import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {
    private Queue<T> queue;
    private int max;

    public BlockingQueue(int size) {
        this.queue = new LinkedList<>();
        this.max = size;
    }

    public void enqueue(T item) throws InterruptedException {
        synchronized (queue) {
            if (queue.size() == max) {
                queue.wait();
            }
            queue.add(item);
            queue.notifyAll();
        }
    }


    public void dequeue() throws InterruptedException {
        synchronized (queue) {
            if (queue.size() != max) {
                queue.wait();
            }
            T item = queue.poll();
            queue.add(item);
            queue.notifyAll();
        }
    }

    public int size() {
        synchronized (queue) {
            return queue.size();
        }
    }

}