package manager;

public class ExecutionManagerImpl implements  ExecutionManager {
    public Context execute(Runnable... tasks){
        Context result = new ContextImpl(tasks);
        new Thread((Runnable) result).start();
        return result;
    }
}
