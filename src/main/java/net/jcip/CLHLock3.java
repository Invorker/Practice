package net.jcip;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;


/**
 * @see https://www.cnblogs.com/yuyutianxia/p/4296220.html
 */
public abstract class CLHLock3 implements Lock {
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myPred;
    ThreadLocal<QNode> myNode;

    public class QNode {
        private volatile boolean locked;

    }

    public CLHLock3() {

        tail = new AtomicReference<QNode>(new QNode());
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
        myPred = new ThreadLocal<QNode>() {
            @Override
            protected QNode initialValue() {
                return null;
            }
        };
    }

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        qnode.locked = true;
        QNode pred = tail.getAndSet(qnode);
        myPred.set(pred);
        while (pred.locked) {
        }
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        qnode.locked = false;
        myNode.set(myPred.get());
    }
}
