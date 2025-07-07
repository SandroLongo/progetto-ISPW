package it.uniroma2.progettoispw;

public class WindowCounter {
    private static WindowCounter instance = new WindowCounter();
    private int count = 0;

    public static WindowCounter getInstance() {
        return instance;
    }

    public synchronized void increment() {
        count++;
    }

    public synchronized void decrement() {
        count--;
        if (count <= 0) {
            Launcher.shutdownSocketServer();
        }
    }

    public synchronized int getCount() {
        return count;
    }
}
