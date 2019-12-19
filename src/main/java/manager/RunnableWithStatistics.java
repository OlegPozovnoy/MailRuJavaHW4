package manager;

public class RunnableWithStatistics implements Runnable {
    private State state = State.UNDEFINED;
    private int length;
    private final Runnable task;

    public RunnableWithStatistics(Runnable task) {
        this.task = task;
    }

    public void run() {
        try {
            long startTime = System.nanoTime();
            task.run();
            long endTime = System.nanoTime();
            this.length = (int) ((endTime - startTime) / 1000000);
            this.state = State.SUCCESS;
        } catch (Exception e) {
            this.state = State.FAIL;
        }
    }

    public State getState() {
        return state;
    }

    public int getLength() {
        return length;
    }

    public enum State {UNDEFINED, SUCCESS, FAIL}
}
