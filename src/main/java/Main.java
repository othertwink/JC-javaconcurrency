import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // test case 1
        int queueSize = 5;
        int requests = 10;
        BlockingQueue<Integer> queue = new BlockingQueue<>(queueSize);

        List<Integer> es = new ArrayList<>();
        for (int i = 0; i < requests; i ++) {
            es.add(i*4);
        }

        Thread producer = new Thread(() -> {
            try {
                for (Integer e : es) {
                    queue.enqueue(e);
                    System.out.println("Produced and enqued" + e);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < requests; i++) {
                    queue.dequeue();
                    System.out.println("Consumed");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });


        producer.start();
        consumer.start();

        // end of test case 1

    }
}
