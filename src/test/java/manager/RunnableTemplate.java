package manager;

public class RunnableTemplate implements Runnable {
    private final int iterationos;
    private final String name;

    public RunnableTemplate(int interationos, String name) {
        this.iterationos = interationos;
        this.name = name;
    }

    public void run() {
        System.out.println("Starting " + name);
        for (int i = 0; i < iterationos; i++) {
            try {
                if (i >= 5)
                    throw new IllegalArgumentException("Illegal");
                System.out.println(name + "+" + i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Finishing " + name);
    }
}
