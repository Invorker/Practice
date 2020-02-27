package net.jcip;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class CLHLock {


    private static Unsafe unsafe = null;
    private static final long valueOffset;
    private volatile CLHNode tail;

    public class CLHNode {
        private boolean isLocked = true;
    }


    static {
        try {
            unsafe = getUnsafeInstance();
            valueOffset = unsafe.objectFieldOffset(CLHLock.class
                    .getDeclaredField("tail"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }


    public void lock(CLHNode currentThreadNode) {
        CLHNode preNode = null;
        for (; ; ) {
            preNode = tail;
            if (unsafe.compareAndSwapObject(this, valueOffset, tail,
                    currentThreadNode))
                break;
        }
        if (preNode != null)
            while (preNode.isLocked) {
            }
    }

    /**
     * 释放锁操作，无论if内条件真假，方法都会结束返回。
     * 若compareAndSwapObject返回true，说明当前tail指向currentThreadNode，即队列只剩当前线程，只需要将尾结点(tail)置为空，isLocked无修改的意义；
     * 否则将isLocked置为false，下一个线程自然会从lock方法的while循环退出返回，结束自旋。
     *
     * @param currentThreadNode
     */
    public void unlock(CLHNode currentThreadNode) {
        if (!unsafe.compareAndSwapObject(this, valueOffset, currentThreadNode, null))
            currentThreadNode.isLocked = false;
    }


    private static Unsafe getUnsafeInstance() throws SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        return (Unsafe) theUnsafeInstance.get(Unsafe.class);
    }


}