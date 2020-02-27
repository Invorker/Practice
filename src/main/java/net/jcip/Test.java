package net.jcip;

public class Test {

    private static Object lock ;

    public static class  Run implements Runnable{

        @Override
        public void run() {
            try {
                synchronized (lock){
                    System.out.println("qweqwe");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                System.out.println("Ex");
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        lock  = new Object();
        new Thread(new Run()).start();
        Thread.sleep(1000);
        lock = null;
    }
}
