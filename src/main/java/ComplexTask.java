import java.util.Random;

public class ComplexTask {

    private final int taskId;
    private int result;

    public ComplexTask(int taskId) {
        this.taskId = taskId;
    }

    public void execute() {

        try {
            Thread.sleep(new Random().nextInt(3000)); // имитация бурной деятельности
            result = new Random().nextInt(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } // почему System.out выводит принт после CombineResults()?
    }

    public int getResult() {
        return result;
    }

    public int getTaskId() {
        return taskId;
    }
}
