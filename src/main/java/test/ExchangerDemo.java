package test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class ExchangerDemo {
//    public static void main(String[] args) {
//        final Exchanger<List<Integer>> exchanger = new Exchanger<List<Integer>>();
//
//        new Thread(){
//            public void run(){
//                List<Integer> list = new ArrayList<Integer>();
//                list.add(1);
//                list.add(2);
//                try {
//                    list = exchanger.exchange(list);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("Thread1"+list);
//            }
//        }.start();
//
//        new Thread(){
//            public void run(){
//                List<Integer> list = new ArrayList<Integer>();
//                list.add(3);
//                list.add(4);
//                try {
//                    list = exchanger.exchange(list);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("Thread2"+list);
//            }
//        }.start();
//    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        System.out.println(System.nanoTime());
    }
}