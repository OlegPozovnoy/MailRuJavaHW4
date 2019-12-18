package manager;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContextImplTest {
    ExecutionManager executionManager = new ExecutionManagerImpl();

    @Test
    public void getCompletedTaskCount() {
        RunnableTemplate r2 = new RunnableTemplate(2, "thread2");
        RunnableTemplate r5 = new RunnableTemplate(4, "thread5");
        RunnableTemplate r11 = new RunnableTemplate(6, "thread11");

        Context context = executionManager.execute(r2, r5, r11);
        context.awaitTermination();
        assertEquals(2, context.getCompletedTaskCount());
    }

    @Test
    public void getFailedTaskCount() {
        Context context = executionManager.execute(() -> {
            throw new IllegalArgumentException();
        });
        context.awaitTermination();
        assertEquals(1, context.getFailedTaskCount());
    }

    @Test
    public void getInterruptedTaskCount() {
        // если ставить паузы между thread.start в ContextImpl, то вроде бы прерывается, а если нет, то не понятно, какой тест написать
    }

    @Test
    public void interrupt() {
        // если ставить паузы между thread.start в ContextImpl, то вроде бы прерывается, а если нет, то не понятно, какой тест написать
    }

    @Test
    public void isFinished() {
        Context context = executionManager.execute(() -> {
            throw new IllegalArgumentException();
        });
        context.awaitTermination();
        assertEquals(false, context.isFinished());
        context = executionManager.execute(() -> {
            ;
        });
        context.awaitTermination();
        assertEquals(true, context.isFinished());

    }

    @Test
    public void onFinish() {
        Runnable runnable = () -> {
            System.out.println("Runnable is executed");
        };
        Context context = executionManager.execute(runnable);
        context.onFinish(runnable);
        context.awaitTermination();
    }

    @Test
    public void getStatistics() {
        RunnableTemplate r2 = new RunnableTemplate(2, "thread2");
        RunnableTemplate r5 = new RunnableTemplate(4, "thread5");
        RunnableTemplate r11 = new RunnableTemplate(6, "thread11");

        Context context = executionManager.execute(r2, r5, r11);
        context.awaitTermination();
        System.out.println(context.getStatistics());
    }
}