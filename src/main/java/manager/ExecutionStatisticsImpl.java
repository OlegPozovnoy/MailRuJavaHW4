package manager;

public class ExecutionStatisticsImpl implements ExecutionStatistics {
    private int minExecutionTimeInMs = Integer.MAX_VALUE; // минимальное время выполнения среди тасков в миллисекундах
    private int maxExecutionTimeInMs = Integer.MIN_VALUE; // максимальное время выполнения среди тасков в миллисекундах
    private int tasksAccounted;
    private int totalExecutionTime;

    public int getMinExecutionTimeInMs() {
        return zeroIfNoTasks(minExecutionTimeInMs);
    }

    public int getMaxExecutionTimeInMs() {
        return zeroIfNoTasks(maxExecutionTimeInMs);
    }

    public int getAverageExecutionTimeInMs() {
        return tasksAccounted == 0 ? 0 : totalExecutionTime / tasksAccounted;
    }

    public void addExecutionTime(int executionTime) {
        minExecutionTimeInMs = Math.min(executionTime, minExecutionTimeInMs);
        maxExecutionTimeInMs = Math.max(executionTime, maxExecutionTimeInMs);
        tasksAccounted += 1;
        totalExecutionTime += executionTime;
    }

    private int zeroIfNoTasks(int value){
        return tasksAccounted == 0 ? 0 : value;
    }

    @Override
    public String toString() {
        return "\nminExecutionTime: " + minExecutionTimeInMs +
                "\nmaxExecutionTime: " + maxExecutionTimeInMs +
                "\navgExecutionTime: " + getAverageExecutionTimeInMs();
    }
}
