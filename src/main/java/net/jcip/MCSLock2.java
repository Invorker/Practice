package net.jcip;


import java.util.concurrent.atomic.AtomicReference;

/**
 * MCS：John Mellor-Crummey and Michael Scott
 * CLH：Craig，Landin and Hagersten
 *
 * @author zhibo
 * @version 1.0
 * @date 2018/11/7 10:39
 */
public class MCSLock2 {
    private AtomicReference<MSCNode> tail;
    private volatile ThreadLocal<MSCNode> threadLocal;

    public MCSLock2() {
        this.tail = new AtomicReference<>();
        this.threadLocal = new ThreadLocal<>();
    }

    public void lock() {
        MSCNode curNode = threadLocal.get();
        if (curNode == null) {
            curNode = new MSCNode();
            threadLocal.set(curNode);
        }

        MSCNode predNode = tail.getAndSet(curNode);
        if (predNode != null) {
            predNode.setNext(curNode);

            while (curNode.getLocked()) {

            }
        } else {
            curNode.setLocked(false);
        }
    }

    public void unlock() {
        MSCNode curNode = threadLocal.get();
        threadLocal.remove();

        if (curNode == null || curNode.getLocked() == true) {
            return;
        }

        if (curNode.getNext() == null && !tail.compareAndSet(curNode, null)) {
            while (curNode.getNext() == null) {

            }
        }

        if (curNode.getNext() != null) {
            curNode.getNext().setLocked(false);
            curNode.setNext(null);
        }
    }

    public static void main(String[] args) {
        final MCSLock2 mscLock = new MCSLock2();

        for (int i = 0; i < 10; i++) {
            new Thread(new DemoTask(mscLock, i + "")).start();
        }
    }

    class MSCNode {
        private volatile boolean locked = true;
        private volatile MSCNode next = null;

        public boolean getLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        public MSCNode getNext() {
            return next;
        }

        public void setNext(MSCNode next) {
            this.next = next;
        }
    }
}

class DemoTask implements Runnable {
    private MCSLock2 lock;
    private String taskId;

    public DemoTask(final MCSLock2 lock, final String taskId) {
        this.lock = lock;
        this.taskId = taskId;
    }

    @Override
    public void run() {
        try {
            lock.lock();
            Thread.sleep(500);
            System.out.println(String.format("Thread %s Completed", taskId));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}