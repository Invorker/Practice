package test;


/**
 * suspend 挂起线程，并不释放线程占有的锁。 循环语句，由于锁粗化，一次加锁。
 */
public class Mythread extends Thread {
    private long i = 0;

    @Override
    public void run() {
        while (true) {
            i++;
            System.out.println(i);
        }
    }


    public static void main(String[] args) {

        try {
            Mythread mythread = new Mythread();
            mythread.start();
            Thread.sleep(1000);
            mythread.suspend();
            System.out.println("main end!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}