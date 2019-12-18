package manager;

public interface ExecutionManager {
    Context execute(Runnable... tasks);
}
