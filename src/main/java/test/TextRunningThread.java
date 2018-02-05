package test;

public class TextRunningThread {
	private static void showThread(Thread thread, String index) {// 显示线程信息
		if (thread == null)
			return;
		System.out.println(index + "线程名称 - " + thread.getName() + "  线程优先级 - " + thread.getPriority()
				+ (thread.isDaemon() ? " 守护" : "") + (thread.isAlive() ? "" : " 不活动"));
	}

	private static void showThreadGroup(ThreadGroup group, String index) {// 显示线程组信息
		if (group == null)
			return;// 判断线程组
		int count = group.activeCount();// 获得活动的线程数
		int countGroup = group.activeGroupCount();// 获得活动的线程组数
		Thread[] threads = new Thread[count];// 根据活动的线程数创建指定个数的线程数组
		ThreadGroup[] groups = new ThreadGroup[countGroup];// 根据活动的线程组数创建指定个数的线程组数组
		group.enumerate(threads, false);// 把所有活动子组的引用复制到指定数组中，false表示不包括对子组的所有活动子组的引用
		group.enumerate(groups, false);
		System.out.println(index + "线程组的名称 - " + group.getName() + " 最高优先级 - " + group.getMaxPriority()
				+ (group.isDaemon() ? " 守护" : "　"));
		for (int i = 0; i < count; i++)
			// 循环显示当前活动的线程信息
			showThread(threads[i], index + "");// 调用方法
		for (int i = 0; i < countGroup; i++)
			// 循环显示当前活动的线程组信息
			showThreadGroup(groups[i], index + "");// 递归调用方法
	}

	public static void listAllThreads() {// 找到根线程组并列出它递归的信息
		ThreadGroup currentThreadGroup;// 当前线程组
		ThreadGroup rootThreadGroup;// 根线程组
		ThreadGroup parent;
		currentThreadGroup = Thread.currentThread().getThreadGroup();// 获得当前活动的线程组
		rootThreadGroup = currentThreadGroup;// 获得根线程组
		parent = rootThreadGroup.getParent();// 获得根线程
		while (parent != null) {// 循环对根线程组重新赋值
			rootThreadGroup = parent;
			parent = parent.getParent();
		}
		showThreadGroup(rootThreadGroup, "");// 显示根线程组
	}

	public static void main(String[] args) {// java程序主入口处
		TextRunningThread.listAllThreads();// 调用方法显示所有线程的信息
	}
}