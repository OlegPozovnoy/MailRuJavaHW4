package manager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExecutionStatisticsImplTest {
    ExecutionStatisticsImpl statistics = new ExecutionStatisticsImpl();

    @Test
    public void getMinExecutionTimeInMs() {
        assertEquals(0,statistics.getMinExecutionTimeInMs());
        statistics.addExecutionTime(10);
        statistics.addExecutionTime(100);
        assertEquals(10,statistics.getMinExecutionTimeInMs());
    }

    @Test
    public void getMaxExecutionTimeInMs() {
        assertEquals(0,statistics.getMaxExecutionTimeInMs());
        statistics.addExecutionTime(10);
        statistics.addExecutionTime(100);
        assertEquals(100,statistics.getMaxExecutionTimeInMs());
    }

    @Test
    public void getAverageExecutionTimeInMs() {
        assertEquals(0,statistics.getAverageExecutionTimeInMs());
        statistics.addExecutionTime(10);
        statistics.addExecutionTime(100);
        assertEquals(55,statistics.getAverageExecutionTimeInMs());
    }
}