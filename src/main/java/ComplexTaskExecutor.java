import java.util.concurrent.*;

public class ComplexTaskExecutor {

    private final CyclicBarrier barrier;
    private final ExecutorService executorService;
    private final ComplexTask[] tasks;

    public ComplexTaskExecutor(int numberOfTasks) {
        this.barrier = new CyclicBarrier(numberOfTasks, this::combineResults);
        this.executorService = Executors.newFixedThreadPool(numberOfTasks);
        this.tasks = new ComplexTask[numberOfTasks];
    }

    public void executeTasks(int numberOfTasks) {
        for (int i = 0; i < numberOfTasks; i++) {
            tasks[i] = new ComplexTask(i + 1);
            int taskIndex = i;
            executorService.submit(() -> {
                tasks[taskIndex].execute();
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }

    private void combineResults() {
        System.out.println("All tasks finished. Combining results:");
        int res = 0;
        for (ComplexTask task : tasks) {
            res += task.getResult();
            System.out.println("Task " + task.getTaskId() + " result: " + task.getResult());
        }
        System.out.println("Combined result: " + res);
    }
}
