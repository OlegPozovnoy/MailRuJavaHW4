package manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ContextImpl implements Context, Runnable {
    private final Object lock = new Object();
    private int interruptedTaskCount = 0;
    private volatile boolean isInterrupted = false;
    private boolean isFinished = false;
    private boolean isStarted = false;
    private List<Thread> threads = new ArrayList<>();
    private List<RunnableWithStatistics> runnables = new ArrayList<>();
    private Optional<Runnable> callback = Optional.empty();

    ContextImpl(Runnable... tasks) {
        Arrays.stream(tasks).map(RunnableWithStatistics::new).forEach(t -> runnables.add(t));
    }

    public boolean isInterrupted() {
        return isInterrupted;
    }

    public void run() {
        // на всякий случай чтобы 2 раза нельзя было запускать как Runnable
        synchronized (this) {
            if (!isStarted)
                isStarted = true;
            else
                return;
        }
        // стартуем
        for (Runnable runnable : runnables)
            if (!isInterrupted) {
                Thread thread = new Thread(runnable);
                threads.add(thread);
                thread.start();
            } else {
                interruptedTaskCount = runnables.size() - threads.size();
            }
        // ждем завершения
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ignore) {
            }
        }
        // говорим что завершились
        synchronized (lock) {
            isFinished = true;
            lock.notify();
        }
        callback.ifPresent(Runnable::run);
    }

}

    public int getCompletedTaskCount() {
        return (int) runnables.stream().map(RunnableWithStatistics::getState)
                .filter(t -> t == RunnableWithStatistics.State.SUCCESS)
                .count();
    }

    public int getFailedTaskCount() {
        return (int) runnables.stream().map(RunnableWithStatistics::getState)
                .filter(t -> t == RunnableWithStatistics.State.FAIL)
                .count();
    }

    public int getInterruptedTaskCount() {
        return interruptedTaskCount;
    }

    public void interrupt() {
        isInterrupted = true;
    }

    public boolean isFinished() {
        return (runnables.size() - getCompletedTaskCount() - interruptedTaskCount == 0);
    }

    public void onFinish(Runnable callback) {
        this.callback = Optional.of(callback);
    }

    public ExecutionStatistics getStatistics() {
        ExecutionStatisticsImpl statistics = new ExecutionStatisticsImpl();
        runnables.stream()
                .filter(t -> t.getState() == RunnableWithStatistics.State.SUCCESS)
                .forEach(t -> statistics.addExecutionTime(t.getLength()));
        return statistics;
    }

    public void awaitTermination() {
        synchronized (lock) {
            while (!isFinished)
                try {
                    lock.wait();
                } catch (InterruptedException ignore) {
                }
        }
    }
}
