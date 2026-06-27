class MyThread implements Runnable {
    public void run() {
        System.out.println("Thread running using runnable");
    }
}

public class Runnables {
    public static void main(String[] args) {
        MyThread m = new MyThread();
        Thread t1 = new Thread(m);
        t1.start();
    }
}
