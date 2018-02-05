package test;

public class A {

	private static  int i = 0;

	public static void main(String[] args) {
		Thread[] threads = new Thread[20];
		for (int j = 0; j < 20; j++) {
			threads[j] = new Thread() {
				@Override
				public void run() {
					synchronized (A.class) {
						for (int j = 0; j < 10000; j++) {
							i = i + 1;
						}
					}
				}
			};
			threads[j].start();

		}
		while (Thread.activeCount() > 1)
			Thread.yield();

		System.out.println(i);
	}
}
